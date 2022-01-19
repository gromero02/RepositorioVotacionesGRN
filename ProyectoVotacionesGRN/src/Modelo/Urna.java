package Modelo;

import java.util.ArrayList;
import java.util.Arrays;

public class Urna {

	// Seccion critica
		private static Object object = new Object();
		
	//Array de nombres de comunidades
		private ArrayList <String> nombresC;
		
	//Matriz de los datos
		private String [][] datosGen = new String [20][17];
		
	//Constructor	
		public Urna (ArrayList <String> nombresC) {
			this.nombresC = nombresC;
			this.datosGen = configurarMatriz(datosGen,nombresC);
		}
		
	//Método para configurar la matriz con los campos principales
		public String[][] configurarMatriz (String [][] datosGen, ArrayList <String> nombresC) {
			
			
			//CABECERA DE LA MATRIZ
				datosGen[0][0] = "COMUNIDAD";	datosGen[0][1] = "PP_18_25"; datosGen[0][2] = "PSOE_18_25"; datosGen[0][3] = "PODEMOS_18_25"; datosGen[0][4] = "VOX_18_25"; 
				datosGen[0][5] = "PP_26_40"; datosGen[0][6] = "PSOE_26_40"; datosGen[0][7] = "PODEMOS_26_40"; datosGen[0][8] = "VOX_26_40";
				datosGen[0][9] = "PP_41_65"; datosGen[0][10] = "PSOE_41_65"; datosGen[0][11] = "PODEMOS_41_65"; datosGen[0][12] = "VOX_41_65";
				datosGen[0][13] = "PP_mas_66"; datosGen[0][14] = "PSOE_mas_66"; datosGen[0][15] = "PODEMOS_mas_66"; datosGen[0][16] = "VOX_mas_66";
			//Añadir un 0 a cada recuento
				for(int i = 1; i<20;i++) {
					for(int j = 1; j < 17; j++) {
						datosGen[i][j] = "0";
					}
				}
				
				//Columna de las comunidad
				
				for(int j = 0;j < nombresC.size();j++) {
						
						datosGen[j+1][0] = nombresC.get(j);
				}
															
			return datosGen;
		}
		
	//Método para rellenar la matriz según los votos de las personas entre 18 y 25 años
		public void votacion_18_25 (String nombreComunidad, String voto) {
			
				
				int contador=0;
				
				try {
					// Controlar el acceso al recurso compartido
					synchronized (object) {
						//Recorremos en la matriz las columnas que hacen referencia a los votos de las personas entre 18 y 20 años
						for(int i = 1; i<20;i++) {
							
							if(datosGen[i][0].equals(nombreComunidad)) {
								
								for(int j = 1; j<5;j++) {
									if(datosGen[0][j].equals(voto)) {
											contador = Integer.parseInt(datosGen[i][j]);
											contador ++;
											datosGen[i][j] = Integer.toString(contador);
										}
										
									}
								}
							}
						contador = 0;
						}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
		}
		
		//Método para rellenar la matriz según los votos de las personas entre 26 y 40 años
		public void votacion_26_40 (String nombreComunidad, String voto) {
			int contador = 0;
			try {
				// Controlar el acceso al recurso compartido
				synchronized (object) {
					//Recorremos en la matriz las columnas que hacen referencia a los votos de las personas entre 26 y 40 años
					for(int i = 1; i<20;i++) {
						
						if(datosGen[i][0].equals(nombreComunidad)) {
							
							for(int j = 5; j<9;j++) {
								if(datosGen[0][j].equals(voto)) {
									contador = Integer.parseInt(datosGen[i][j]);
									contador ++;
									datosGen[i][j] = Integer.toString(contador);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//Método para rellenar la matriz según los votos de las personas entre 41 y 65 años
		public void votacion_41_65 (String nombreComunidad, String voto) {
			int contador = 0;
			try {
				// Controlar el acceso al recurso compartido
				synchronized (object) {
					//Recorremos en la matriz las columnas que hacen referencia a los votos de las personas entre 41 y 65 años
					for(int i = 1; i<20;i++) {
						
						if(datosGen[i][0].equals(nombreComunidad)) {
							
							for(int j = 9; j<13;j++) {
								if(datosGen[0][j].equals(voto)) {
									contador = Integer.parseInt(datosGen[i][j]);
									contador ++;
									datosGen[i][j] = Integer.toString(contador);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//Método para rellenar la matriz según los votos de las personas con más de 66 años
		public void votacion_mas_66 (String nombreComunidad, String voto) {
			int contador = 0;
			try {
				// Controlar el acceso al recurso compartido
				synchronized (object) {
					//Recorremos en la matriz las columnas que hacen referencia a los votos de las personas con más de 66 años
					for(int i = 1; i<20;i++) {
						
						if(datosGen[i][0].equals(nombreComunidad)) {
							
							for(int j = 13; j<17;j++) {
								if(datosGen[0][j].equals(voto)) {
									contador = Integer.parseInt(datosGen[i][j]);
									contador ++;
									datosGen[i][j] = Integer.toString(contador);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		public String[][] getDatosGen() {
			return datosGen;
		}

		@Override
		public String toString() {
			return "Urna [datosGen=" + Arrays.toString(datosGen) + "]";
		}
		
		
	
}
