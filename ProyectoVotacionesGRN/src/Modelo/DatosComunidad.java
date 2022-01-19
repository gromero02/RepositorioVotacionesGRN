package Modelo;

public class DatosComunidad {

	//Atributos
		private String nombreC;
		private int totalHabitantes, porc18_25, porc26_40, porc41_65, porcmas_66, numv18_25, numv26_40, numv41_65, numvmas_66;
	
	//Constructor vacío
		public DatosComunidad() {
			
		}
		
	//Getters && Setters
		public String getNombreC() {
			return nombreC;
		}

		public void setNombreC(String nombreC) {
			this.nombreC = nombreC;
		}

		public int getTotalHabitantes() {
			return totalHabitantes;
		}

		public void setTotalHabitantes(int totalHabitantes) {
			this.totalHabitantes = totalHabitantes;
		}

		public int getPorc18_25() {
			return porc18_25;
		}

		public void setPorc18_25(int porc18_25) {
			this.porc18_25 = porc18_25;
		}

		public int getPorc26_40() {
			return porc26_40;
		}

		public void setPorc26_40(int porc26_40) {
			this.porc26_40 = porc26_40;
		}

		public int getPorc41_65() {
			return porc41_65;
		}

		public void setPorc41_65(int porc41_65) {
			this.porc41_65 = porc41_65;
		}

		public int getPorcmas_66() {
			return porcmas_66;
		}

		public void setPorcmas_66(int porcmas_66) {
			this.porcmas_66 = porcmas_66;
		}

		public int getNumv18_25() {
			return numv18_25;
		}

		public void setNumv18_25(int numv18_25) {
			this.numv18_25 = numv18_25;
		}

		public int getNumv26_40() {
			return numv26_40;
		}

		public void setNumv26_40(int numv26_40) {
			this.numv26_40 = numv26_40;
		}

		public int getNumv41_65() {
			return numv41_65;
		}

		public void setNumv41_65(int numv41_65) {
			this.numv41_65 = numv41_65;
		}

		public int getNumvmas_66() {
			return numvmas_66;
		}

		public void setNumvmas_66(int numvmas_66) {
			this.numvmas_66 = numvmas_66;
		}

		//To String

			@Override
			public String toString() {
				return "DatosComunidad [nombreC=" + nombreC + ", totalHabitantes=" + totalHabitantes + ", porc18_25="
						+ porc18_25 + ", porc26_40=" + porc26_40 + ", porc41_65=" + porc41_65 + ", porcmas_66=" + porcmas_66
						+ ", numv18_25=" + numv18_25 + ", numv26_40=" + numv26_40 + ", numv41_65=" + numv41_65
						+ ", numvmas_66=" + numvmas_66 + "]"+"\n";
			}

}
