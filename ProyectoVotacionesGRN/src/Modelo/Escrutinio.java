package Modelo;

public class Escrutinio extends Thread{

	//Atributos
		private String [][] datosGen = new String [20][17];
		private Urna urna = null;
		private int totalV;
		
	//Constructor
		public Escrutinio (int totalV, Urna urna) {
			this.totalV = totalV;
			this.urna = urna;
		}

		@Override
		public void run() {
			try {
				//recuperar los datos de las votaciones
				for(int i=0;i<totalV/5;i++) {
				
					datosGen = urna.getDatosGen();
				
				}
				for(int u = 0; u<20;u++) {
					for(int v = 0; v < 17; v++) {
						System.out.print(datosGen[u][v].toString()+"--");
					}
					System.out.println();
					//Una vez recupera los datos duerme 5 segundos
						sleep(200);	
				}
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	//Recuperar Escrutinio
		public String[][] getDatosGen() {
			return datosGen;
		}
	
	
}
