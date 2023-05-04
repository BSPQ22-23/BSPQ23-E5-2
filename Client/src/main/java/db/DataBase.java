package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
public static Connection con;
	
	public static Connection initDB() {
		 con = null;
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost/HotelManagementDB","spq","spq");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		 return con;
	}
	
	/**
	 * Usamos este metodo para cerrar la conexi�n de la base de datos
	 * @param con la conexion  la cerramos
	 *
	 */
		/** 
		 */
		public static void closeBD(Connection con) {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/**
		 * Metodo que crea las tablas necesarias en la BBDD
		 * 
		 * @param con Conexion
		 */
		public static void crearTablas(Connection con) {
			String sql1 = "CREATE TABLE IF NOT EXISTS Bill (id  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, issueDate VARCHAR(20), services VARCHAR(20), billTotal DOUBLE(5), idbooking INTEGER(3),FOREIGN KEY(idbooking) REFERENCES Booking(id) ON DELETE CASCADE);";
			String sql2 = "CREATE TABLE IF NOT EXISTS Booking (id  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, checkinDate VARCHAR(20), checkoutDate VARCHAR(20),guests VARCHAR(20), idroom INTEGER(3),FOREIGN KEY(idroom)REFERENCES Room(roomNumber) ON DELETE CASCADE);";
			String sql3 = "CREATE TABLE IF NOT EXISTS Room (roomNumber  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, type VARCHAR(20), numMaxGuests INTEGER(4),spaceInMeters INTEGER(4));";
			String sql4 = "CREATE TABLE IF NOT EXISTS Guest (dni VARCHAR PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR(20), surname VARCHAR(20),nick VARCHAR(20),password VARCHAR(20),age INTEGER(5),cityOfP VARCHAR(20));";
			String sql5 = "CREATE TABLE IF NOT EXISTS Service (name  VARCHAR PRIMARY KEY AUTOINCREMENT NOT NULL, description VARCHAR(20), price VARCHAR(20),guests VARCHAR(20));";
			String sql6 = "CREATE TABLE IF NOT EXISTS User (username VARCHAR PRIMARY KEY AUTOINCREMENT NOT NULL, email VARCHAR(20), isHotelOwner BOOLEAN, idguest INTEGER(3),FOREIGN KEY(idguest) REFERENCES Guest(id) ON DELETE CASCADE);";
			Statement st = null;

			try {
				st = con.createStatement();
				st.executeUpdate(sql1);
				st.executeUpdate(sql2);
				st.executeUpdate(sql3);
				st.executeUpdate(sql4);
				st.executeUpdate(sql5);
				st.executeUpdate(sql6);
						
			} catch (Exception e) {
			} finally {
				if (st != null) {
					try {
						st.close();
					} catch (Exception e) {
					}
				}
			}
		}

}
