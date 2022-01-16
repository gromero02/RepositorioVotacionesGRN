package Modelo;

import java.io.FileNotFoundException;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Principal {

	//Conexión a la base de datos
		public Connection createConnection() throws FileNotFoundException, IOException, ClassNotFoundException {
			Connection cn = null;
			
			try {
				
				Properties p = new Properties();
				p.load( new FileReader("resources/database.properties"));
				
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
	
	//Método para desconectarnos de la base de datos	
		public void disconnect(Connection cn) throws SQLException{
			
			try {
				
				if(null != cn) {
					cn.close();
					cn = null;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
			
		}
		
	//Método para obtener todos los datos del censo el cuçal se encuentra en la base de datos	
		public ArrayList<DatosComunidad> obtenerDatosCenso (Connection cn) {
			//Variables
				String consulta = "SELECT NOMBRE_COMUNIDAD, TOTAL_HABITANTES, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66 FROM PORCENTAJES_RANGOEDAD";
			
				ArrayList <DatosComunidad> datosComunidades = new ArrayList<DatosComunidad>();
				Statement st = null;
				ResultSet rs = null;
				
				try {
					
					st = cn.createStatement();
					rs = st.executeQuery(consulta);
					 
					while(rs.next()) {
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
				}finally {
					if(st != null) {
						try {
							st.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
					if(rs != null) {
						try {
							rs.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			
				return datosComunidades;
		}
		
	//Método para calcular los votantes por cada rango	
		public void calcularVotantes (ArrayList <DatosComunidad> datosComunidades) {
			//Variables
				int habT = 0;
				double nv_18_25, nv_26_40, nv_41_65, nv_mas_66, porc_18_25, porc_26_40, porc_41_65, porc_mas_66;
				
			//Con el bucle for lo que se pretende es recorrer el array datosComunidad 
				//y asignar valor a sus atributos según los votantes por cada rango
					for(int i = 0; i < datosComunidades.size(); i++) {
						habT = datosComunidades.get(i).getTotalHabitantes(); //Habitantes totales
						porc_18_25 = datosComunidades.get(i).getPorc18_25(); //Porcentaje de los votantes entre 18 y 25 años
						porc_26_40 = datosComunidades.get(i).getPorc26_40(); //Porcentaje de los votantes entre 26 y 40 años
						porc_41_65 = datosComunidades.get(i).getPorc41_65(); //Porcentaje de los votantes entre 40 y 65 años
						porc_mas_66 = datosComunidades.get(i).getPorcmas_66(); //Porcentaje de los votantes de más de 66 años
						
							//Número de votantes entre 18 y 25 años
								nv_18_25 = ((habT*(porc_18_25/100))/100000);
								
								datosComunidades.get(i).setNumv18_25((int) Math.round(nv_18_25));
						
							//Número de votantes entre 26 y 40 años
								nv_26_40 = ((habT*(porc_26_40/100))/100000);
								
								datosComunidades.get(i).setNumv26_40((int) Math.round(nv_26_40));
						
							//Número de votantes entre 41 y 65 años
								nv_41_65 = ((habT*(porc_41_65/100))/100000);
							
								datosComunidades.get(i).setNumv41_65((int) Math.round(nv_41_65));
						
							//Número de votantes con más de 66 años
								nv_mas_66 = ((habT*(porc_mas_66/100))/100000);
								
								datosComunidades.get(i).setNumvmas_66((int) Math.round(nv_mas_66));
						
					}
		}
		
		
	//Main
		public static void main (String[]args) {
			//Variables && Objetos
				ArrayList <DatosComunidad> datosComunidades = new ArrayList<DatosComunidad>();
				Principal p = new Principal();
				
				Connection cn = null;
				
					try {
						//Conexión
							cn = p.createConnection();
						//Recuperamos datos del censo, llamando al método "obtenerDatosCenso"	
							datosComunidades = p.obtenerDatosCenso(cn);
						//Calculamos los votantes
							p.calcularVotantes(datosComunidades);
							
						System.out.println(datosComunidades.toString());
						
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
