package Vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class InterfazPrincipal extends JFrame {
	
	
	public JPanel panelGlobal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazPrincipal frame = new InterfazPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazPrincipal() {
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		
		panelGlobal = new JPanel();
		panelGlobal.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelGlobal.setLayout(new BorderLayout(0, 0));
		setContentPane(panelGlobal);
		
		JPanel panelInicio = new JPanel();
		panelGlobal.add(panelInicio, BorderLayout.CENTER);
		panelInicio.setLayout(null);
	}

}
