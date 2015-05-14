package br.com.unionoffice.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Principal extends JFrame{
	JTabbedPane tabbedPane;
	
	public Principal(){
		inicializarComponentes();
		definirEventos();
	}
	
	private void inicializarComponentes(){
		tabbedPane = new JTabbedPane();
		
		
		setContentPane(tabbedPane);
		
		setSize(580, 695);
		setLocationRelativeTo(null);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	private void definirEventos(){
		
	}
}
