package br.com.unionoffice.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import br.com.unionoffice.model.Pedido;

public class VisualizaEmail extends JDialog{
	JTextPane tpMsg;
	JScrollPane pnMsg;
	JButton btSalvar;
	Pedido pedido;
	
	public VisualizaEmail(Pedido pedido){
		this.pedido = pedido;
		inicializarComponentes();
		definirEventos();	
		setModal(true);
		setVisible(true);
	}
	
	private void inicializarComponentes(){
		tpMsg = new JTextPane();
		tpMsg.setContentType("text/html");
		tpMsg.setText(pedido.getMensagem());
		
		pnMsg = new JScrollPane(tpMsg);
		
		btSalvar = new JButton("Salvar");
		
		
		add(pnMsg, BorderLayout.CENTER);
		add(btSalvar, BorderLayout.SOUTH);
		
		//
		setSize(500,600);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Visualização do e-mail");
	}
	
	private void definirEventos(){
		btSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pedido.setMensagem(tpMsg.getText());
				dispose();
			}
		});
		
	}
}
