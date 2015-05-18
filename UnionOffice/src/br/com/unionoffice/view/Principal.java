package br.com.unionoffice.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Principal extends JFrame {
	JTabbedPane tabbedPane;
	EmailPanel pnEmail;
	PedidoPanel pnPedido;

	public Principal() {
		inicializarComponentes();
		definirEventos();
	}

	private void inicializarComponentes() {
		pnEmail = new EmailPanel();
		pnPedido = new PedidoPanel();

		tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		tabbedPane.addTab("Nfe", pnEmail);
		tabbedPane.addTab("Pedido", pnPedido);
		

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
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {				
				if(tabbedPane.getSelectedIndex() == 1){
					setSize(700,720);
				}else{
					setSize(580,720);
				}
				setLocationRelativeTo(null);
			}
		});
	}
}
