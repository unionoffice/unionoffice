package br.com.unionoffice.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Principal extends JFrame {
	JTabbedPane tabbedPane;
	EmailPanel pnEmail;

	public Principal() {
		inicializarComponentes();
		definirEventos();
	}

	private void inicializarComponentes() {
		pnEmail = new EmailPanel();

		tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
	
		tabbedPane.addTab("Nfe", pnEmail);
		

		setContentPane(tabbedPane);

		// parâmetros do JFrame
		setTitle("Union Office");
		setSize(580, 720);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	private void definirEventos() {

	}
}
