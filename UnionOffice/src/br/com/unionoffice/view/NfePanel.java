package br.com.unionoffice.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.mail.EmailException;
import org.xml.sax.SAXException;

import br.com.unionoffice.dao.PedidoDao;
import br.com.unionoffice.email.EmailNfe;
import br.com.unionoffice.model.NotaFiscal;
import br.com.unionoffice.model.Pedido;

public class NfePanel extends JPanel {
	JLabel lbPara, lbCopia, lbCopiaOculta, lbAssunto;
	JPanel pnMensagem;
	JButton btSelXml, btAddAnx, btRemAnex, btEnviar;
	JTextField tfPara, tfCopia, tfCopiaOculta, tfAssunto;
	NotaFiscal nota;
	JTextPane tpMsg;
	JScrollPane spMsg, spAnexos;
	JList<File> lstAnexos;
	EmailNfe email;
	List<File> anexos;
	JCheckBox chkMantem, chkDeposito;
	JFileChooser fcDialog;
	Pedido pedido;


	public NfePanel() {
		inicializarComponentes();
		definirEventos();
		anexos = new ArrayList<File>();
		try {
			email = new EmailNfe();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(),
					"Erro ao criar objeto e-mail", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public void lerPedido(Pedido ped, File xml){
		this.pedido = ped;
		try {
			nota = new NotaFiscal(xml);
			pedido.setNotaFiscal(nota);
			tfPara.setText(pedido.getEmailContato());
			tfAssunto.setText("Ref.: Nota Fiscal Eletronica - NF "
					+ nota.getNumero().substring(4) + " - "
					+ nota.getDestinatario());
			tpMsg.setText(EmailNfe.criaMensagem(nota,
					chkDeposito.isSelected()));
			if (!anexos.contains(xml)) {
				anexos.add(xml);
			}
			atualizaAnexos();
			btEnviar.setEnabled(true);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	private void inicializarComponentes() {
		// fcDialog
		fcDialog = new JFileChooser();
		fcDialog.setCurrentDirectory(new File("P:\\NFE"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Arquivo XML", "xml");
		fcDialog.setFileFilter(filter);

		// lbPara
		lbPara = new JLabel("Para:");
		lbPara.setLocation(5, 5);
		lbPara.setSize(70, 25);

		// tfPara
		tfPara = new JTextField();
		tfPara.setLocation(80, 5);
		tfPara.setSize(350, 25);

		// lbCopia
		lbCopia = new JLabel("Cc:");
		lbCopia.setLocation(5, 35);
		lbCopia.setSize(70, 25);

		// tfCopia
		tfCopia = new JTextField();
		tfCopia.setLocation(80, 35);
		tfCopia.setSize(350, 25);

		// lbCopiaOculta
		lbCopiaOculta = new JLabel("Cco:");
		lbCopiaOculta.setLocation(5, 65);
		lbCopiaOculta.setSize(70, 25);

		// tfCopiaOculta
		tfCopiaOculta = new JTextField("vendas@unionoffice.com.br");
		tfCopiaOculta.setLocation(80, 65);
		tfCopiaOculta.setSize(350, 25);

		// chkDeposito
		chkDeposito = new JCheckBox("Depósito em C/C");
		chkDeposito.setLocation(430, 65);
		chkDeposito.setSize(130, 25);

		// btSelXml
		btSelXml = new JButton("Selecionar XML");
		btSelXml.setLocation(435, 5);
		btSelXml.setSize(130, 55);
		btSelXml.setToolTipText("Adicionar XML");

		// lbAssunto
		lbAssunto = new JLabel("Assunto:");
		lbAssunto.setLocation(5, 95);
		lbAssunto.setSize(70, 25);

		// tfAssunto
		tfAssunto = new JTextField();
		tfAssunto.setLocation(80, 95);
		tfAssunto.setSize(485, 25);

		// taMsg
		tpMsg = new JTextPane();
		tpMsg.setContentType("text/html");

		// spMsg
		spMsg = new JScrollPane(tpMsg);

		// pnMensagem
		pnMensagem = new JPanel();
		pnMensagem.setBackground(Color.cyan);
		pnMensagem.setLocation(5, 125);
		pnMensagem.setSize(560, 440);
		pnMensagem.setLayout(new GridLayout());
		pnMensagem.add(spMsg);

		// lstAnexos
		lstAnexos = new JList<File>();

		// spAnexos
		spAnexos = new JScrollPane(lstAnexos);
		spAnexos.setLocation(5, 570);
		spAnexos.setSize(330, 90);

		// btAddAnx
		btAddAnx = new JButton("Anexar");
		btAddAnx.setLocation(340, 570);
		btAddAnx.setSize(100, 42);
		btAddAnx.setToolTipText("Adicionar anexo");
		btAddAnx.addActionListener(acaoBtAdd);

		// btRemAnx
		btRemAnex = new JButton("Excluir");
		btRemAnex.setLocation(340, 617);
		btRemAnex.setSize(100, 42);
		btRemAnex.setEnabled(false);
		btRemAnex.setToolTipText("Remover anexo");
		btRemAnex.addActionListener(acaoBtRem);

		// btEnviar
		btEnviar = new JButton("Enviar");
		btEnviar.setLocation(445, 570);
		btEnviar.setSize(120, 65);
		btEnviar.setEnabled(false);
		btEnviar.setToolTipText("Enviar E-mail");
		btEnviar.addActionListener(acaoBtEnviar);

		// chkMantem
		chkMantem = new JCheckBox("Manter Dados");
		chkMantem.setLocation(445, 635);
		chkMantem.setSize(120, 25);

		// pnDados
		// pnDados = new JPanel();
		setLayout(null);
		add(lbPara);
		add(tfPara);
		add(lbCopia);
		add(tfCopia);
		add(lbCopiaOculta);
		add(tfCopiaOculta);
		add(chkDeposito);
		add(btSelXml);
		add(lbAssunto);
		add(tfAssunto);
		add(pnMensagem);
		add(spAnexos);
		add(btAddAnx);
		add(btRemAnex);
		add(btEnviar);
		add(chkMantem);
	}

	private void definirEventos() {
		btSelXml.addActionListener(acaoBtLer);

		chkDeposito.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nota != null) {
					tpMsg.setText(EmailNfe.criaMensagem(nota,
							chkDeposito.isSelected()));
				}
			}
		});

	}

	private void atualizaAnexos() {
		DefaultListModel<File> model = new DefaultListModel<File>();
		for (File f : anexos) {
			model.addElement(f);
		}
		lstAnexos.setModel(model);
		if (anexos.size() == 0) {
			btRemAnex.setEnabled(false);
		} else {
			btRemAnex.setEnabled(true);
		}
	}

	private ActionListener acaoBtAdd = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			fcDialog.setMultiSelectionEnabled(true);
			fcDialog.setFileFilter(fcDialog.getChoosableFileFilters()[0]);
			int returnValue = fcDialog.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				for (File arq : fcDialog.getSelectedFiles()) {
					if (!anexos.contains(arq)) {
						anexos.add(arq);
					}
				}
				atualizaAnexos();
			}
		}
	};

	private ActionListener acaoBtRem = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (lstAnexos.getSelectedIndex() < 0) {
				JOptionPane.showMessageDialog(null,
						"Selecione um anexo para remover", "Selecione",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				List<File> files = lstAnexos.getSelectedValuesList();
				for (File arq : files) {
					anexos.remove(arq);
				}
				atualizaAnexos();
			}
		}
	};

	private void limparDados(boolean mantem) {
		try {
			email = new EmailNfe();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(),
					"Erro ao criar objeto e-mail", JOptionPane.ERROR_MESSAGE);
		}
		btEnviar.setEnabled(false);
		btRemAnex.setEnabled(false);
		tfAssunto.setText(null);
		tpMsg.setText(null);
		nota = null;
		pedido = null;
		anexos = new ArrayList<File>();
		lstAnexos.setModel(new DefaultListModel<File>());
		if (!mantem) {
			tfPara.setText(null);
			tfCopia.setText(null);
			chkDeposito.setSelected(false);
		}
	}

	private ActionListener acaoBtEnviar = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (tfPara.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Digite o e-mail do destinatário",
						"Informe o destinatário",
						JOptionPane.INFORMATION_MESSAGE);
				tfPara.requestFocus();
			} else if (anexos.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Selecione pelo menos um anexo para enviar.",
						"Sem anexos", JOptionPane.INFORMATION_MESSAGE);
			} else {				
				//new Thread() {
				//	public void run() {
						try {
							email.setDestinatario(tfPara.getText().trim().split(";"));
							email.setCopias(tfCopia.getText().trim().split(";"));
							email.setCopiasOculas(tfCopiaOculta.getText().trim().split(";"));
							email.setAssunto(tfAssunto.getText());
							email.setAnexos(anexos);
							email.setMensagem(tpMsg.getText());			
							email.enviar();							
							if (pedido != null) {								
								pedido.getNotaFiscal().setEmail(email.getDestinatario()[0]);
								new PedidoDao().gravarNf(pedido);
								Principal.retornar();
							}
						} catch (EmailException e) {
							JOptionPane.showMessageDialog(null,
									"Erro ao enviar e-mail: " + e.getMessage(),
									"Erro de envio", JOptionPane.ERROR_MESSAGE);
						}catch (SQLException e){
							JOptionPane.showMessageDialog(null,
									"Erro ao gravar a nf: " + e.getMessage(),
									"Erro de gravação", JOptionPane.ERROR_MESSAGE);
						}

					};
				//}.start();
				limparDados(chkMantem.isSelected());
			//}
		}
	};

	private ActionListener acaoBtLer = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			fcDialog.setFileFilter(fcDialog.getChoosableFileFilters()[1]);
			fcDialog.setMultiSelectionEnabled(false);
			int returnValue = fcDialog.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				try {
					File arquivo = fcDialog.getSelectedFile();
					nota = new NotaFiscal(arquivo);
					tfAssunto.setText("Ref.: Nota Fiscal Eletronica - NF "
							+ nota.getNumero().substring(4) + " - "
							+ nota.getDestinatario());
					tpMsg.setText(EmailNfe.criaMensagem(nota,
							chkDeposito.isSelected()));
					if (!anexos.contains(arquivo)) {
						anexos.add(arquivo);
					}
					atualizaAnexos();
					btEnviar.setEnabled(true);
					btRemAnex.setEnabled(true);
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, "Erro ao ler o XML: "
							+ erro.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};
}
