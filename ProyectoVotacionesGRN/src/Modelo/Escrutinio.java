package Modelo;

import java.util.ArrayList;

public class Escrutinio extends Thread {

	// Atributos
	private String[][] datosGen = new String[20][17];
	private Urna urna = null;
	private int totalV;
	private ArrayList <String> recuentoRangos_ESP = new ArrayList<String>();
	private String[][] recuentoComunidades = new String[20][6];
	
	ArrayList<String> nombresC = new ArrayList<String>();
	private ArrayList<String> ganadores_Rangos_esGanadorFinal = new ArrayList<String>();
	private ArrayList<String> ganadores_PorComunidad = new ArrayList<String>();

	// Constructor
	public Escrutinio(int totalV, Urna urna, ArrayList<String>nombresC) throws InterruptedException {
		this.totalV = totalV;
		this.urna = urna;
		this.nombresC = nombresC;
		this.recuentoComunidades = this.configurarMatriz(recuentoComunidades, nombresC);
		
	}

	@Override
	public void run() {
		try {
			// recuperar los datos de las votaciones
			for (int i = 0; i < totalV / 5; i++) {

				datosGen = urna.getDatosGen();

			}
			for (int u = 0; u < 20; u++) {
				for (int v = 0; v < 17; v++) {
					System.out.print(datosGen[u][v].toString() + "--");
				}
				System.out.println();
				// Una vez recupera los datos duerme 5 segundos
				sleep(200);
			}
			
			this.ganadores_Rangos_esGanadorFinal = this.generarEsRangoEsFinal(this.datosGen);
			this.ganadores_PorComunidad = this.generarEsComunidad(this.datosGen);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Método para configurar la matriz con los campos principales
			public String[][] configurarMatriz (String [][] recuentoComunidades, ArrayList <String> nombresC) {
				
				
				//CABECERA DE LA MATRIZ
					datosGen[0][0] = "COMUNIDAD";	datosGen[0][1] = "PP"; datosGen[0][2] = "PSOE"; datosGen[0][3] = "PODEMOS"; 
					datosGen[0][4] = "VOX"; 		datosGen[0][5] = "GANADOR"; 
				//Añadir un 0 a cada recuento
					for(int i = 1; i<20;i++) {
						for(int j = 1; j < 6; j++) {
							recuentoComunidades[i][j] = "";
						}
					}
					
					//Columna de las comunidad
					
					for(int j = 0;j < nombresC.size();j++) {
							
							datosGen[j+1][0] = nombresC.get(j);
					}
																
				return datosGen;
			}
	

	// Ganadores según los rangos y ganador final de españa
	public ArrayList<String> generarEsRangoEsFinal(String[][] datosEscrutinio) throws InterruptedException {
		String variable = "";
		// Variables y Objetos
		String ganadorR = "";// Variable que almacena el ganador en cada iteración del primer for
		int vtMaxR, vtMaxRAux; // Variable que almacena el partido con más votos segun el rango
		int gnR = 0; // Variable que almacena la posicion del partido con más votos por rango
		int votosPP = 0, votosPSOE = 0, votosPODEMOS = 0, votosVOX = 0; // Son los contadores que almacenan los votos de
		// cada partido por rango

		String ganadorF = "";// Variable que almacena el ganador final de españa
		int vtMaxF, vtMaxFAux; // Variable que almacena el partido con más votos
		int gnF = 0; // Variable que almacena la posicion del partido con más votos
		int ppF = 0, psoeF = 0, podemosF = 0, voxF = 0; // Estas variables van a contener el recuento para sacar el
														// ganador final i no se reinician

		int[] recuento = new int[8]; // Almacena los valores de los contadores de los votos
		ArrayList<String> rangos = new ArrayList<String>(); // Almacena los distintos rangos
		ArrayList<String> resultado = new ArrayList<String>(); // Es el array que se retorna, el cual almacena en forma
																// de string los ganadores

		// En el array rangos, guardo los distintos rangos para después hacer la
		// condicional

		rangos.add("18_25");
		rangos.add("26_40");
		rangos.add("41_65");
		rangos.add("mas_66");

		// El array tendra 4 iteraciones, una por cada rango de edad
		for (int v = 0; v < rangos.size(); v++) {

			// Con este for recorro las columnas del array
			for (int i = 0; i < 17; i++) {
				// Condición segun los rangos

				if (datosEscrutinio[0][i].equals("PP_" + rangos.get(v))) {
					for (int j = 1; j < 20; j++) {
						votosPP = votosPP + Integer.parseInt(datosEscrutinio[j][i]);// Votos por rango

						ppF = ppF + Integer.parseInt(datosEscrutinio[j][i]);// Votos generales
						recuento[0] = votosPP;
						recuento[4] = ppF;
					}
				}

				if (datosEscrutinio[0][i].equals("PSOE_" + rangos.get(v))) {
					for (int j = 1; j < 20; j++) {
						votosPSOE = votosPSOE + Integer.parseInt(datosEscrutinio[j][i]);// Votos por rango

						psoeF = psoeF + Integer.parseInt(datosEscrutinio[j][i]);// Votos generales
						recuento[1] = votosPSOE;
						recuento[5] = psoeF;
					}
				}

				if (datosEscrutinio[0][i].equals("PODEMOS_" + rangos.get(v))) {
					for (int j = 1; j < 20; j++) {
						votosPODEMOS = votosPODEMOS + Integer.parseInt(datosEscrutinio[j][i]);// Votos por rango

						podemosF = podemosF + Integer.parseInt(datosEscrutinio[j][i]);// Votos generales
						recuento[2] = votosPODEMOS;
						recuento[6] = podemosF;
					}
				}

				if (datosEscrutinio[0][i].equals("VOX_" + rangos.get(v))) {
					for (int j = 1; j < 20; j++) {
						votosVOX = votosVOX + Integer.parseInt(datosEscrutinio[j][i]);// Votos por rango

						voxF = voxF + Integer.parseInt(datosEscrutinio[j][i]);// Votos generales
						recuento[3] = votosVOX;
						recuento[7] = voxF;
					}
				}
			}

			// Una vez hecho el recuento, tengo que sacar el máximo de votos por rango
			vtMaxR = recuento[0];
			ganadorR = this.sacarGanador(0);
			
			for (int h = 0; h < 4; h++) {
				// Por rango

				if (vtMaxR < recuento[h]) {
					vtMaxR = recuento[h];
					gnR = h;

					ganadorR = this.sacarGanador(h);

				} else
				// Desempate
				if (recuento[h] == vtMaxR && h > 0) {

					vtMaxRAux = 1 + (int) (Math.random() * 2);

					if (vtMaxRAux == 1) {
						recuento[h] = recuento[h] + 1;
						ganadorR = this.sacarGanador(h);

					} else {
						recuento[gnR] = recuento[gnR] + 1;
						ganadorR = this.sacarGanador(h);
					}
				}
			}
			
			//Almacenar en resultado de los votos
			this.recuentoRangos_ESP.add(Integer.toString(votosPP));
			this.recuentoRangos_ESP.add(Integer.toString(votosPSOE));
			this.recuentoRangos_ESP.add(Integer.toString(votosPODEMOS));
			this.recuentoRangos_ESP.add(Integer.toString(votosVOX));
			this.recuentoRangos_ESP.add(ganadorR);

			// Añado el ganador por rango al array de resultado
			resultado.add(ganadorR);

			// Reiniciamos las variables que cuentan los votos
			votosPP = 0;
			votosPSOE = 0;
			votosPODEMOS = 0;
			votosVOX = 0;

		} // fin primer for

		// Condicionamos ya que queremos saber el partido ganador
		// Maximos votos generales
		vtMaxF = recuento[4];
		ganadorF = this.sacarGanador(0);
		
		for (int p = 4; p < 8; p++) {

			if (vtMaxF < recuento[p]) {
				vtMaxF = recuento[p];
				gnF = p;

				ganadorF = this.sacarGanador(p);

			} else
			// Desempate
			if (recuento[p] == vtMaxF && p > 4) {

				vtMaxFAux = 1 + (int) (Math.random() * 2);

				if (vtMaxFAux == 1) {
					recuento[p] = recuento[p] + 1;
					
					ganadorF = this.sacarGanador(p);

				} else {
					recuento[gnF] = recuento[gnF] + 1;
					// Condicionamos ya que queremos saber el partido ganador por rango
					ganadorF = this.sacarGanador(p);
				}
			}
		}

		//Almacenar en resultado de los votos del ganador final
		this.recuentoRangos_ESP.add(Integer.toString(ppF));
		this.recuentoRangos_ESP.add(Integer.toString(psoeF));
		this.recuentoRangos_ESP.add(Integer.toString(podemosF));
		this.recuentoRangos_ESP.add(Integer.toString(voxF));
		this.recuentoRangos_ESP.add(ganadorF);
		
		// Añado el ganador final en el ultimo campo del array resultado
		resultado.add(ganadorF);

		return resultado;
	}

	// Ganador según la comunidad autónoma
	public ArrayList<String> generarEsComunidad(String[][] datosEscrutinio) {
		// Variables && Objetos
		String ganadorCom = "";// Variable que almacena el ganador en cada iteración del primer for
		int vtMaxCom, vtMaxCaux; // Variable que almacena el partido con más votos de cada comunidad
		int gnC = 0, gnCaux = 0; // Variable que almacena la posicion del partido con más votos por comunidad
		int votosPP = 0, votosPSOE = 0, votosPODEMOS = 0, votosVOX = 0; // Son los contadores que almacenan los votos de
																		// cada partido por comunidad

		ArrayList<String> ganadoresCom = new ArrayList<String>();// Array que va a contener los ganadores
		int[] recuento = new int[4]; // Almacena los valores de los contadores de los votos

		try {
			// Recorre las filas desde la 1 hasta la 19
			for (int i = 1; i < 20; i++) {

				// Recorre las columnas desde la 1 hasta la 16
				for (int j = 1; j < 17; j++) {

					// Votos al PP
					if (datosEscrutinio[0][j].equals("PP_18_25") || datosEscrutinio[0][j].equals("PP_26_40")
							|| datosEscrutinio[0][j].equals("PP_41_65") || datosEscrutinio[0][j].equals("PP_mas_66")) {

						// Incrementamos la variable de PP
						votosPP = votosPP + Integer.parseInt(datosEscrutinio[i][j]);
						recuento[0] = votosPP;
					} else
					// Votos al PSOE
					if (datosEscrutinio[0][j].equals("PSOE_18_25") || datosEscrutinio[0][j].equals("PSOE_26_40")
							|| datosEscrutinio[0][j].equals("PSOE_41_65")
							|| datosEscrutinio[0][j].equals("PSOE_mas_66")) {

						// Incrementamos la variable de PSOE
						votosPSOE = votosPSOE + Integer.parseInt(datosEscrutinio[i][j]);
						recuento[1] = votosPSOE;
					} else
					// Votos a PODEMOS
					if (datosEscrutinio[0][j].equals("PODEMOS_18_25") || datosEscrutinio[0][j].equals("PODEMOS_26_40")
							|| datosEscrutinio[0][j].equals("PODEMOS_41_65")
							|| datosEscrutinio[0][j].equals("PODEMOS_mas_66")) {

						// Incrementamos la variable de PODEMOS
						votosPODEMOS = votosPODEMOS + Integer.parseInt(datosEscrutinio[i][j]);
						recuento[2] = votosPODEMOS;
					} else
					// Votos a VOX
					if (datosEscrutinio[0][j].equals("VOX_18_25") || datosEscrutinio[0][j].equals("VOX_26_40")
							|| datosEscrutinio[0][j].equals("VOX_41_65")
							|| datosEscrutinio[0][j].equals("VOX_mas_66")) {

						// Incrementamos la variable de VOX
						votosVOX = votosVOX + Integer.parseInt(datosEscrutinio[i][j]);
						recuento[3] = votosVOX;
					}

				}

				// Una vez realizado el recuento, procedemos a sacar el ganador
				vtMaxCom = recuento[0];
				ganadorCom = this.sacarGanador(0);
				
				for (int v = 0; v < recuento.length; v++) {

					if (recuento[v] > vtMaxCom) {
						vtMaxCom = recuento[v];

						ganadorCom = this.sacarGanador(v);

						gnC = v;

					} else if (recuento[v] == vtMaxCom && v > 0) {

						vtMaxCaux = 1 + (int) (Math.random() * 2);

						if (vtMaxCaux == 1) {
							recuento[v] = recuento[v] + 1;

							ganadorCom = this.sacarGanador(v);

						} else {
							recuento[gnC] = recuento[gnC] + 1;

							ganadorCom = this.sacarGanador(v);
						}
					}
				}

				//Relleno el array del recuentoTotal por comunidad
				this.recuentoComunidades[i][1]= Integer.toString(votosPP);
				this.recuentoComunidades[i][2]= Integer.toString(votosPSOE);
				this.recuentoComunidades[i][3]= Integer.toString(votosPODEMOS);
				this.recuentoComunidades[i][4]= Integer.toString(votosVOX);
				this.recuentoComunidades[i][5]= ganadorCom;
				
				// Añado el ganador final en el ultimo campo del array resultado
				ganadoresCom.add(ganadorCom);

				// Restablecemos a 0 los contadores de votos y el array de recuento
				votosPP = 0;
				votosPSOE = 0;
				votosPODEMOS = 0;
				votosVOX = 0;
				recuento[0] = 0;
				recuento[1] = 0;
				recuento[2] = 0;
				recuento[3] = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ganadoresCom;
	}

	//Sacar partido ganador
	public String sacarGanador(int numero) {
		String partido="";
		
		if (numero == 0) {
			partido = "PP";
		} else if (numero == 1) {
			partido = "PSOE";
		} else if (numero == 2) {
			partido = "PODEMOS";
		} else if (numero == 3) {
			partido = "VOX";
		}else if (numero == 4) {
			partido = "PP";
		} else if (numero == 5) {
			partido = "PSOE";
		} else if (numero == 6) {
			partido = "PODEMOS";
		} else if (numero == 7) {
			partido = "VOX";
		}
		return partido;
	}
	
	// GETTERS

	// Recuperar Escrutinio
	public String[][] getDatosGen() {
		return datosGen;
	}
	
	public ArrayList<String> getGanadores_Rangos_esGanadorFinal() {
		return ganadores_Rangos_esGanadorFinal;
	}

	public ArrayList<String> getGanadores_PorComunidad() {
		return ganadores_PorComunidad;
	}

	public ArrayList<String> getRecuentoRangos_ESP() {
		return recuentoRangos_ESP;
	}

	public String[][] getRecuentoComunidades() {
		return recuentoComunidades;
	}

}
