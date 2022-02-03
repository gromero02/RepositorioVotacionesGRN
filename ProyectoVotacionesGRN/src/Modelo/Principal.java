package Modelo;

import java.io.FileNotFoundException;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
public class Principal {
	
	// Conexión a la base de datos
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

	// Método para desconectarnos de la base de datos
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

	// Método para obtener todos los datos del censo el cual se encuentra en la base de datos
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

	// Método para calcular los votantes por cada rango
	public void calcularVotantes(ArrayList<DatosComunidad> datosComunidades) {
		// Variables
		int habT = 0;
		double nv_18_25, nv_26_40, nv_41_65, nv_mas_66, porc_18_25, porc_26_40, porc_41_65, porc_mas_66;

		// Con el bucle for lo que se pretende es recorrer el array datosComunidad
		// y asignar valor a sus atributos según los votantes por cada rango
		for (int i = 0; i < datosComunidades.size(); i++) {
			habT = datosComunidades.get(i).getTotalHabitantes(); // Habitantes totales
			porc_18_25 = datosComunidades.get(i).getPorc18_25(); // Porcentaje de los votantes entre 18 y 25 años
			porc_26_40 = datosComunidades.get(i).getPorc26_40(); // Porcentaje de los votantes entre 26 y 40 años
			porc_41_65 = datosComunidades.get(i).getPorc41_65(); // Porcentaje de los votantes entre 40 y 65 años
			porc_mas_66 = datosComunidades.get(i).getPorcmas_66(); // Porcentaje de los votantes de más de 66 años

			// Número de votantes entre 18 y 25 años
			nv_18_25 = ((habT * (porc_18_25 / 100)) / 100000);

			// Si el resultado del cálculo anterior es menor de 1, se le da el valor de 1
			// por defecto
			if (nv_18_25 < 0.5) {
				nv_18_25 = 1;
			}
			// Se almacena en el array el número de votantes
			datosComunidades.get(i).setNumv18_25((int) Math.round(nv_18_25));

			// Número de votantes entre 26 y 40 años
			nv_26_40 = ((habT * (porc_26_40 / 100)) / 100000);

			// Si el resultado del cálculo anterior es menor de 1, se le da el valor de 1
			// por defecto
			if (nv_26_40 < 0.5) {
				nv_26_40 = 1;
			}
			// Se almacena en el array el número de votantes
			datosComunidades.get(i).setNumv26_40((int) Math.round(nv_26_40));

			// Número de votantes entre 41 y 65 años
			nv_41_65 = ((habT * (porc_41_65 / 100)) / 100000);

			// Si el resultado del cálculo anterior es menor de 1, se le da el valor de 1
			// por defecto
			if (nv_41_65 < 0.5) {
				nv_41_65 = 1;
			}
			// Se almacena en el array el número de votantes
			datosComunidades.get(i).setNumv41_65((int) Math.round(nv_41_65));

			// Número de votantes con más de 66 años
			nv_mas_66 = ((habT * (porc_mas_66 / 100)) / 100000);

			// Si el resultado del cálculo anterior es menor de 1, se le da el valor de 1
			// por defecto
			if (nv_mas_66 < 0.5) {
				nv_mas_66 = 1;
			}
			// Se almacena en el array el número de votantes
			datosComunidades.get(i).setNumvmas_66((int) Math.round(nv_mas_66));

		}
	}

	// Método para crear los hilos
	public int crearHilos(ArrayList<DatosComunidad> datosComunidades, ArrayList<String> nombresC, Urna urna) {

		// Variable para saber el total de hilos que se van a crear;
		int totalV = 0;

		try {

			for (int i = 0; i < datosComunidades.size(); i++) {
				// Creamos los votantes de 18 a 25 años
				for (int j = 0; j < datosComunidades.get(i).getNumv18_25(); j++) {
					Votante vot = new Votante(datosComunidades.get(i).getNombreC(), "18_25", urna);
					vot.start();
					totalV++;
				}
				// Creamos los votantes de 26 a 40 años
				for (int j = 0; j < datosComunidades.get(i).getNumv26_40(); j++) {
					Votante vot = new Votante(datosComunidades.get(i).getNombreC(), "26_40", urna);
					vot.start();
					totalV++;
				}
				// Creamos los votantes de 41 a 65 años
				for (int j = 0; j < datosComunidades.get(i).getNumv41_65(); j++) {
					Votante vot = new Votante(datosComunidades.get(i).getNombreC(), "41_65", urna);
					vot.start();
					totalV++;
				}
				// Creamos los votantes de más de 66 años
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

	// Obetener todos los nombres de las comunidades autónomas
	public ArrayList<String> nombresComunidades(ArrayList<DatosComunidad> datosComunidades) {

		// Variables && Objetos
		ArrayList<String> nombres = new ArrayList<String>();

		try {
			// Completamos el array de nombres con los nombres obtenidos del array pasado
			// por parámetro
			for (int i = 0; i < datosComunidades.size(); i++) {
				nombres.add(datosComunidades.get(i).getNombreC());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return nombres;
	}

	// DATOS ESCRUTINIO GENERAL
	// Insert de los datos del escrutinio individual de la comunidad autónoma
	public void insertEscrutinioIndividual(Connection cn, String[][] datosEscrutinio, int nFila, String ganadorCom ) throws SQLException {

		// Variables && Objetos
		String consulta = "INSERT INTO VOTACION (NOMBRE_COMUNIDAD,PP_18_25,PSOE_18_25,PODEMOS_18_25,VOX_18_25,"
				+ "PP_26_40,PSOE_26_40,PODEMOS_26_40,VOX_26_40,PP_41_65,PSOE_41_65,PODEMOS_41_65,VOX_41_65,"
				+ "PP_MAS_66,PSOE_MAS_66,PODEMOS_MAS_66,VOX_MAS_66,GANADOR)" + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

		PreparedStatement pe = null;

		try {

			pe = cn.prepareStatement(consulta);

			// Resuelve el parametro de los nombres de la comunidad
			pe.setString(1, datosEscrutinio[nFila][0]);

			for (int i = 1; i < 17; i++) {
				pe.setString(i + 1, datosEscrutinio[nFila][i]);
			}
			// Resuelve el parametro del ganador de la comunidad
			pe.setString(18, ganadorCom);
			
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

	// Actualización de los datos del escrutinio individual de la comunidad autónoma
	public void updateEscrutinioIndividual(Connection cn, String[][] datosEscrutinio, ArrayList<String> esComunidades) throws SQLException {
		// Variables && Objetos
		String consulta = "UPDATE VOTACION SET NOMBRE_COMUNIDAD=?,PP_18_25=?,PSOE_18_25=?,PODEMOS_18_25=?,"
				+ "VOX_18_25=?,PP_26_40=?,PSOE_26_40=?,PODEMOS_26_40=?,VOX_26_40=?,"
				+ "PP_41_65=?,PSOE_41_65=?,PODEMOS_41_65=?,VOX_41_65=?,"
				+ "PP_MAS_66=?,PSOE_MAS_66=?,PODEMOS_MAS_66=?,VOX_MAS_66=?,GANADOR = ? WHERE ID_VOTACION = ?;";
		PreparedStatement pe = null;

		try {

			pe = cn.prepareStatement(consulta);

			// Objetivo del primer for:
			// - Da valor a la primera interrogación, el valor de i es igual al número de la
			// fila en la columna 0 de la matriz
			// - Da valor a la interrogación 18, la cual da valor al id_votacion
			for (int i = 1; i < 20; i++) {
				pe.setString(1, datosEscrutinio[i][0]);
				pe.setString(18, esComunidades.get(i-1));
				pe.setInt(19, i);

				// Con el segundo for, doy valor a las demás interrogaciones, pasando los datos
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

	// Método para comprobar si existe la votacion según el nombre
	public long existeEscrutinioIndividual(Connection cn, String nombreComunidad) throws SQLException {

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
	public void insertEscrutinioRangosyG(Connection cn, ArrayList<String> esRangos) throws SQLException {
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

	// Actualización de los datos del escrutinio por rangos
	public void updateEscrutinioRangosyG(Connection cn, ArrayList<String> esRangos) {
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

	// Método para comprobar si existe el escrutinio por rangos en la tabla
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
	
	// INSERT MASIVO
	public void insertMasivo(Connection cn, String[][] datosEscrutinio, ArrayList<String> esRangos, ArrayList<String> esComunidades)
			throws SQLException {

		try {
			// Gestión de los datos de la tabla votacion
			for (int i = 1; i < 20; i++) {
				// Si no existe se ejecuta el insert
				if (existeEscrutinioIndividual(cn, datosEscrutinio[i][0]) == 0) {
					insertEscrutinioIndividual(cn, datosEscrutinio, i, esComunidades.get(i-1));
					System.out.println("INSERTADO");
				} else {
					// Si existe, se actualiza
					System.out.println("ACTUALIZADO");
					updateEscrutinioIndividual(cn, datosEscrutinio, esComunidades);
				}
			}

			// Gestión de los datos de la tabla escrutinio_rangos
			if (existeEscrutinioRangosyG(cn) == 0) {
				insertEscrutinioRangosyG(cn, esRangos);
				System.out.println("INSERTADO ESCRUTINIO POR RANGO");
			} else {
				updateEscrutinioRangosyG(cn, esRangos);
				System.out.println("ACTUALIZADO ESCRUTINIO POR RANGO");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
