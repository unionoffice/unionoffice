package br.com.unionoffice.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.unionoffice.dao.ConnectionFactory;
import br.com.unionoffice.model.Pedido;

public class Principal extends JFrame {
	static JTabbedPane tabbedPane;
	static NfePanel pnEmail;
	PedidoPanel pnPedido;
	static PedidoFimPanel pnFimPedido;
	
	

	public Principal() {
		inicializarComponentes();
		definirEventos();
	}

	private void inicializarComponentes() {
		pnEmail = new NfePanel();
		pnPedido = new PedidoPanel();
		pnFimPedido = new PedidoFimPanel();


		tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		tabbedPane.addTab("Nfe", pnEmail);
		tabbedPane.addTab("Recebimento", pnPedido);
		tabbedPane.addTab("Finaliza��o", pnFimPedido);

		setContentPane(tabbedPane);

		// par�metros do JFrame
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
				if (tabbedPane.getSelectedIndex() == 1) {
					setSize(700, 720);
				}else if (tabbedPane.getSelectedIndex() == 2){
					setSize(850,720);
					pnFimPedido.cbFiltrar.setSelectedIndex(0);
				}else {
					setSize(580, 720);
				}
				setLocationRelativeTo(null);
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ConnectionFactory.close();					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,
							"ERRO AO FECHAR A CONEX�O:" + e1.getMessage());
				}

			}
		});
	}
	
	public static void lerXML(Pedido pedido, File xml){
		pnEmail.lerPedido(pedido, xml);		
		tabbedPane.setSelectedIndex(0);
	}
	
	public static void retornar(){
		tabbedPane.setSelectedIndex(2);
		pnFimPedido.cbFiltrar.setSelectedIndex(0);
	}
}
