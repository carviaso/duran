package duran;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 717, 409);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Menu.class.getResource("/img/duran logo.png")));
		label.setBounds(12, 12, 249, 141);
		frame.getContentPane().add(label);
		
		JLabel lblSistemaDeVentas = new JLabel("Sistema de ventas, cotizaciones");
		lblSistemaDeVentas.setFont(new Font("Dialog", Font.BOLD, 20));
		lblSistemaDeVentas.setHorizontalAlignment(SwingConstants.CENTER);
		lblSistemaDeVentas.setBounds(273, 25, 420, 50);
		frame.getContentPane().add(lblSistemaDeVentas);
		
		JLabel lblYControlDe = new JLabel("y control de inventario");
		lblYControlDe.setFont(new Font("Dialog", Font.BOLD, 20));
		lblYControlDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblYControlDe.setBounds(273, 74, 420, 50);
		frame.getContentPane().add(lblYControlDe);
		
		JLabel lblMenDeOpciones = new JLabel("Menú de opciones");
		lblMenDeOpciones.setFont(new Font("Dialog", Font.BOLD, 18));
		lblMenDeOpciones.setBounds(273, 191, 171, 15);
		frame.getContentPane().add(lblMenDeOpciones);
		
		JButton btnVenta = new JButton("Realizar Venta");
		btnVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ventas ventas = new Ventas();
				ventas.setVisible(true);
			}
		});
		btnVenta.setBounds(30, 245, 171, 50);
		frame.getContentPane().add(btnVenta);
		
		JButton btnCotizacion = new JButton("Realizar Cotización");
		btnCotizacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cotizaciones cotizaciones = new Cotizaciones();
				cotizaciones.setVisible(true);
			}
		});
		btnCotizacion.setBounds(273, 245, 164, 50);
		frame.getContentPane().add(btnCotizacion);
		
		JButton btnInventario = new JButton("Consultar Inventario");
		btnInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Inventario inventario = new Inventario();
				inventario.setVisible(true);
			}
		});
		btnInventario.setBounds(510, 245, 171, 50);
		frame.getContentPane().add(btnInventario);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnSalir.setIcon(new ImageIcon(Menu.class.getResource("/com/sun/java/swing/plaf/gtk/resources/gtk-cancel-4.png")));
		btnSalir.setBounds(302, 341, 107, 25);
		frame.getContentPane().add(btnSalir);
	}
}
