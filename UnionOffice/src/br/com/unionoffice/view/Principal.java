package br.com.unionoffice.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.unionoffice.dao.ConnectionFactory;

public class Principal extends JFrame {
	JTabbedPane tabbedPane;
	NfePanel pnEmail;
	PedidoPanel pnPedido;

	public Principal() {
		inicializarComponentes();
		definirEventos();
	}

	private void inicializarComponentes() {
		pnEmail = new NfePanel();
		pnPedido = new PedidoPanel();

		tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		tabbedPane.addTab("Nfe", pnEmail);
		tabbedPane.addTab("Recebimento", pnPedido);
		tabbedPane.addTab("Entrega", new JPanel());

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
				if (tabbedPane.getSelectedIndex() == 1) {
					setSize(700, 720);
				} else {
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
							"ERRO AO FECHAR A CONEXÃO:" + e1.getMessage());
				}

			}
		});
	}
}
