package duran;

/**
 * @author emmanuel
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Conexion {
	
	private static Connection con;
	private Statement st;
	private ResultSet rs;
	
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
	
	public Vector getDatosProducto(String nombreProducto) {
		int noProducto = 0;
		String nombreRealProducto = null;
		Vector resultado = new Vector();
		
		try {
			rs = st.executeQuery("SELECT idproducto, nombreproducto FROM inventario WHERE nombreproducto LIKE '%" + nombreProducto + "%'");
			if(rs.first()) {
			    noProducto = rs.getInt("idproducto");
			    nombreRealProducto = rs.getString("nombreproducto");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resultado.add(noProducto);
		resultado.add(nombreRealProducto);
		return resultado;
	}
	
	public String getNombreProducto(int noProducto) {
		String nombreProducto = null;
		
		try {
			rs = st.executeQuery("SELECT nombreproducto FROM inventario WHERE idproducto = " + noProducto);
			if(rs.first()) {
			    nombreProducto = rs.getString("nombreproducto");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return nombreProducto;
	}
	
	public double getPrecioProducto(int noProducto) {
		double precioProducto = 0;
		
		try {
			rs = st.executeQuery("SELECT precioporkilo FROM inventario WHERE idproducto = " + noProducto);
			if(rs.first()) {
			    precioProducto = rs.getDouble("precioporkilo");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return precioProducto;
	}
	
	public int getCantidadDisponible(int noProducto) {
		int cantidad = 0;
		
		try {
			rs = st.executeQuery("SELECT cantidad FROM inventario WHERE idproducto = " + noProducto);
			if(rs.first()) {
			    cantidad = rs.getInt("cantidad");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cantidad;
	}
	
	public void retirarDeInventario(int noProducto, int cantidad) {
		try {
			st.executeUpdate("UPDATE inventario SET cantidad = cantidad - " + cantidad + " WHERE idproducto = " + noProducto);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int insertarVenta(String nombreCliente, double totalVenta) {
		int idventa = -1;
		
		try {
			idventa = st.executeUpdate("INSERT INTO ventas (nombrecliente, totalventa) VALUES('" + nombreCliente + "', " + totalVenta + ")", Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idventa;
	}
	
	public void insertarVentaProducto(int idventa, int idproducto, int cantidad) {
		try {
			st.executeUpdate("INSERT INTO ventaproducto (idventa, idproducto, cantidad) VALUES(" + idventa + ", " + idproducto + ", " + cantidad + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int insertarCotizacion(String nombreCliente, double totalCotizacion) {
		int idcotizacion = -1;
		
		try {
			idcotizacion = st.executeUpdate("INSERT INTO ventas (nombrecliente, totalventa) VALUES('" + nombreCliente + "', " + totalCotizacion + ")", Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idcotizacion;
	}
	
	public void insertarCotizacionProducto(int idventa, int idproducto, int cantidad) {
		try {
			st.executeUpdate("INSERT INTO contizacionproducto (idventa, idproducto, cantidad) VALUES(" + idventa + ", " + idproducto + ", " + cantidad + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
