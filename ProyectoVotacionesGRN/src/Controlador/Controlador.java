package Controlador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYZDataset;

import Modelo.DatosComunidad;
import Modelo.Escrutinio;
import Modelo.Principal;
import Modelo.Urna;
import Vista.InterfazVotaciones;

public class Controlador implements ActionListener {

	private static final long ESPERA = 10 * 1000;
	private boolean bandera = false;
	InterfazVotaciones iv = new InterfazVotaciones();
	
	// Variables && Objetos
	
	private int totalV;

	private String[][] datosEscrutinio = new String[20][17];
	private ArrayList<DatosComunidad> datosComunidades = new ArrayList<DatosComunidad>();
	private ArrayList<String> esRangos = new ArrayList<String>();
	private ArrayList<String> esComunidades = new ArrayList<String>();
	private ArrayList<String> esRangosTOTAL = new ArrayList<String>();
	private String[][] esComunidadesTOTAL = new String[20][6];
	private ArrayList<String> nombresC = new ArrayList<String>();
	
	Principal p = new Principal();
	private int rang =0;
	private String rangoEdad="";
	Connection cn = null;

	public Controlador(InterfazVotaciones frame) {

		this.iv = frame;
		this.iv.botonIniciarVotacion.addActionListener(this);
		this.iv.botonMostrarPanelRs.addActionListener(this);
		this.iv.botonRespaña.addActionListener(this);
		this.iv.botonatrasPREsp.addActionListener(this);
		this.iv.botonRcom.addActionListener(this);
		this.iv.botonMostrarPanelRcom.addActionListener(this);
		this.iv.botonatrasPRCom.addActionListener(this);
		this.iv.botonRRang.addActionListener(this);
		this.iv.botonatrasPRRang.addActionListener(this);
		this.iv.botonRRangFiltro18_25.addActionListener(this);
		this.iv.botonRRangFiltro26_40.addActionListener(this);
		this.iv.botonRRangFiltro41_65.addActionListener(this);
		this.iv.botonRRangFiltroMas66.addActionListener(this);
		
		iv.panelinicio.setVisible(true);
		iv.panelMostrar.setVisible(false);
		iv.panelRespaña.setVisible(false);
		iv.panelRcom.setVisible(false);
		iv.panelRRang.setVisible(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Acciones de los botones
		if (e.getSource() == iv.botonIniciarVotacion) {
			iv.tituloPanelVotacion.setVisible(true);
			iv.gifLoading.setVisible(true);
			iv.gifVotacion.setVisible(true);
			iv.tituloPInicio.setVisible(false);
			iv.botonIniciarVotacion.setVisible(false);
			iv.panelRespaña.setVisible(false);
			
			
			//Al pulsar realizamos el procedimiento
			try {
				// Conexión
				cn = p.createConnection();
				// Recuperamos datos del censo, llamando al método "obtenerDatosCenso"
				datosComunidades = p.obtenerDatosCenso(cn);
				// Calculamos los votantes
				p.calcularVotantes(datosComunidades);
				// Obetener los nombres de las comunidades
				nombresC = p.nombresComunidades(datosComunidades);
				
				// Creación de la urna
				Urna urna = new Urna(nombresC);
				// Crear los hilos
				totalV = p.crearHilos(datosComunidades, nombresC, urna);

				// Crear hilo escruitinio
				Escrutinio esc = new Escrutinio(totalV, urna,nombresC);
				esc.start();
				esc.join();
				datosEscrutinio = esc.getDatosGen();
				
				//Recuperamos ganadores del hilo escrutinio
				esRangos = esc.getGanadores_Rangos_esGanadorFinal();
				esComunidades = esc.getGanadores_PorComunidad();
				
				//Recuperamos los ganadores y sus votos, según los rangos y a nivel España
				esRangosTOTAL = esc.getRecuentoRangos_ESP();
				
				//Recuperamos los ganadores y sus votos, según cada comunidad autonoma
				esComunidadesTOTAL = esc.getRecuentoComunidades();
				
				// Realiza el insert de todo los datos del escrutinio, en la tabla votacion
				p.insertMasivo(cn, datosEscrutinio, esRangos, esComunidades);

				
				// Imprimir datos
				// System.out.println(datosComunidades.toString());
				System.out.println("*************************************");
				System.out.println("	   Resultado segun rango ");
				System.out.println("18_25 años " + esRangos.get(0));
				System.out.println("26_40 años " + esRangos.get(1));
				System.out.println("41_65 años " + esRangos.get(2));
				System.out.println("mas_66 años " + esRangos.get(3));
				System.out.println("ganador elecciones: " + esRangos.get(4));
				System.out.println("*************************************");
				System.out.println(esRangosTOTAL);
				System.out.println();

				for (int u = 0; u < 20; u++) {
					for (int v = 0; v < 6; v++) {
						System.out.print(esComunidadesTOTAL[u][v].toString() + "--");
					}
					System.out.println();
				
				}
				System.out.println(totalV);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					p.disconnect(cn);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}	
			
			//Ver el botón mostrar
			iv.botonMostrarPanelRs.setVisible(true);
				
		}else if(e.getSource() == iv.botonMostrarPanelRs) {
			iv.panelinicio.setVisible(false);
			iv.panelMostrar.setVisible(true);
			this.rellenarCBComunidades();
			
			
		}else if(e.getSource() == iv.botonRespaña) {

			iv.panelMostrar.setVisible(false);
			iv.panelRespaña.setVisible(true);
			this.iv.logoGanadorREspaña.setVisible(true);
			this.iv.ganadorREspaña.setVisible(true);
			this.iv.ganadorREspaña.setVisible(true);
			this.iv.presidenteREspaña.setVisible(true);
			this.iv.fotoPresidenteREspaña.setVisible(true);
			this.resultadoEspaña();
			
		}else if(e.getSource() == iv.botonRcom) {
			iv.panelMostrar.setVisible(false);
			iv.panelRcom.setVisible(true);
			
		}else if(e.getSource() == iv.botonMostrarPanelRcom) {
		
			if(!iv.comboBoxCom.getSelectedItem().toString().equals("")) {
						
				this.resultadoCom();	
				
			}
		}else if(e.getSource() == iv.botonatrasPREsp) {
			iv.panelMostrar.setVisible(true);
			iv.panelRespaña.setVisible(false);
			
		}else if(e.getSource() == iv.botonatrasPRCom) {
			iv.panelMostrar.setVisible(true);
			iv.panelRcom.setVisible(false);
		}else if(e.getSource() == iv.botonRRang) {
			iv.panelMostrar.setVisible(false);
			iv.panelRRang.setVisible(true);
			
		}else if(e.getSource() == iv.botonRRangFiltro18_25) {
			this.rang = 1;
			this.rangoEdad = " personas entre 18 y 25 años";
			this.resultadoRangos();
		}else if(e.getSource() == iv.botonRRangFiltro26_40) {
			this.rang = 2;
			this.rangoEdad = "personas entre 26 y 40 años";
			this.resultadoRangos();
		}else if(e.getSource() == iv.botonRRangFiltro41_65) {
			this.rang = 3;
			this.rangoEdad = "personas entre 41 y 65 años";
			this.resultadoRangos();
		}else if(e.getSource() == iv.botonRRangFiltroMas66) {
			this.rang = 4;
			this.rangoEdad = "personas con más de 66 años";
			this.resultadoRangos();
		}else if(e.getSource() ==iv.botonatrasPRRang) {
			iv.panelMostrar.setVisible(true);
			iv.panelRRang.setVisible(false);
		}

	}
	
	//PANEL RESULTADOS ESPAÑA
		public void resultadoEspaña() {
			String ganadorE= "";
			
			int pp = Integer.parseInt(esRangosTOTAL.get(20));
		    int psoe = Integer.parseInt(esRangosTOTAL.get(21));
		    int podemos = Integer.parseInt(esRangosTOTAL.get(22));
		    int vox = Integer.parseInt(esRangosTOTAL.get(23));
			
			ganadorE = esRangosTOTAL.get(24);
			
			if(ganadorE.equals("PP")) {
				iv.logoGanadorREspaña.setIcon(new ImageIcon("imgsVotaciones/iPP.png"));
				iv.fotoPresidenteREspaña.setIcon(new ImageIcon("imgsVotaciones/pPPEsp.PNG"));
			}else if(ganadorE.equals("PSOE")) {
				iv.logoGanadorREspaña.setIcon(new ImageIcon("imgsVotaciones/iPSOE.jpg"));
				iv.fotoPresidenteREspaña.setIcon(new ImageIcon("imgsVotaciones/pPSOEEsp.PNG"));
			}else if(ganadorE.equals("PODEMOS")) {
				iv.logoGanadorREspaña.setIcon(new ImageIcon("imgsVotaciones/iPODEMOS.jpg"));
				iv.fotoPresidenteREspaña.setIcon(new ImageIcon("imgsVotaciones/pPODEMOSEsp.PNG"));
			}else if(ganadorE.equals("VOX")) {
				iv.logoGanadorREspaña.setIcon(new ImageIcon("imgsVotaciones/iVOX.png"));
				iv.fotoPresidenteREspaña.setIcon(new ImageIcon("imgsVotaciones/pVOXEsp.PNG"));
			}
			
			
			//Pintar Grafico
			

			DefaultPieDataset datosVotosE = new DefaultPieDataset();
			
			datosVotosE.setValue("PP \n Votos -> "+pp, pp);
			datosVotosE.setValue("PSOE \n Votos -> "+psoe, psoe);
			datosVotosE.setValue("PODEMOS \n Votos -> "+podemos, podemos);
			datosVotosE.setValue("VOX \n Votos -> "+vox, vox);
			
			iv.graficoEsp = ChartFactory.createPieChart("RESULTADO ELECTORAL A NIVEL DE ESPAÑA",
					datosVotosE,
					true, 
					true, 
					true);
			
			PiePlot plot = (PiePlot) iv.graficoEsp.getPlot();
			
			LegendTitle legend = iv.graficoEsp.getLegend();
				legend.setItemFont(new Font("Tahoma", Font.BOLD, 14));
			
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
					"{2}", NumberFormat.getNumberInstance(),
					new DecimalFormat("0%")));
			 
			plot.setSectionPaint(0, new Color(0, 47, 187));
			plot.setSectionPaint(1, new Color(200,29,17));
			plot.setSectionPaint(2, new Color(139,0,139));
			plot.setSectionPaint(3, new Color(0,187,45));
			
			plot.setLabelFont(new Font("Tahoma", Font.BOLD, 14));
		    iv.graficoEsp.setBackgroundPaint(new Color(102, 205, 170));
			iv.panelGraficoEsp.setChart(iv.graficoEsp);	
			
			iv.graficoEsp.setBackgroundPaint(new Color(102, 205, 170));
			iv.panelGraficoEsp.setChart(iv.graficoEsp);
			
		}
	
	//PANEL RESULTADOS COMUNIDADES
		public void rellenarCBComunidades() {
			try {
				
				iv.comboBoxCom.addItem("");
				for(int i=0;i<this.nombresC.size();i++) {
					iv.comboBoxCom.addItem(this.nombresC.get(i));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		public void resultadoCom() {
			int pp=0,psoe=0,podemos=0,vox=0;
			String comunidad="";
			String ganador ="";
			
			if(!iv.comboBoxCom.getSelectedItem().toString().equals("")) {
				this.iv.ganadorRcom.setVisible(true);
				this.iv.logoGanadorRcom.setVisible(true);
				this.iv.panelGraficoCom.setVisible(true);
			}
			
			
			//Recuperar votos
			for(int i = 1; i < 20;i++) {
				if(this.esComunidadesTOTAL[i][0].equals(iv.comboBoxCom.getSelectedItem().toString())) {
					comunidad=esComunidadesTOTAL[i][0];
					pp = Integer.parseInt(esComunidadesTOTAL[i][1]);
				    psoe = Integer.parseInt(esComunidadesTOTAL[i][2]);
				    podemos = Integer.parseInt(esComunidadesTOTAL[i][3]);
				    vox = Integer.parseInt(esComunidadesTOTAL[i][4]);
				    ganador = esComunidadesTOTAL[i][5];
				}
			}
			
			if(ganador.equals("PP")) {
				iv.logoGanadorRcom.setIcon(new ImageIcon("imgsVotaciones/iPP.png"));
			}else if(ganador.equals("PSOE")) {
				iv.logoGanadorRcom.setIcon(new ImageIcon("imgsVotaciones/iPSOE.jpg"));
			}else if(ganador.equals("PODEMOS")) {
				iv.logoGanadorRcom.setIcon(new ImageIcon("imgsVotaciones/iPODEMOS.jpg"));
			}else if(ganador.equals("VOX")) {
				iv.logoGanadorRcom.setIcon(new ImageIcon("imgsVotaciones/iVOX.png"));
			}
			
			
			//Pintar Grafico
			

			DefaultCategoryDataset datosVotosCom = new DefaultCategoryDataset();
			
			datosVotosCom.setValue(pp, "PP \n Votos -> "+pp, "PP");
			datosVotosCom.setValue(psoe, "PSOE \n Votos -> "+psoe, "PSOE");
			datosVotosCom.setValue(podemos, "PODEMOS \n Votos -> "+podemos, "PODEMOS");
			datosVotosCom.setValue(vox, "VOX \n Votos -> "+vox, "VOX");
			
			iv.graficoCom = ChartFactory.createBarChart3D("RESULTADO ELECTORAL DE "+comunidad.toUpperCase(), 
					"Partidos", 
					"Votos", 
					datosVotosCom, 
					PlotOrientation.VERTICAL, 
					true, 
					true, 
					false);
			
			LegendTitle legend = iv.graficoCom.getLegend();
			legend.setItemFont(new Font("Tahoma", Font.BOLD, 12));
			
			CategoryPlot plot =(CategoryPlot) iv.graficoCom.getPlot();
			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			//Dar color a cada barra
		        GradientPaint gp0= new GradientPaint(0.0f,0.0f,new Color(0, 47, 187),0.0f,0.0f,new Color(0, 47, 187));
		        GradientPaint gp1= new GradientPaint(0.0f,0.0f,new Color(200,29,17),0.0f,0.0f,new Color(200,29,17));
		        GradientPaint gp2= new GradientPaint(0.0f,0.0f,new Color(139,0,139),0.0f,0.0f,new Color(139,0,139));
		        GradientPaint gp3= new GradientPaint(0.0f,0.0f,new Color(0,187,45),0.0f,0.0f,new Color(0,187,45));
		        renderer.setSeriesPaint(0,gp0);
		        renderer.setItemMargin(-2);
		        renderer.setSeriesPaint(1,gp1);
		        renderer.setItemMargin(-2);
		        renderer.setSeriesPaint(2,gp2);
		        renderer.setItemMargin(-2);
		        renderer.setSeriesPaint(3,gp3);
		        renderer.setItemMargin(-2);
			
			
		    iv.graficoCom.setBackgroundPaint(new Color(102, 205, 170));
			iv.panelGraficoCom.setChart(iv.graficoCom);
			
			
		}

	//Panel resultado rangos
		@SuppressWarnings("deprecation")
		public void resultadoRangos() {
			int pp=0,psoe=0,podemos=0,vox=0;
			String ganador ="";
			
			//Mostramos el ganador
				iv.panelGraficoRang.setVisible(true);
				iv.ganadorRRang.setVisible(true);
				iv.logoGanadorRRang.setVisible(true);
				
			//Recuperar votos
		
				if(this.rang == 1) {
					
					pp = Integer.parseInt(esRangosTOTAL.get(0));
					psoe = Integer.parseInt(esRangosTOTAL.get(1));
					podemos = Integer.parseInt(esRangosTOTAL.get(2));
					vox = Integer.parseInt(esRangosTOTAL.get(3));
					ganador = esRangosTOTAL.get(4);
					
				}else if(this.rang == 2) {
					
					pp = Integer.parseInt(esRangosTOTAL.get(5));
					psoe = Integer.parseInt(esRangosTOTAL.get(6));
					podemos = Integer.parseInt(esRangosTOTAL.get(7));
					vox = Integer.parseInt(esRangosTOTAL.get(8));
					ganador = esRangosTOTAL.get(9);
					
				}else if(this.rang == 3) {
					
					pp = Integer.parseInt(esRangosTOTAL.get(10));
					psoe = Integer.parseInt(esRangosTOTAL.get(11));
					podemos = Integer.parseInt(esRangosTOTAL.get(12));
					vox = Integer.parseInt(esRangosTOTAL.get(13));
					ganador = esRangosTOTAL.get(14);
					
				}else if(this.rang == 4) {
					pp = Integer.parseInt(esRangosTOTAL.get(15));
					psoe = Integer.parseInt(esRangosTOTAL.get(16));
					podemos = Integer.parseInt(esRangosTOTAL.get(17));
					vox = Integer.parseInt(esRangosTOTAL.get(18));
					ganador = esRangosTOTAL.get(19);
				}
			
			
			if(ganador.equals("PP")) {
				iv.logoGanadorRRang.setIcon(new ImageIcon("imgsVotaciones/iPP.png"));
			}else if(ganador.equals("PSOE")) {
				iv.logoGanadorRRang.setIcon(new ImageIcon("imgsVotaciones/iPSOE.jpg"));
			}else if(ganador.equals("PODEMOS")) {
				iv.logoGanadorRRang.setIcon(new ImageIcon("imgsVotaciones/iPODEMOS.jpg"));
			}else if(ganador.equals("VOX")) {
				iv.logoGanadorRRang.setIcon(new ImageIcon("imgsVotaciones/iVOX.png"));
			}
			

			//Pintar Grafico
			

			DefaultCategoryDataset datosVotosRang = new DefaultCategoryDataset();
			
			datosVotosRang.setValue(pp, "PP \n Votos -> "+pp, "PP");
			datosVotosRang.setValue(psoe, "PSOE \n Votos -> "+psoe, "PSOE");
			datosVotosRang.setValue(podemos, "PODEMOS \n Votos -> "+podemos, "PODEMOS");
			datosVotosRang.setValue(vox, "VOX \n Votos -> "+vox, "VOX");
			
			iv.graficoRang = ChartFactory.createBarChart("RESULTADO ELECTORAL DE "+this.rangoEdad.toUpperCase(), 
					"Partidos", 
					"Votos", 
					datosVotosRang, 
					PlotOrientation.HORIZONTAL, 
					true, 
					true, 
					false);
			LegendTitle legend = iv.graficoRang.getLegend();
			legend.setItemFont(new Font("Tahoma", Font.BOLD, 12));
			
			CategoryPlot plot =(CategoryPlot) iv.graficoRang.getPlot();
			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			//Dar color a cada barra
		        GradientPaint gp0= new GradientPaint(0.0f,0.0f,new Color(0, 47, 187),0.0f,0.0f,new Color(0, 47, 187));
		        GradientPaint gp1= new GradientPaint(0.0f,0.0f,new Color(200,29,17),0.0f,0.0f,new Color(200,29,17));
		        GradientPaint gp2= new GradientPaint(0.0f,0.0f,new Color(139,0,139),0.0f,0.0f,new Color(139,0,139));
		        GradientPaint gp3= new GradientPaint(0.0f,0.0f,new Color(0,187,45),0.0f,0.0f,new Color(0,187,45));
		        renderer.setSeriesPaint(0,gp0);
		        renderer.setItemMargin(-2);
		        renderer.setSeriesPaint(1,gp1);
		        renderer.setItemMargin(-2);
		        renderer.setSeriesPaint(2,gp2);
		        renderer.setItemMargin(-2);
		        renderer.setSeriesPaint(3,gp3);
		        renderer.setItemMargin(-2);
	
		    iv.graficoRang.setBackgroundPaint(new Color(102, 205, 170));
			iv.panelGraficoRang.setChart(iv.graficoRang);
			
			
		}
}
