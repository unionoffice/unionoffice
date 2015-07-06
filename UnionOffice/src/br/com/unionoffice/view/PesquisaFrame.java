package br.com.unionoffice.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.com.unionoffice.model.Pedido;
import br.com.unionoffice.model.Satisfacao;

public class PesquisaFrame extends JDialog {
	Pedido pedido;
	Satisfacao satisf;
	JLabel lbNf, lbCliente, lbQuest1, lbQuest2, lbQuest3, lbQuest4;
	JRadioButton rbSim1, rbNao1, rbSim2, rbNao2, rbOtimo, rbBom, rbRegular,
			rbRuim;
	JTextArea taComentarios;
	JScrollPane spComentarios;
	ButtonGroup bgQuest1, bgQuest2, bgQuest3;
	JButton btGravar;

	public PesquisaFrame(Pedido pedido, Satisfacao satisf) {
		this.pedido = pedido;
		this.satisf = satisf;
		inicializarComponentes();
		definirEventos();
		setModal(true);
		setVisible(true);
	}

	private void inicializarComponentes() {
		// lbCliente
		lbCliente = new JLabel(pedido.getCliente());
		lbCliente.setLocation(10, 10);
		lbCliente.setSize(300, 25);

		// lbNf
		lbNf = new JLabel();
		lbNf.setLocation(10, 40);
		lbNf.setSize(300, 25);
		lbNf.setText("Nota Fiscal: " + pedido.getNotaFiscal().getNum());

		// lbQuest1
		lbQuest1 = new JLabel(
				"1 - O prazo de entrega estabelecido foi cumprido? ");
		lbQuest1.setSize(300, 25);
		lbQuest1.setLocation(10, 70);

		// rbSim1
		rbSim1 = new JRadioButton("Sim");
		rbSim1.setLocation(10, 100);
		rbSim1.setSize(50, 25);

		// rbNao1
		rbNao1 = new JRadioButton("Não");
		rbNao1.setLocation(70, 100);
		rbNao1.setSize(50, 25);

		// bgQuest1
		bgQuest1 = new ButtonGroup();
		bgQuest1.add(rbSim1);
		bgQuest1.add(rbNao1);

		// lbQuest2
		lbQuest2 = new JLabel(
				"<html>2 - A qualidade dos produtos recebidos atendeu às suas expectativas?");
		lbQuest2.setSize(300, 50);
		lbQuest2.setLocation(10, 130);

		// rbSim2
		rbSim2 = new JRadioButton("Sim");
		rbSim2.setLocation(10, 175);
		rbSim2.setSize(50, 25);

		// rbNao2
		rbNao2 = new JRadioButton("Não");
		rbNao2.setLocation(70, 175);
		rbNao2.setSize(50, 25);

		// bgQuest2
		bgQuest2 = new ButtonGroup();
		bgQuest2.add(rbSim2);
		bgQuest2.add(rbNao2);

		// lbQuest3
		lbQuest3 = new JLabel(
				"<html>3 - Como foi o atendimento dado pelos colaboradores da Union durante este processo de compra?");
		lbQuest3.setSize(310, 50);
		lbQuest3.setLocation(10, 205);

		// rbOtimo
		rbOtimo = new JRadioButton("Ótimo");
		rbOtimo.setLocation(10, 250);
		rbOtimo.setSize(60, 25);
		
		// rbBom
		rbBom = new JRadioButton("Bom");
		rbBom.setLocation(80, 250);
		rbBom.setSize(55, 25);
		
		// rbRegular
		rbRegular = new JRadioButton("Regular");
		rbRegular.setLocation(145, 250);
		rbRegular.setSize(80, 25);
		
		// rbRuim
		rbRuim = new JRadioButton("Ruim");
		rbRuim.setLocation(235, 250);
		rbRuim.setSize(80, 25);
		
		// bgQuest3
		bgQuest3 = new ButtonGroup();
		bgQuest3.add(rbOtimo);
		bgQuest3.add(rbBom);
		bgQuest3.add(rbRegular);
		bgQuest3.add(rbRuim);
		
		// lbQuest4
		lbQuest4 = new JLabel(
				"<html>4 - Comentários?");
		lbQuest4.setSize(310, 50);
		lbQuest4.setLocation(10, 270);
		
		// taComentarios
		taComentarios = new JTextArea();
		
		// spComentarios
		spComentarios = new JScrollPane(taComentarios);
		spComentarios.setLocation(10,310);
		spComentarios.setSize(310,100);
		
		// btGravar
		btGravar = new JButton(satisf.getQuestoes() == null ? "Gravar" : "Fechar");
		btGravar.setLocation(110,420);
		btGravar.setSize(100,30);		

		setSize(330, 500);
		setLocationRelativeTo(null);
		setLayout(null);
		setTitle("Pesquisa de Satisfação");
		setResizable(false);

		add(lbCliente);
		add(lbNf);
		add(lbQuest1);
		add(rbSim1);
		add(rbNao1);
		add(lbQuest2);
		add(rbSim2);
		add(rbNao2);
		add(lbQuest3);
		add(rbOtimo);
		add(rbBom);
		add(rbRegular);
		add(rbRuim);
		add(lbQuest4);
		add(spComentarios);
		add(btGravar);
	}

	private void definirEventos() {

	}
}
