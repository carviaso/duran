package duran;

/**
 * @author emmanuel
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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
	
	public Object[] getDatosProducto(String nombreProducto) {
		int noProducto = 0;
		String nombreRealProducto = null;
		
		nombreProducto = nombreProducto.toUpperCase();
		nombreProducto = nombreProducto.replace("Á", "A");
		nombreProducto = nombreProducto.replace("É", "E");
		nombreProducto = nombreProducto.replace("Í", "I");
		nombreProducto = nombreProducto.replace("Ó", "O");
		nombreProducto = nombreProducto.replace("Ú", "U");
		
		try {
			rs = st.executeQuery("SELECT idproducto, nombreproducto FROM inventario WHERE UPPER(nombreproducto) LIKE '%" + nombreProducto + "%' ORDER BY nombreproducto ASC");
			if(rs.first()) {
			    noProducto = rs.getInt("idproducto");
			    nombreRealProducto = rs.getString("nombreproducto");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object[] datosproducto = {noProducto, nombreRealProducto};
		return datosproducto;
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
	
	public Object[] getProductoPorNumero(int noProducto) {
		String nombreProducto = null;
		double precio = 0;
		int cantidad = 0;
		Object[] producto = null;
		
		try {
			rs = st.executeQuery("SELECT nombreproducto, precioporkilo, cantidad FROM inventario WHERE idproducto = " + noProducto);
			if(rs.first()) {
			    nombreProducto = rs.getString("nombreproducto");
			    precio = rs.getDouble("precioporkilo");
			    cantidad = rs.getInt("cantidad");
			    DecimalFormat df = new DecimalFormat("#.00");
			    producto = new Object[] {noProducto, nombreProducto, df.format(precio), cantidad};
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return producto;
	}
	
	public Vector getProductosPorNombre(String nombreProducto) {
		int noProducto = 0;
		String nombreReal;
		double precio = 0;
		int cantidad = 0;
		Vector productos = new Vector();
		
		nombreProducto = nombreProducto.toUpperCase();
		nombreProducto = nombreProducto.replace("Á", "A");
		nombreProducto = nombreProducto.replace("É", "E");
		nombreProducto = nombreProducto.replace("Í", "I");
		nombreProducto = nombreProducto.replace("Ó", "O");
		nombreProducto = nombreProducto.replace("Ú", "U");
		
		try {
			rs = st.executeQuery("SELECT idproducto, nombreproducto, precioporkilo, cantidad FROM inventario WHERE UPPER(nombreproducto) LIKE '%" + nombreProducto + "%'");
			while(rs.next()) {
			    noProducto = rs.getInt("idproducto");
			    nombreReal = rs.getString("nombreproducto");
			    precio = rs.getDouble("precioporkilo");
			    cantidad = rs.getInt("cantidad");
			    DecimalFormat df = new DecimalFormat("#.00");
			    productos.add(new Object [] {noProducto, nombreReal, "$" + df.format(precio), cantidad});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productos;
	}
	
	public Vector getProductosPorPrecio(double precio) {
		int noProducto = 0;
        String nombreProducto = null;
		int cantidad = 0;
		Vector productos = new Vector();
		
		try {
			rs = st.executeQuery("SELECT idproducto, nombreproducto, cantidad FROM inventario WHERE precioporkilo = " + precio);
			while(rs.next()) {
			    noProducto = rs.getInt("idproducto");
			    nombreProducto = rs.getString("nombreproducto");
			    cantidad = rs.getInt("cantidad");
			    DecimalFormat df = new DecimalFormat("#.00");
			    productos.add(new Object [] {noProducto, nombreProducto, "$" + df.format(precio), cantidad});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productos;
	}
	
	public Vector getAllProductos() {
		int noProducto = 0, cantidad = 0;
		String nombreProducto = null;
		double precio = 0.0;
		Vector productos = new Vector();
		
		try {
			rs = st.executeQuery("SELECT idproducto, nombreproducto, precioporkilo, cantidad FROM inventario");
			while(rs.next()) {
			    noProducto = rs.getInt("idproducto");
			    nombreProducto = rs.getString("nombreproducto");
			    cantidad = rs.getInt("cantidad");
			    precio = rs.getDouble("precioporkilo");
			    DecimalFormat df = new DecimalFormat("#.00");
			    productos.add(new Object [] {noProducto, nombreProducto, "$" + df.format(precio), cantidad});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productos;
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
