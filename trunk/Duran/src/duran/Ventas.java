package duran;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.ListSelectionModel;

public class Ventas extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField tFNombreCliente, tFNoProducto, tFNombreProducto, tFCantidad;
	private DefaultTableModel dtm;
	private JTable table;
	private JButton btnAgregar, btnEliminar, btnRegistrar, btnCancelar;

	/**
	 * Create the frame.
	 */
	public Ventas() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 790, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblRealizarUnaVenta = new JLabel("Realizar una venta");
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

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(this);
		btnRegistrar.setIcon(new ImageIcon(Ventas.class.getResource("/com/sun/java/swing/plaf/gtk/resources/gtk-yes-4.png")));
		btnRegistrar.setBounds(200, 422, 126, 25);
		contentPane.add(btnRegistrar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setIcon(new ImageIcon(Ventas.class.getResource("/com/sun/java/swing/plaf/gtk/resources/gtk-no-4.png")));
		btnCancelar.setBounds(375, 422, 126, 25);
		contentPane.add(btnCancelar);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnAgregar) {
			int noProducto = 0, cantidad = 0;
			String nombreProducto = "";
			double precio = 0.00;

			if ((tFNoProducto.getText() != "" || tFNombreProducto.getText() != "") && tFCantidad.getText() != "") {
				Conexion cnx = new Conexion();

				if (tFNoProducto.getText() != "") {
					noProducto = Integer.parseInt(tFNoProducto.getText());
					nombreProducto = cnx.getNombreProducto(noProducto);
					if (nombreProducto == "") {
						JOptionPane.showMessageDialog(null, "Número de producto inválido", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else if (tFNombreProducto.getText() != "") {
					nombreProducto = tFNombreProducto.getText();
					noProducto = cnx.getNoProducto(nombreProducto);
					if (noProducto == -1) {
						JOptionPane.showMessageDialog(null, "Nombre de producto no válido", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				cantidad = Integer.parseInt(tFCantidad.getText());
				precio = cnx.getPrecioProducto(noProducto);
				precio *= cantidad;
				Object[] row = { noProducto, nombreProducto, cantidad, precio };
				dtm.addRow(row);
			} else {
				JOptionPane.showMessageDialog(null, "Debes de introducir número o nombre del producto y la cantidad", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(arg0.getSource() == btnEliminar) {
			while(table.getSelectedRow() != -1) {
			    dtm.removeRow(table.getSelectedRow());
			}
		}
		else if(arg0.getSource() == btnRegistrar) {
			if(tFNombreCliente.getText() != "") {
			    if(dtm.getRowCount() > 0) {
				    double totalVenta = 0.00;
				    Conexion cnx = new Conexion();
				
				    for(int i = 0; i < dtm.getRowCount(); i++) {
					    totalVenta += Double.parseDouble(dtm.getValueAt(i, 3).toString());
				    }
				    cnx.insertarVenta(tFNombreCliente.getText(), totalVenta);
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
