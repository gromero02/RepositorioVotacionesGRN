package Modelo;

import java.io.FileNotFoundException;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Principal {

	// Conexi�n a la base de datos
	public Connection createConnection() throws FileNotFoundException, IOException, ClassNotFoundException {
		Connection cn = null;

		try {

			Properties p = new Properties();
			p.load(new FileReader("resources/database.properties"));

			String driver = p.getProperty("database.driver");
			String url = p.getProperty("database.url");
			String user = p.getProperty("database.user");
			String password = p.getProperty("database.password");

			Class.forName(driver);
			cn = DriverManager.getConnection(url, user, password);
			cn.setAutoCommit(false);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cn;
	}

	// M�todo para desconectarnos de la base de datos
	public void disconnect(Connection cn) throws SQLException {

		try {

			if (null != cn) {
				cn.close();
				cn = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	// M�todo para obtener todos los datos del censo el cu�al se encuentra en la
	// base de datos
	public ArrayList<DatosComunidad> obtenerDatosCenso(Connection cn) {
		// Variables
		String consulta = "SELECT NOMBRE_COMUNIDAD, TOTAL_HABITANTES, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66 FROM PORCENTAJES_RANGOEDAD";

		ArrayList<DatosComunidad> datosComunidades = new ArrayList<DatosComunidad>();
		Statement st = null;
		ResultSet rs = null;

		try {

			st = cn.createStatement();
			rs = st.executeQuery(consulta);

			while (rs.next()) {
				DatosComunidad dc = new DatosComunidad();
				dc.setNombreC(rs.getString("NOMBRE_COMUNIDAD"));
				dc.setTotalHabitantes(rs.getInt("TOTAL_HABITANTES"));
				dc.setPorc18_25(rs.getInt("RANGO_18_25"));
				dc.setPorc26_40(rs.getInt("RANGO_26_40"));
				dc.setPorc41_65(rs.getInt("RANGO_41_65"));
				dc.setPorcmas_66(rs.getInt("RANGO_MAS_66"));

				datosComunidades.add(dc);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return datosComunidades;
	}

	// M�todo para calcular los votantes por cada rango
	public void calcularVotantes(ArrayList<DatosComunidad> datosComunidades) {
		// Variables
		int habT = 0;
		double nv_18_25, nv_26_40, nv_41_65, nv_mas_66, porc_18_25, porc_26_40, porc_41_65, porc_mas_66;

		// Con el bucle for lo que se pretende es recorrer el array datosComunidad
		// y asignar valor a sus atributos seg�n los votantes por cada rango
		for (int i = 0; i < datosComunidades.size(); i++) {
			habT = datosComunidades.get(i).getTotalHabitantes(); // Habitantes totales
			porc_18_25 = datosComunidades.get(i).getPorc18_25(); // Porcentaje de los votantes entre 18 y 25 a�os
			porc_26_40 = datosComunidades.get(i).getPorc26_40(); // Porcentaje de los votantes entre 26 y 40 a�os
			porc_41_65 = datosComunidades.get(i).getPorc41_65(); // Porcentaje de los votantes entre 40 y 65 a�os
			porc_mas_66 = datosComunidades.get(i).getPorcmas_66(); // Porcentaje de los votantes de m�s de 66 a�os

			// N�mero de votantes entre 18 y 25 a�os
			nv_18_25 = ((habT * (porc_18_25 / 100)) / 100000);

			// Si el resultado del c�lculo anterior es menor de 1, se le da el valor de 1
			// por defecto
			if (nv_18_25 < 0.5) {
				nv_18_25 = 1;
			}
			// Se almacena en el array el n�mero de votantes
			datosComunidades.get(i).setNumv18_25((int) Math.round(nv_18_25));

			// N�mero de votantes entre 26 y 40 a�os
			nv_26_40 = ((habT * (porc_26_40 / 100)) / 100000);

			// Si el resultado del c�lculo anterior es menor de 1, se le da el valor de 1
			// por defecto
			if (nv_26_40 < 0.5) {
				nv_26_40 = 1;
			}
			// Se almacena en el array el n�mero de votantes
			datosComunidades.get(i).setNumv26_40((int) Math.round(nv_26_40));

			// N�mero de votantes entre 41 y 65 a�os
			nv_41_65 = ((habT * (porc_41_65 / 100)) / 100000);

			// Si el resultado del c�lculo anterior es menor de 1, se le da el valor de 1
			// por defecto
			if (nv_41_65 < 0.5) {
				nv_41_65 = 1;
			}
			// Se almacena en el array el n�mero de votantes
			datosComunidades.get(i).setNumv41_65((int) Math.round(nv_41_65));

			// N�mero de votantes con m�s de 66 a�os
			nv_mas_66 = ((habT * (porc_mas_66 / 100)) / 100000);

			// Si el resultado del c�lculo anterior es menor de 1, se le da el valor de 1
			// por defecto
			if (nv_mas_66 < 0.5) {
				nv_mas_66 = 1;
			}
			// Se almacena en el array el n�mero de votantes
			datosComunidades.get(i).setNumvmas_66((int) Math.round(nv_mas_66));

		}
	}

	// M�todo para crear los hilos
	public int crearHilos(ArrayList<DatosComunidad> datosComunidades, ArrayList<String> nombresC, Urna urna) {

		// Variable para saber el total de hilos que se van a crear;
		int totalV = 0;

		try {

			for (int i = 0; i < datosComunidades.size(); i++) {
				// Creamos los votantes de 18 a 25 a�os
				for (int j = 0; j < datosComunidades.get(i).getNumv18_25(); j++) {
					Votante vot = new Votante(datosComunidades.get(i).getNombreC(), "18_25", urna);
					vot.start();
					totalV++;
				}
				// Creamos los votantes de 26 a 40 a�os
				for (int j = 0; j < datosComunidades.get(i).getNumv26_40(); j++) {
					Votante vot = new Votante(datosComunidades.get(i).getNombreC(), "26_40", urna);
					vot.start();
					totalV++;
				}
				// Creamos los votantes de 41 a 65 a�os
				for (int j = 0; j < datosComunidades.get(i).getNumv41_65(); j++) {
					Votante vot = new Votante(datosComunidades.get(i).getNombreC(), "41_65", urna);
					vot.start();
					totalV++;
				}
				// Creamos los votantes de m�s de 66 a�os
				for (int j = 0; j < datosComunidades.get(i).getNumvmas_66(); j++) {
					Votante vot = new Votante(datosComunidades.get(i).getNombreC(), "mas_66", urna);
					vot.start();
					totalV++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalV;
	}

	// Obetener todos los nombres de las comunidades aut�nomas
	public ArrayList<String> nombresComunidades(ArrayList<DatosComunidad> datosComunidades) {

		// Variables && Objetos
		ArrayList<String> nombres = new ArrayList<String>();

		try {
			// Completamos el array de nombres con los nombres obtenidos del array pasado
			// por par�metro
			for (int i = 0; i < datosComunidades.size(); i++) {
				nombres.add(datosComunidades.get(i).getNombreC());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return nombres;
	}

	// DATOS ESCRUTINIO GENERAL
		// Insert de los datos del escrutinio individual de la comunidad aut�noma
		public void insertEscrutinioIndividual(Connection cn, String[][] datosEscrutinio, int nFila) throws SQLException {
	
			// Variables && Objetos
			String consulta = "INSERT INTO VOTACION (NOMBRE_COMUNIDAD,PP_18_25,PSOE_18_25,PODEMOS_18_25,VOX_18_25,"
					+ "PP_26_40,PSOE_26_40,PODEMOS_26_40,VOX_26_40," + "PP_41_65,PSOE_41_65,PODEMOS_41_65,VOX_41_65,"
					+ "PP_MAS_66,PSOE_MAS_66,PODEMOS_MAS_66,VOX_MAS_66)" + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	
			PreparedStatement pe = null;
	
			try {
	
				pe = cn.prepareStatement(consulta);
	
				// Resuelve el parametro de los nombres de la comunidad
				pe.setString(1, datosEscrutinio[nFila][0]);
	
				for (int i = 1; i < 17; i++) {
					pe.setString(i + 1, datosEscrutinio[nFila][i]);
				}
	
				pe.executeUpdate();
				// COMMIT PARA QUE LOS DATOS SE REFLEJEN EN LA BD
				cn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				cn.rollback();
			} finally {
				if (pe != null) {
					try {
						pe.close();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
	
		}
	
		// Actualizaci�n de los datos del escrutinio individual de la comunidad aut�noma
		public void updateEscrutinioIndividual(Connection cn, String[][] datosEscrutinio) throws SQLException {
			// Variables && Objetos
			String consulta = "UPDATE VOTACION SET NOMBRE_COMUNIDAD=?,PP_18_25=?,PSOE_18_25=?,PODEMOS_18_25=?,"
					+ "VOX_18_25=?,PP_26_40=?,PSOE_26_40=?,PODEMOS_26_40=?,VOX_26_40=?,"
					+ "PP_41_65=?,PSOE_41_65=?,PODEMOS_41_65=?,VOX_41_65=?,"
					+ "PP_MAS_66=?,PSOE_MAS_66=?,PODEMOS_MAS_66=?,VOX_MAS_66=? WHERE ID_VOTACION = ?;";
			PreparedStatement pe = null;
	
			try {
	
				pe = cn.prepareStatement(consulta);
	
				// Objetivo del primer for:
				// - Da valor a la primera interrogaci�n, el valor de i es igual al n�mero de la
				// fila en la columna 0 de la matriz
				// - Da valor a la interrogaci�n 18, la cual da valor al id_votacion
				for (int i = 1; i < 20; i++) {
					pe.setString(1, datosEscrutinio[i][0]);
					pe.setInt(18, i);
	
					// Con el segundo for, doy valor a las dem�s interrogaciones, pasando los datos
					// de la matriz.
					for (int j = 1; j < 17; j++) {
						pe.setString(j + 1, datosEscrutinio[i][j]);
					}
	
					// Ejecuto el update
					pe.executeUpdate();
				}
	
				// COMMIT PARA QUE LOS DATOS SE REFLEJEN EN LA BD
				cn.commit();
	
			} catch (Exception e) {
				e.printStackTrace();
				cn.rollback();
			} finally {
				if (pe != null) {
					try {
						pe.close();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		}
		
		// M�todo para comprobar si existe la votacion seg�n el nombre
		public long existeVotacion(Connection cn, String nombreComunidad) throws SQLException {
	
			// Variables && Objetos
			long idV = 0;
			String consulta = "SELECT * FROM VOTACION WHERE NOMBRE_COMUNIDAD =?";
			PreparedStatement pe = null;
			ResultSet rs = null;
	
			try {
	
				pe = cn.prepareStatement(consulta);
				pe.setString(1, nombreComunidad);
				rs = pe.executeQuery();
	
				if (rs.next()) {
					idV = rs.getLong("ID_VOTACION");
				}
	
			} catch (SQLException e2) {
				e2.printStackTrace();
				throw e2;
			} finally {
				// cerramos todos los resources
				if (null != rs) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (null != pe) {
					try {
						pe.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return idV;
		}		
	//DATOS ESCRUTINIO POR RANGOS
		
		// Insert del escrutinio por rangos
		public void insertEscrutinioRango(Connection cn, ArrayList<String> esRangos) throws SQLException {
			// Variables && Objetos
			String consulta = "INSERT INTO ESCRUTINIO_RANGOS_Y_FINAL (R_18_25, R_26_40, R_41_65, R_MAS_66, GANADOR_GENERAL) VALUES (?,?,?,?,?)";
			PreparedStatement pe = null;

			try {

				pe = cn.prepareStatement(consulta);
				pe.setString(1, esRangos.get(0));
				pe.setString(2, esRangos.get(1));
				pe.setString(3, esRangos.get(2));
				pe.setString(4, esRangos.get(3));
				pe.setString(5, esRangos.get(4));

				pe.executeUpdate();

				// Commit
				cn.commit();

			} catch (Exception e) {
				e.printStackTrace();
				cn.rollback();
			} finally {
				if (null != pe) {
					try {
						pe.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		// Actualizaci�n de los datos del escrutinio por rangos
		public void updateEscrutinioRangos(Connection cn, ArrayList<String> esRangos) {
			// Variables && Objetos
			String consulta = "UPDATE ESCRUTINIO_RANGOS_Y_FINAL SET R_18_25=?,R_26_40=?, R_41_65=?, R_MAS_66=?, GANADOR_GENERAL=?";
			PreparedStatement pe = null;

			try {

				pe = cn.prepareStatement(consulta);
				pe.setString(1, esRangos.get(0));
				pe.setString(2, esRangos.get(1));
				pe.setString(3, esRangos.get(2));
				pe.setString(4, esRangos.get(3));
				pe.setString(5, esRangos.get(4));

				pe.executeUpdate();

				// Commit
				cn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != pe) {
					try {
						pe.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

	
	
	// M�todo para comprobar si existe el escrutinio por rangos en la tabla
	public long existeEscrutinioRangosyG(Connection cn) throws SQLException {

		// Variables && Objetos
		long idV = 0;
		String consulta = "SELECT * FROM ESCRUTINIO_RANGOS_Y_FINAL WHERE ID_ESCRUTINIO_RANGO = 1";
		Statement es = null;
		ResultSet rs = null;

		try {

			es = cn.createStatement();
			
			rs = es.executeQuery(consulta);

			if (rs.next()) {
				idV = rs.getLong("ID_ESCRUTINIO_RANGO");
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			throw e2;
		} finally {
			// cerramos todos los resources
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != es) {
				try {
					es.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return idV;
	}

	
//INSERT MASIVO
	public void insertMasivo(Connection cn, String[][] datosEscrutinio, ArrayList<String> esRangos)
			throws SQLException {

		try {
			//Gesti�n de los datos de la tabla votacion
				for (int i = 1; i < 20; i++) {
					// Si no existe se ejecuta el insert
					if (existeVotacion(cn, datosEscrutinio[i][0]) == 0) {
						insertEscrutinioIndividual(cn, datosEscrutinio, i);
						System.out.println("INSERTADO");
					} else {
						// Si existe, se actualiza
						System.out.println("ACTUALIZADO");
						updateEscrutinioIndividual(cn, datosEscrutinio);
					}
				}
				
			//Gesti�n de los datos de la tabla escrutinio_rangos
				if(existeEscrutinioRangosyG(cn) == 0) {
					insertEscrutinioRango(cn, esRangos);
					System.out.println("INSERTADO ESCRUTINIO POR RANGO");
				}else {
					updateEscrutinioRangos(cn, esRangos);
					System.out.println("ACATUALIZADO ESCRUTINIO POR RANGO");
				}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Ganadores seg�n los rangos y ganador final
	public ArrayList<String> generarEsRangoEsFinal(String[][] datosEscrutinio) {
		// Variables y Objetos
		String ganadorR = "";// Variable que almacena el ganador en cada iteraci�n del primer for
		int vtMaxR; // Variable que almacena el partido con m�s votos segun el rango
		int gnR = 0; // Variable que almacena la posicion del partido con m�s votos por rango	
		int votosPP = 0, votosPSOE = 0, votosPODEMOS = 0, votosVOX = 0; // Son los contadores que almacenan los votos de cada partido por rango
		
		String ganadorF = "";// Variable que almacena el ganador final
		int vtMaxF; // Variable que almacena el partido con m�s votos
		int gnF = 0; // Variable que almacena la posicion del partido con m�s votos
		int ppF= 0,psoeF=0,podemosF=0,voxF=0; //Estas variables van a contener el recuento para sacar el ganador final i no se reinician
		
		int[] recuento = new int[8]; // Almacena los valores de los contadores de los votos
		ArrayList<String> rangos = new ArrayList<String>(); // Almacena los distintos rangos
		ArrayList<String> resultado = new ArrayList<String>(); // Es el array que se retorna, el cual almacena en forma
																// de string los ganadores

		// En el array rangos, guardo los distintos rangos para despu�s hacer la
		// condicional

		rangos.add("18_25");
		rangos.add("26_40");
		rangos.add("41_65");
		rangos.add("mas_66");

		// El array tendra 4 iteraciones, una por cada rango de edad
		for (int v = 0; v < rangos.size(); v++) {

			// Con este for recorro las columnas del array
			for (int i = 0; i < 17; i++) {
				// Condici�n segun los rangos

				if (datosEscrutinio[0][i].equals("PP_" + rangos.get(v))) {
					for (int j = 1; j < 20; j++) {
						votosPP = votosPP + Integer.parseInt(datosEscrutinio[j][i]);//Votos por rango
						ppF = ppF + Integer.parseInt(datosEscrutinio[j][i]);//Votos generales
						recuento[0] = votosPP;
						recuento[4] = ppF;
					}
				}

				if (datosEscrutinio[0][i].equals("PSOE_" + rangos.get(v))) {
					for (int j = 1; j < 20; j++) {
						votosPSOE = votosPSOE + Integer.parseInt(datosEscrutinio[j][i]);//Votos por rango
						psoeF = psoeF + Integer.parseInt(datosEscrutinio[j][i]);//Votos generales
						recuento[1] = votosPSOE;
						recuento[5] = psoeF;
					}
				}

				if (datosEscrutinio[0][i].equals("PODEMOS_" + rangos.get(v))) {
					for (int j = 1; j < 20; j++) {
						votosPODEMOS = votosPODEMOS + Integer.parseInt(datosEscrutinio[j][i]);//Votos por rango
						podemosF = podemosF + Integer.parseInt(datosEscrutinio[j][i]);//Votos generales
						recuento[2] = votosPODEMOS;
						recuento[6] = podemosF;
					}
				}

				if (datosEscrutinio[0][i].equals("VOX_" + rangos.get(v))) {
					for (int j = 1; j < 20; j++) {
						votosVOX = votosVOX + Integer.parseInt(datosEscrutinio[j][i]);//Votos por rango
						voxF = voxF + Integer.parseInt(datosEscrutinio[j][i]);//Votos generales
						recuento[3] = votosVOX;
						recuento[7] = voxF;
					}
				}
			}

	
			// Una vez hecho el recuento, tengo que sacar el m�ximo de votos por rango
			vtMaxR = recuento[0];
			
			
			for (int i = 0; i < 4; i++) {
				//Por rango
			
				if (vtMaxR < recuento[i]) {
						vtMaxR = recuento[i];
						gnR = i;
				}
			}
					
			
			// Condicionamos ya que queremos saber el partido ganador por rango
			if (gnR == 0) {
				ganadorR = "PP";
			} else if (gnR == 1) {
				ganadorR = "PSOE";
			} else if (gnR == 2) {
				ganadorR = "PODEMOS";
			} else if (gnR == 3) {
				ganadorR = "VOX";
			}
			
			// A�ado el ganador por rango al array de resultado
			resultado.add(ganadorR);

			// Reiniciamos las variables que cuentan los votos
			votosPP = 0;
			votosPSOE = 0;
			votosPODEMOS = 0;
			votosVOX = 0;
			
		}//fin primer for
		
		// Condicionamos ya que queremos saber el partido ganador
		//Maximos votos generales
		vtMaxF = recuento[4];
		
		for(int i=4;i<8;i++) {
			//General
			if(i>4) {
				if (vtMaxF < recuento[i]) {
					vtMaxR = recuento[i];
					gnF = i;
				}
			}
		}
	
			if (gnF == 4) {
				ganadorF = "PP";
			} else if (gnF == 5) {
				ganadorF = "PSOE";
			} else if (gnF == 6) {
				ganadorF = "PODEMOS";
			} else if (gnF == 7) {
				ganadorF = "VOX";
			}
					
					
			//A�ado el ganador final en el ultimo campo del array resultado
				resultado.add(ganadorF);
				
		return resultado;
	}
	 
	//Main
		public static void main (String[]args) {
			//Variables && Objetos
				int totalV;
				
				String [][] datosEscrutinio = new String [20][17];
				ArrayList <DatosComunidad> datosComunidades = new ArrayList<DatosComunidad>();
				ArrayList <String> nombresC = new ArrayList<String>();
				ArrayList <String> esRangos = new ArrayList<String>();
				
				Principal p = new Principal();
				
				
				Connection cn = null;
				
					try {
						//Conexi�n
							cn = p.createConnection();
						//Recuperamos datos del censo, llamando al m�todo "obtenerDatosCenso"	
							datosComunidades = p.obtenerDatosCenso(cn);
						//Calculamos los votantes
							p.calcularVotantes(datosComunidades);
						//Obetener los nombres de las comunidades
							nombresC = p.nombresComunidades(datosComunidades);
						//Creaci�n de la urna
							Urna urna = new Urna(nombresC);
						//Crear los hilos
							totalV = p.crearHilos(datosComunidades, nombresC, urna);
							
						//Crear hilo escruitinio
							Escrutinio esc = new Escrutinio(totalV,urna);
							esc.start();
							esc.join();
							datosEscrutinio = esc.getDatosGen();
							
						//Obtener resultados segun los rangos
							esRangos = p.generarEsRangoEsFinal(datosEscrutinio);
							
						//Realiza el insert de todo los datos del escrutinio, en la tabla votacion
							p.insertMasivo(cn, datosEscrutinio,esRangos);
						
						
							
						//Imprimir datos
							//System.out.println(datosComunidades.toString());
							System.out.println("*************************************");
							System.out.println("	   Resultado segun rango ");
							System.out.println("18_25 a�os "+esRangos.get(0));
							System.out.println("26_40 a�os "+esRangos.get(1));
							System.out.println("41_65 a�os "+esRangos.get(2));
							System.out.println("mas_66 a�os "+esRangos.get(3));
							System.out.println("ganador elecciones: "+esRangos.get(4));
							System.out.println("*************************************");
							
						System.out.println(totalV);
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						try {
							p.disconnect(cn);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
		}
	
}
