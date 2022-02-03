package Vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import Controlador.Controlador;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GradientPaint;

import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

public class InterfazVotaciones extends JFrame {
	
	public JPanel contentPane;

	public JPanel panelinicio;
		public JLabel tituloPInicio;
		public JLabel logoGobierno;
		public JLabel logoEfa;
		public JButton botonIniciarVotacion;
		public JLabel tituloPanelVotacion;
		public JLabel gifLoading;
		public JLabel gifVotacion;
		public JButton botonMostrarPanelRs;
	
	public JPanel panelMostrar;
		public JLabel tituloPanelMostrar;
		public JButton botonRespaña;
		public JLabel logoMapaEspaña;
		public JLabel logoMapaEspaña2;
		public JButton botonRcom;
		public JLabel logoMapaCom;
		public JLabel logoMapaCom2;
		public JButton botonRRang;
		public JLabel logoRangos;
		public JLabel logoRangos2;
	
	public JPanel panelRespaña;
		public JLabel tituloPanelRespaña;
		public JFreeChart graficoEsp;
		public ChartPanel panelGraficoEsp;
		public JLabel ganadorREspaña;
		public JLabel presidenteREspaña;
		public JLabel logoGanadorREspaña;
		public JLabel fotoPresidenteREspaña;
		public JButton botonatrasPREsp;
	
	public JPanel panelRcom;
		public JPanel panelRcomFiltroCom;
		public JLabel tituloPanelRcom;
		public JButton botonMostrarPanelRcom;
		public JComboBox comboBoxCom;
		public JLabel tituloMostrarRcom;
		public JLabel ganadorRcom;
		public JLabel logoGanadorRcom;
		public JFreeChart graficoCom;
		public ChartPanel panelGraficoCom;
		public JButton botonatrasPRCom;

	public JPanel panelRRang;
		public JLabel tituloPanelRRang;
		public JLabel logoPanelRRang;
		public JLabel logoPanelRRang2;
		public JPanel panelRRangFiltro;
			public JButton botonRRangFiltro18_25;
			public JButton botonRRangFiltro26_40;
			public JButton botonRRangFiltro41_65;
			public JButton botonRRangFiltroMas66;
		public JLabel ganadorRRang;
		public JLabel logoGanadorRRang;
		public JFreeChart graficoRang;
		public ChartPanel panelGraficoRang;
		public JButton botonatrasPRRang;
	/**
	 /* Launch the application.
	  * 
	  * @param args
	  */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazVotaciones frame = new InterfazVotaciones();
					frame.setVisible(true);
					@SuppressWarnings("unused")
					Controlador controlador=new Controlador(frame);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazVotaciones() {
		setTitle("VOTACIONES GENERALES");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 731);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(new BorderLayout(0, 0));

		
		panelinicio = new JPanel();
		panelinicio.setBackground(new Color(102, 205, 170));
		panelinicio.setBounds(0, 0, 948, 690);
		contentPane.add(panelinicio);
		panelinicio.setLayout(null);
		
		tituloPInicio = new JLabel("");
		tituloPInicio.setFont(new Font("Cambria", Font.BOLD, 60));
		tituloPInicio.setBounds(179, 154, 568, 300);
		tituloPInicio.setIcon(new ImageIcon("imgsVotaciones/logoVotaciones2023.png"));
		panelinicio.add(tituloPInicio);
		
		botonIniciarVotacion = new JButton("INICIAR VOTACIONES\r\n");
		botonIniciarVotacion.setForeground(new Color(0, 0, 0));
		botonIniciarVotacion.setFont(new Font("Tahoma", Font.BOLD, 32));
		botonIniciarVotacion.setBounds(275, 518, 404, 97);
		panelinicio.add(botonIniciarVotacion);
		
		tituloPanelVotacion = new JLabel("LAS VOTACIONES SE EST\u00C1N REALIZANDO");
		tituloPanelVotacion.setFont(new Font("Cambria", Font.BOLD, 38));
		tituloPanelVotacion.setBounds(117, 98, 760, 132);
		tituloPanelVotacion.setVisible(false);
		panelinicio.add(tituloPanelVotacion);
		
		gifLoading = new JLabel("");
		gifLoading.setBounds(810, 570, 97, 89);
		gifLoading.setIcon(new ImageIcon("imgsVotaciones/cargando.gif"));
		gifLoading.setVisible(false);
		panelinicio.add(gifLoading);
		
		gifVotacion = new JLabel("");
		gifVotacion.setBounds(284, 152, 370, 400);
		gifVotacion.setIcon(new ImageIcon("imgsVotaciones/gifVotacion.gif"));
		gifVotacion.setVisible(false);
		panelinicio.add(gifVotacion);
		
		botonMostrarPanelRs = new JButton("MOSTRAR");
		botonMostrarPanelRs.setFont(new Font("Tahoma", Font.BOLD, 30));
		botonMostrarPanelRs.setBounds(392, 570, 200, 80);
		botonMostrarPanelRs.setVisible(false);
		panelinicio.add(botonMostrarPanelRs);
		
		logoGobierno = new JLabel("");
		logoGobierno.setBounds(10, 10, 313, 97);
		logoGobierno.setVisible(true);
		logoGobierno.setIcon(new ImageIcon("imgsVotaciones/gobiernoPRESIDENCIA.png"));
		panelinicio.add(logoGobierno);
		
		logoEfa = new JLabel("");
		logoEfa.setBounds(613, 10, 313, 97);
		logoEfa.setVisible(true);
		logoEfa.setIcon(new ImageIcon("imgsVotaciones/logoEfa.png"));
		panelinicio.add(logoEfa);
		
		panelMostrar = new JPanel();
		panelMostrar.setBackground(new Color(102, 205, 170));
		panelMostrar.setBounds(0, 0, 948, 690);
		contentPane.add(panelMostrar);
		panelMostrar.setLayout(null);
		
		tituloPanelMostrar = new JLabel("MOSTRAR RESULTADOS");
		tituloPanelMostrar.setFont(new Font("Cambria", Font.BOLD, 60));
		tituloPanelMostrar.setBounds(133, 23, 670, 132);
		panelMostrar.add(tituloPanelMostrar);
		
		botonRespaña = new JButton("Resultado España\r\n");
		botonRespaña.setFont(new Font("Tahoma", Font.BOLD, 23));
		botonRespaña.setBounds(311, 183, 296, 97);
		panelMostrar.add(botonRespaña);
		
		logoMapaEspaña = new JLabel("");
		logoMapaEspaña.setBounds(122, 170, 160, 120);
		logoMapaEspaña.setIcon(new ImageIcon("imgsVotaciones/mapaEspaña.png"));
		panelMostrar.add(logoMapaEspaña);
		
		logoMapaEspaña2 = new JLabel("");
		logoMapaEspaña2.setBounds(644, 170, 160, 120);
		logoMapaEspaña2.setIcon(new ImageIcon("imgsVotaciones/mapaEspaña.png"));
		panelMostrar.add(logoMapaEspaña2);
		
		botonRcom = new JButton("Resultado Comunidades");
		botonRcom.setFont(new Font("Tahoma", Font.BOLD, 21));
		botonRcom.setBounds(311, 345, 296, 97);
		panelMostrar.add(botonRcom);
		
		logoMapaCom = new JLabel("");
		logoMapaCom.setBounds(122, 334, 160, 120);
		logoMapaCom.setIcon(new ImageIcon("imgsVotaciones/mapaComunidades2.png"));
		panelMostrar.add(logoMapaCom);
		
		logoMapaCom2 = new JLabel("");
		logoMapaCom2.setBounds(644, 334, 160, 120);
		logoMapaCom2.setIcon(new ImageIcon("imgsVotaciones/mapaComunidades2.png"));
		panelMostrar.add(logoMapaCom2);
		
		botonRRang = new JButton("Resultado Rangos Edad\r\n");
		botonRRang.setFont(new Font("Tahoma", Font.BOLD, 21));
		botonRRang.setBounds(311, 509, 296, 97);
		panelMostrar.add(botonRRang);
		
		logoRangos = new JLabel("");
		logoRangos.setBounds(122, 480, 160, 163);
		logoRangos.setIcon(new ImageIcon("imgsVotaciones/rangosEdad2.png"));
		panelMostrar.add(logoRangos);
		
		logoRangos2 = new JLabel("");
		logoRangos2.setBounds(644, 480, 160, 163);
		logoRangos2.setIcon(new ImageIcon("imgsVotaciones/rangosEdad2.png"));
		panelMostrar.add(logoRangos2);
	
		panelRespaña = new JPanel();
		panelRespaña.setBackground(new Color(102, 205, 170));
		panelRespaña.setBounds(0, 0, 948, 690);
		contentPane.add(panelRespaña);
		panelRespaña.setLayout(null);
		
		tituloPanelRespaña = new JLabel("RESULTADO A NIVEL PENINSULAR");
		tituloPanelRespaña.setFont(new Font("Cambria", Font.BOLD, 38));
		tituloPanelRespaña.setBounds(182, 10, 605, 100);
		panelRespaña.add(tituloPanelRespaña);
		
		logoMapaEspaña = new JLabel("");
		logoMapaEspaña.setBounds(10, 22, 160, 120);
		logoMapaEspaña.setIcon(new ImageIcon("imgsVotaciones/mapaEspaña.png"));
		panelRespaña.add(logoMapaEspaña);
		
		logoMapaEspaña2 = new JLabel("");
		logoMapaEspaña2.setBounds(766, 22, 160, 120);
		logoMapaEspaña2.setIcon(new ImageIcon("imgsVotaciones/mapaEspaña.png"));
		panelRespaña.add(logoMapaEspaña2);
		
		ganadorREspaña = new JLabel("GANADOR");
		ganadorREspaña.setHorizontalAlignment(SwingConstants.CENTER);
		ganadorREspaña.setFont(new Font("Tahoma", Font.BOLD, 30));
		ganadorREspaña.setBounds(636, 152, 200, 59);
		ganadorREspaña.setVisible(false);
		panelRespaña.add(ganadorREspaña);
		
		logoGanadorREspaña = new JLabel("");
		logoGanadorREspaña.setBounds(666, 201, 150, 150);
		logoGanadorREspaña.setVisible(false);
		panelRespaña.add(logoGanadorREspaña);
             
		presidenteREspaña = new JLabel("PRESIDENTE");
		presidenteREspaña.setHorizontalAlignment(SwingConstants.CENTER);
		presidenteREspaña.setFont(new Font("Tahoma", Font.BOLD, 30));
		presidenteREspaña.setBounds(640, 361, 220, 59);
		presidenteREspaña.setVisible(false);
		panelRespaña.add(presidenteREspaña);
		
		fotoPresidenteREspaña = new JLabel("");
		fotoPresidenteREspaña.setBounds(672, 426, 131, 220);
		fotoPresidenteREspaña.setVisible(false);
		fotoPresidenteREspaña.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelRespaña.add(fotoPresidenteREspaña);
		
		panelGraficoEsp = new ChartPanel(graficoEsp);
		panelGraficoEsp.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelGraficoEsp.setLocation(115, 136);
		panelGraficoEsp.setSize(490, 432);
		panelGraficoEsp.setVisible(true);;
		panelRespaña.add(panelGraficoEsp);
		
		botonatrasPREsp = new JButton("");
		botonatrasPREsp.setBounds(10, 580, 100, 100);
		panelRespaña.add(botonatrasPREsp);
		botonatrasPREsp.setBackground(new Color(102, 205, 170));
		botonatrasPREsp.setIcon(new ImageIcon("imgsVotaciones/flecha.png"));
		
		panelRcom = new JPanel();
		panelRcom.setBackground(new Color(102, 205, 170));
		panelRcom.setBounds(0, 0, 948, 690);
		contentPane.add(panelRcom);
		panelRcom.setLayout(null);
		
		tituloPanelRcom = new JLabel("RESULTADO POR COMUNIDADES");
		tituloPanelRcom.setFont(new Font("Cambria", Font.BOLD, 38));
		tituloPanelRcom.setBounds(184, 10, 620, 132);
		panelRcom.add(tituloPanelRcom);
		
		logoMapaCom = new JLabel("");
		logoMapaCom.setBounds(14, 10, 160, 120);
		logoMapaCom.setIcon(new ImageIcon("imgsVotaciones/mapaComunidades2.png"));
		panelRcom.add(logoMapaCom);
		
		logoMapaCom2 = new JLabel("");
		logoMapaCom2.setBounds(766, 10, 160, 120);
		logoMapaCom2.setIcon(new ImageIcon("imgsVotaciones/mapaComunidades2.png"));
		panelRcom.add(logoMapaCom2);
		
		panelRcomFiltroCom = new JPanel();
		panelRcomFiltroCom.setBorder(new MatteBorder(2, 1, 2, 2, (Color) new Color(0, 0, 0)));
		panelRcomFiltroCom.setBackground(new Color(102, 205, 170));
		panelRcomFiltroCom.setBounds(194, 115, 543, 145);
		panelRcom.add(panelRcomFiltroCom);
		panelRcomFiltroCom.setLayout(null);
		
		tituloMostrarRcom = new JLabel("ELIGE TU COMUNIDAD");
		tituloMostrarRcom.setBounds(35, 20, 234, 25);
		tituloMostrarRcom.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelRcomFiltroCom.add(tituloMostrarRcom);
		
		botonMostrarPanelRcom = new JButton("MOSTRAR");
		botonMostrarPanelRcom.setFont(new Font("Tahoma", Font.PLAIN, 30));
		botonMostrarPanelRcom.setBounds(183, 75, 170, 60);
		panelRcomFiltroCom.add(botonMostrarPanelRcom);
		
		comboBoxCom = new JComboBox();
		comboBoxCom.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboBoxCom.setBounds(291, 10, 223, 45);
		panelRcomFiltroCom.add(comboBoxCom);
		
		ganadorRcom = new JLabel("GANADOR");
		ganadorRcom.setHorizontalAlignment(SwingConstants.CENTER);
		ganadorRcom.setFont(new Font("Tahoma", Font.BOLD, 30));
		ganadorRcom.setBounds(684, 270, 231, 59);
		ganadorRcom.setVisible(false);
		panelRcom.add(ganadorRcom);
		
		logoGanadorRcom = new JLabel("");
		logoGanadorRcom.setBounds(730, 339, 150, 150);
		logoGanadorRcom.setVisible(false);
		panelRcom.add(logoGanadorRcom);
		
		panelGraficoCom = new ChartPanel(graficoCom);
		panelGraficoCom.setLocation(189, 270);
		panelGraficoCom.setSize(464, 392);
		panelGraficoCom.setVisible(false);
		panelRcom.add(panelGraficoCom);
		
		botonatrasPRCom = new JButton("");
		botonatrasPRCom.setBounds(10, 580, 100, 100);
		panelRcom.add(botonatrasPRCom);
		botonatrasPRCom.setIcon(new ImageIcon("imgsVotaciones/flecha.png"));
		botonatrasPRCom.setBackground(new Color(102, 205, 170));
		
		panelRRang = new JPanel();
		panelRRang.setBackground(new Color(102, 205, 170));
		panelRRang.setBounds(0, 0, 948, 690);
		contentPane.add(panelRRang);
		panelRRang.setLayout(null);
		
		tituloPanelRRang = new JLabel("RESULTADO POR RANGO DE EDAD");
		tituloPanelRRang.setBounds(170, 10, 612, 70);
		tituloPanelRRang.setFont(new Font("Cambria", Font.BOLD, 38));
		panelRRang.add(tituloPanelRRang);
		
		logoPanelRRang = new JLabel("");
		logoPanelRRang.setBounds(21, 10, 130, 132);
		logoPanelRRang.setIcon(new ImageIcon("imgsVotaciones/rangosEdad3.png"));
		panelRRang.add(logoPanelRRang);
		
		logoPanelRRang2 = new JLabel("");
		logoPanelRRang2.setBounds(792, 10, 130, 132);
		logoPanelRRang2.setIcon(new ImageIcon("imgsVotaciones/rangosEdad3.png"));
		panelRRang.add(logoPanelRRang2);
		
		panelRRangFiltro = new JPanel();
		panelRRangFiltro.setBorder(new MatteBorder(2, 1, 2, 2, (Color) new Color(0, 0, 0)));
		panelRRangFiltro.setBackground(new Color(102, 205, 170));
		panelRRangFiltro.setBounds(180, 81, 572, 139);
		panelRRang.add(panelRRangFiltro);
		panelRRangFiltro.setLayout(null);
		
		botonRRangFiltro18_25 = new JButton("ENTRE 18 Y 25 A\u00D1OS");
		botonRRangFiltro18_25.setFont(new Font("Tahoma", Font.PLAIN, 20));
		botonRRangFiltro18_25.setBounds(30, 10, 230, 50);
		panelRRangFiltro.add(botonRRangFiltro18_25);
		
		botonRRangFiltro26_40 = new JButton("ENTRE 26 Y 40 A\u00D1OS");
		botonRRangFiltro26_40.setFont(new Font("Tahoma", Font.PLAIN, 20));
		botonRRangFiltro26_40.setBounds(312, 10, 230, 50);
		panelRRangFiltro.add(botonRRangFiltro26_40);
		
		botonRRangFiltro41_65 = new JButton("ENTRE 41 Y 65 A\u00D1OS");
		botonRRangFiltro41_65.setFont(new Font("Tahoma", Font.PLAIN, 20));
		botonRRangFiltro41_65.setBounds(30, 80, 230, 50);
		panelRRangFiltro.add(botonRRangFiltro41_65);
		
		botonRRangFiltroMas66 = new JButton("M\u00C1S DE 66 A\u00D1OS");
		botonRRangFiltroMas66.setFont(new Font("Tahoma", Font.PLAIN, 20));
		botonRRangFiltroMas66.setBounds(312, 80, 230, 50);
		panelRRangFiltro.add(botonRRangFiltroMas66);
		
		ganadorRRang = new JLabel("GANADOR");
		ganadorRRang.setHorizontalAlignment(SwingConstants.CENTER);
		ganadorRRang.setFont(new Font("Tahoma", Font.BOLD, 30));
		ganadorRRang.setBounds(682, 317, 231, 59);
		ganadorRRang.setVisible(false);
		panelRRang.add(ganadorRRang);
		
		logoGanadorRRang = new JLabel("");
		logoGanadorRRang.setBounds(728, 386, 150, 150);
		logoGanadorRRang.setVisible(false);
		panelRRang.add(logoGanadorRRang);
		
		panelGraficoRang = new ChartPanel(graficoRang);
		panelGraficoRang.setLocation(185, 266);
		panelGraficoRang.setSize(468, 396);
		panelGraficoRang.setVisible(false);
		panelRRang.add(panelGraficoRang);
		
		botonatrasPRRang = new JButton("");
		botonatrasPRRang.setBounds(10, 580, 100, 100);
		panelRRang.add(botonatrasPRRang);
		botonatrasPRRang.setIcon(new ImageIcon("imgsVotaciones/flecha.png"));
		botonatrasPRRang.setBackground(new Color(102, 205, 170));

	}
}
