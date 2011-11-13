package duran;

/**
 * @author emmanuel
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
	
	private static Connection con;
	private static Statement st;
	
	public Conexion() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/duran", "root", "root");
			st = con.createStatement();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	public int getNoProducto(String nombreProducto) {
		int noProducto = -1;
		
		noProducto = 1;
		
		return noProducto;
	}
	
	public String getNombreProducto(int noProducto) {
		String nombreProducto = "";
		
		nombreProducto = "Lámina";
		
		return nombreProducto;
	}
	
	public double getPrecioProducto(int noProducto) {
		double precioProducto;
		
		precioProducto = 1000.00;
		
		return precioProducto;
	}
	
	public void insertarVenta(String nombreCliente, double totalVenta) {
		//System.out.println("Nombre del cliente "+ nombreCliente + ", total " + total);
		try {
			st.executeUpdate("INSERT INTO ventas (nombrecliente, totalventa) VALUES('" + nombreCliente + "', '" + totalVenta + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
