package duran;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Inventario extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField tFNoProducto, tFNombreProducto, tFPrecio;
	private JButton btnBuscarPorNumero, btnBuscarPorNombre, btnBuscarPorPrecio;
	private JTable table;
	private DefaultTableModel dtm;
	
	/**
	 * Create the frame.
	 */
	public Inventario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 773, 419);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblConsultaDeInventario = new JLabel("Consulta de Inventario");
		lblConsultaDeInventario.setFont(new Font("Dialog", Font.BOLD, 20));
		lblConsultaDeInventario.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsultaDeInventario.setBounds(206, 12, 245, 15);
		panel.add(lblConsultaDeInventario);
		
		JLabel lblPorNoDe = new JLabel("Buscar Producto(s):");
		lblPorNoDe.setBounds(55, 70, 124, 15);
		panel.add(lblPorNoDe);
		
		JLabel lblPorNo = new JLabel("Por Número:");
		lblPorNo.setBounds(55, 115, 100, 15);
		panel.add(lblPorNo);
		
		tFNoProducto = new JTextField();
		tFNoProducto.setBounds(55, 135, 114, 19);
		panel.add(tFNoProducto);
		tFNoProducto.setColumns(10);
		
		JLabel lblPorNombre = new JLabel("Por nombre:");
		lblPorNombre.setBounds(206, 115, 100, 15);
		panel.add(lblPorNombre);
		
		tFNombreProducto = new JTextField();
		tFNombreProducto.setBounds(206, 135, 290, 19);
		panel.add(tFNombreProducto);
		tFNombreProducto.setColumns(10);
		
		JLabel lblPorCantidad = new JLabel("Por precio:");
		lblPorCantidad.setBounds(525, 115, 70, 15);
		panel.add(lblPorCantidad);
		
		tFPrecio = new JTextField();
		tFPrecio.setBounds(525, 135, 114, 19);
		panel.add(tFPrecio);
		tFPrecio.setColumns(10);
		
		btnBuscarPorNumero = new JButton("Buscar");
		btnBuscarPorNumero.addActionListener(this);
		btnBuscarPorNumero.setBounds(55, 166, 114, 25);
		panel.add(btnBuscarPorNumero);
		
		btnBuscarPorNombre = new JButton("Buscar");
		btnBuscarPorNombre.addActionListener(this);
		btnBuscarPorNombre.setBounds(206, 166, 290, 25);
		panel.add(btnBuscarPorNombre);
		
		btnBuscarPorPrecio = new JButton("Buscar");
		btnBuscarPorPrecio.addActionListener(this);
		btnBuscarPorPrecio.setBounds(525, 166, 114, 25);
		panel.add(btnBuscarPorPrecio);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 215, 577, 177);
		panel.add(scrollPane);
		dtm = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No. de producto", "Nombre del producto", "Precio", "Cantidad"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};		
		table = new JTable(dtm);
		table.setAutoCreateRowSorter(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(98);
		table.getColumnModel().getColumn(1).setPreferredWidth(184);
		table.getColumnModel().getColumn(2).setPreferredWidth(85);
		table.getColumnModel().getColumn(3).setPreferredWidth(82);
		scrollPane.setViewportView(table);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setIcon(new ImageIcon(Inventario.class.getResource("/com/sun/java/swing/plaf/gtk/resources/gtk-cancel-4.png")));
		btnSalir.setBounds(284, 452, 107, 25);
		contentPane.add(btnSalir);
	}
	
	public void limpiarTabla() {
		
		while(dtm.getRowCount() > 0) {
			dtm.removeRow(0);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
        if(e.getSource() == btnBuscarPorNumero) {
        	limpiarTabla();
        	
        	if(tFNoProducto.getText().length() > 0) {
        		int noProducto = Integer.parseInt(tFNoProducto.getText());
        		Conexion cnx = new Conexion();
        		Object[] producto = cnx.getProductoPorNumero(noProducto);
        		if(producto != null) {
        		    dtm.addRow(producto);
        		}
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "Debes introducir el número del producto", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        }
        else if(e.getSource() == btnBuscarPorNombre) {
        	limpiarTabla();
        	
        	if(tFNombreProducto.getText().length() > 0) {
        		String nombreProducto = tFNombreProducto.getText();
        		Conexion cnx = new Conexion();
        		Vector<Object[]> productos = cnx.getProductosPorNombre(nombreProducto);
        		
        		if(productos != null) {
        			for(Object[] producto : productos) {
            			dtm.addRow(producto);
            		}	
        		}
        		
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "Debes introducir el nombre del producto", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        }
        else if(e.getSource() == btnBuscarPorPrecio) {
        	limpiarTabla();
        	
        	if(tFPrecio.getText().length() > 0) {
        		double precio =Double.parseDouble(tFPrecio.getText());
        		Conexion cnx = new Conexion();
        		Vector<Object[]> productos = cnx.getProductosPorPrecio(precio);
        		
        		if(productos != null) {
        			for(Object[] producto : productos) {
            			dtm.addRow(producto);
            		}	
        		}
        		
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "Debes introducir el precio del producto", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        }
		
	}
}
