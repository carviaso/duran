package duran;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

import javax.swing.ListSelectionModel;

public class Cotizaciones extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField tFNombreCliente, tFNoProducto, tFNombreProducto, tFCantidad;
	private DefaultTableModel dtm;
	private JTable table;
	private JButton btnAgregar, btnEliminar, btnRegistrar, btnCancelar;
	private JLabel lblTotalCotizacion;
	private double totalCotizacion = 0.00;

	/**
	 * Create the frame.
	 */
	public Cotizaciones() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 790, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblRealizarUnaVenta = new JLabel("Realizar una cotización");
		lblRealizarUnaVenta.setHorizontalAlignment(SwingConstants.CENTER);
		lblRealizarUnaVenta.setFont(new Font("Dialog", Font.BOLD, 20));
		lblRealizarUnaVenta.setBounds(219, 12, 246, 15);
		contentPane.add(lblRealizarUnaVenta);

		JLabel lblNombreDelCliente = new JLabel("Nombre del cliente:");
		lblNombreDelCliente.setBounds(65, 70, 120, 15);
		contentPane.add(lblNombreDelCliente);

		tFNombreCliente = new JTextField();
		tFNombreCliente.setBounds(220, 68, 400, 19);
		contentPane.add(tFNombreCliente);
		tFNombreCliente.setColumns(10);

		JLabel lblAgregarProducto = new JLabel("Agregar producto:");
		lblAgregarProducto.setBounds(65, 135, 120, 15);
		contentPane.add(lblAgregarProducto);

		JLabel lblPorNmero = new JLabel("Por número:");
		lblPorNmero.setBounds(65, 172, 86, 15);
		contentPane.add(lblPorNmero);

		JLabel lblPorNombre = new JLabel("Por nombre:");
		lblPorNombre.setBounds(219, 172, 86, 15);
		contentPane.add(lblPorNombre);

		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(506, 172, 61, 15);
		contentPane.add(lblCantidad);

		tFNoProducto = new JTextField();
		tFNoProducto.setBounds(65, 200, 114, 19);
		contentPane.add(tFNoProducto);
		tFNoProducto.setColumns(10);

		tFNombreProducto = new JTextField();
		tFNombreProducto.setBounds(219, 200, 240, 19);
		contentPane.add(tFNombreProducto);
		tFNombreProducto.setColumns(10);

		tFCantidad = new JTextField();
		tFCantidad.setBounds(506, 200, 114, 19);
		contentPane.add(tFCantidad);
		tFCantidad.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(65, 255, 555, 100);
		contentPane.add(scrollPane);

		dtm = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No. del producto", "Nombre del producto", "Cantidad", "Precio"
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
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(table);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(632, 330, 107, 25);
		contentPane.add(btnEliminar);

		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(this);
		btnAgregar.setBounds(632, 197, 107, 25);
		contentPane.add(btnAgregar);
		
		lblTotalCotizacion = new JLabel("Total $");
		lblTotalCotizacion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalCotizacion.setBounds(410, 380, 200, 25);
		contentPane.add(lblTotalCotizacion);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(this);
		btnRegistrar.setIcon(new ImageIcon(Cotizaciones.class.getResource("/com/sun/java/swing/plaf/gtk/resources/gtk-yes-4.png")));
		btnRegistrar.setBounds(200, 422, 126, 25);
		contentPane.add(btnRegistrar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setIcon(new ImageIcon(Cotizaciones.class.getResource("/com/sun/java/swing/plaf/gtk/resources/gtk-no-4.png")));
		btnCancelar.setBounds(375, 422, 126, 25);
		contentPane.add(btnCancelar);
	}
	
	public boolean estaEnLaLista(int noProducto) {
		for(int i = 0; i < table.getRowCount(); i ++) {
			if(noProducto == Integer.parseInt(dtm.getValueAt(i, 0).toString())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (arg0.getSource() == btnAgregar) {
			int noProducto = 0, cantidad = 0;
			String nombreProducto = "";
			double precio = 0.00;

			if (tFNoProducto.getText().length() > 0 || tFNombreProducto.getText().length() > 0) {
				Conexion cnx = new Conexion();

				if (tFNoProducto.getText().length() > 0) {
					noProducto = Integer.parseInt(tFNoProducto.getText());
					nombreProducto = cnx.getNombreProducto(noProducto);
					
					if (nombreProducto == null) {
						JOptionPane.showMessageDialog(null, "Número de producto inválido", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
				} else if (tFNombreProducto.getText().length() > 0) {
					nombreProducto = tFNombreProducto.getText();
					
					//Con la llamada a getDatosProducto(nombreProducto) se obtiene el no. de producto y el nombre real
					Object[] datosProducto = cnx.getDatosProducto(nombreProducto);
					noProducto = (Integer) datosProducto[0];
					nombreProducto = (String) datosProducto[1];
					
					if (noProducto == 0) {
						JOptionPane.showMessageDialog(null, "Nombre de producto no válido", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
				}
				
				if(tFCantidad.getText().length() == 0)
				    cantidad = 1;
				else
					cantidad = Integer.parseInt(tFCantidad.getText());
				
                
					
			    if(!estaEnLaLista(noProducto)) {
				    precio = cnx.getPrecioProducto(noProducto);
					precio *= cantidad;
					DecimalFormat df = new DecimalFormat("0.00");
					Object[] row = { noProducto, nombreProducto, cantidad, "$" + df.format(precio) };
					dtm.addRow(row);
					totalCotizacion += precio;
					lblTotalCotizacion.setText("Total $" + df.format(totalCotizacion));
					tFNoProducto.setText("");
					tFNombreProducto.setText("");
					tFCantidad.setText("");
				}
			    else {
			    	JOptionPane.showMessageDialog(null, "El producto que elegiste ya está en la lista", "Error", JOptionPane.ERROR_MESSAGE);
			    }
				
			} else {
				JOptionPane.showMessageDialog(null, "Debes de introducir número o nombre del producto y la cantidad", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else if(arg0.getSource() == btnEliminar) {
			
			while(table.getSelectedRow() != -1) {
				int row = table.getSelectedRow();
				totalCotizacion -= Double.parseDouble(dtm.getValueAt(row, 3).toString().replace("$", ""));
			    dtm.removeRow(row);
			}
			
			if(totalCotizacion > 0) {
			    DecimalFormat df = new DecimalFormat("0.00");
			    lblTotalCotizacion.setText("Total $" + df.format(totalCotizacion));
			}
			else {
				lblTotalCotizacion.setText("Total $");
			}
			
		}
		else if(arg0.getSource() == btnRegistrar) {
			
			if(tFNombreCliente.getText().length() > 0) {
				
			    if(dtm.getRowCount() > 0) {
				    int noCotizacion, noProducto, cantidad;
				    Conexion cnx = new Conexion();
				
				    noCotizacion = cnx.insertarCotizacion(tFNombreCliente.getText(), totalCotizacion);
				    
				    if(noCotizacion != -1) {
				    	
				        while(dtm.getRowCount() > 0) {
					        noProducto = Integer.parseInt(dtm.getValueAt(0, 0).toString());
					        cantidad = Integer.parseInt(dtm.getValueAt(0, 2).toString());
					        cnx.insertarCotizacionProducto(noCotizacion, noProducto, cantidad);
					        dtm.removeRow(0);
				        }
				    
				        tFNombreCliente.setText("");
			            tFNoProducto.setText("");
			            tFNombreProducto.setText("");
			            tFCantidad.setText("");
			            lblTotalCotizacion.setText("Total $");
				        JOptionPane.showMessageDialog(null, "Cotización realizada con éxito", "Aviso", JOptionPane.INFORMATION_MESSAGE);				    
				    }
				    else {
				    	JOptionPane.showMessageDialog(null, "La cotización no se pudo realizar", "Error", JOptionPane.ERROR_MESSAGE);	
				    }
				    
				}
			    else {
			    	JOptionPane.showMessageDialog(null, "No hay productos en la lista", "Error", JOptionPane.ERROR_MESSAGE);
			    }
			    
			}
			else {
				JOptionPane.showMessageDialog(null, "Nombre de cliente inválido", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}

	}
}
