package Modelo;

public class Votante extends Thread{

	//Atributos
		private String nombreComunidad, rangoEdad;
		private String voto;
		private int valeatorio;
		private Urna urna = null;
		
	//Constructor
		public Votante (String nombreComunidad, String rangoEdad,Urna urna) {
			this.nombreComunidad = nombreComunidad;
			this.rangoEdad = rangoEdad;
			this.valeatorio = 0;
			this.voto = "";
			this.urna = urna;
		}

	//Método Run
		@Override
		public void run() {
			try {
				//Crear número aleatorio
				valeatorio = 0+(int)(Math.random()*101);
				voto = resolverVoto(valeatorio);
				//Una vez vota, duerme 1 segundo	
				sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
		//Método para resolver el voto
			public String resolverVoto (int valeatorio) {
				//Variables
					String vt = "";
				
					try {
						//Comprobar el rango de edad ya que según el rango de edad, la equivalencia de valeatorio puede cambiar
						//Rango entre 18 y 25 años incluidos
							if(rangoEdad.equals("18_25")) {
								//Crear las equivalencias según el valor de valeatorio
									if(valeatorio >= 0 && valeatorio <= 30) {
										vt = "PP_18_25";
									}else if(valeatorio >= 31 && valeatorio <= 50) {
										vt = "PSOE_18_25";
									}else if(valeatorio >= 51 && valeatorio <= 70) {
										vt = "PODEMOS_18_25";
									}else if(valeatorio >= 71 && valeatorio <= 100) {
										vt = "VOX_18_25";
									}
							//Llamo al método para contabilizar el voto
								urna.votacion_18_25(nombreComunidad, vt);
								
						//Rango entre 26 y 40 años incluidos	
							}else if(rangoEdad.equals("26_40")) {
								//Crear las equivalencias según el valor de valeatorio
								if(valeatorio >= 0 && valeatorio <= 20) {
									vt = "PP_26_40";
								}else if(valeatorio >= 21 && valeatorio <= 55) {
									vt = "PSOE_26_40";
								}else if(valeatorio >= 56 && valeatorio <= 85) {
									vt = "PODEMOS_26_40";
								}else if(valeatorio >= 86 && valeatorio <= 100) {
									vt = "VOX_26_40";
								}
							//Llamo al método para contabilizar el voto
								urna.votacion_26_40(nombreComunidad, vt);
								
						//Rango entre 41 y 65 años incluidos	
							}else if(rangoEdad.equals("41_65")) {
								//Crear las equivalencias según el valor de valeatorio
								if(valeatorio >= 0 && valeatorio <= 10) {
									vt = "PP_41_65";
								}else if(valeatorio >= 10 && valeatorio <= 55) {
									vt = "PSOE_41_65";
								}else if(valeatorio >= 56 && valeatorio <= 90) {
									vt = "PODEMOS_41_65";
								}else if(valeatorio >= 91 && valeatorio <= 100) {
									vt = "VOX_41_65";
								}
							//Llamo al método para contabilizar el voto
								urna.votacion_41_65(nombreComunidad, vt);
								
						//Rango de más de 66 años incluidos
							}else if(rangoEdad.equals("mas_66")) {
								//Crear las equivalencias según el valor de valeatorio
								if(valeatorio >= 0 && valeatorio <= 25) {
									vt = "PP_mas_66";
								}else if(valeatorio >= 26 && valeatorio <= 60) {
									vt = "PSOE_mas_66";
								}else if(valeatorio >= 61 && valeatorio <= 95) {
									vt = "PODEMOS_mas_66";
								}else if(valeatorio >= 96 && valeatorio <= 100) {
									vt = "VOX_mas_66";
								}
							//Llamo al método para contabilizar el voto
								urna.votacion_mas_66(nombreComunidad, vt);
							}
								
					} catch (Exception e) {
						e.printStackTrace();
					}
				
				
				return vt;
			}
			
}
