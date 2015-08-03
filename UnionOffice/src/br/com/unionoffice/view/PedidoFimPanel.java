package br.com.unionoffice.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import org.apache.commons.mail.EmailException;

import br.com.unionoffice.dao.PedidoDao;
import br.com.unionoffice.dao.SatisfacaoDao;
import br.com.unionoffice.email.EmailPedido;
import br.com.unionoffice.email.EmailSatisfacao;
import br.com.unionoffice.model.Pedido;
import br.com.unionoffice.model.Satisfacao;
import br.com.unionoffice.tablemodel.PedidoFimTableModel;

public class PedidoFimPanel extends JPanel {
	Font fonteSatisfacao;
	JComboBox<String> cbFiltrar;
	JFormattedTextField tfBuscar;
	JButton btFiltrar, btEnviaNF, btSatisf;
	JTextField tfEmailNf, tfEmailSat;
	JLabel lbBuscar, lbEmailRecebimento, lbContatoRecebimento,
			lbDataEnvioRecebimento, lbDataEnvioNF, lbEmailNF, lbEmailSat,
			lbDataEnvioSat, lbPedido, lbIconeRec, lbIconeNf, lbIconeSat, lbDataRespSat, lbSatisfacao;
	List<Pedido> pedidos;
	JTable tbPedidos;
	JScrollPane pnPedidos;
	JPanel pnRecebimento, pnXML, pnSatisf;
	JButton btEnviarMassa;
	PedidoDao daoPedido;
	SatisfacaoDao daoSatisfacao;
	Pedido pedido;
	Satisfacao sat;
	JFileChooser fcDialog;
	final JProgressBar progressBar = new JProgressBar();

	public PedidoFimPanel() {
		try {
			daoPedido = new PedidoDao();
			daoSatisfacao = new SatisfacaoDao();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(PedidoFimPanel.this, e.getMessage());
		}
		inicializarComponentes();
		definirEventos();
	}

	private void inicializarComponentes() {
		//fonteSatisfacao
		fonteSatisfacao = new Font("Arial", Font.BOLD, 20);
		
		// fcDialog
		fcDialog = new JFileChooser();
		fcDialog.setCurrentDirectory(new File("P:\\NFE"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Arquivo XML", "xml");
		fcDialog.setFileFilter(filter);
		fcDialog.setFileFilter(fcDialog.getChoosableFileFilters()[1]);
		fcDialog.setMultiSelectionEnabled(false);

		// cbFiltrar
		cbFiltrar = new JComboBox<String>();
		cbFiltrar.setLocation(5, 10);
		cbFiltrar.setSize(130, 25);
		cbFiltrar.addItem("Não faturados");
		cbFiltrar.addItem("Sem pesquisa");
		cbFiltrar.addItem("Todos");

		// lbBuscar
		lbBuscar = new JLabel("Buscar:");
		lbBuscar.setLocation(145, 10);
		lbBuscar.setSize(45, 25);

		// tfBuscar
		try {
			MaskFormatter mskPedido = new MaskFormatter("AA####/####");
			tfBuscar = new JFormattedTextField(mskPedido);
			tfBuscar.setLocation(200, 10);
			tfBuscar.setSize(100, 25);
			tfBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// btFiltrar
		btFiltrar = new JButton("Buscar");
		btFiltrar.setLocation(310, 10);
		btFiltrar.setSize(90, 25);

		// tbPedidos
		tbPedidos = new JTable();
		tbPedidos.getTableHeader().setReorderingAllowed(false);
		tbPedidos.getTableHeader().setResizingAllowed(false);
		tbPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try {
			criarTabela(daoPedido.listarNaoFaturados());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(PedidoFimPanel.this, e.getMessage());
		}

		// pnPedidos
		pnPedidos = new JScrollPane(tbPedidos);
		pnPedidos.setLocation(5, 45);
		pnPedidos.setSize(500, 550);

		// progressBar
		progressBar.setLocation(5, 605);
		progressBar.setSize(400, 40);
		progressBar.setStringPainted(true);

		// btEnviarMassa
		btEnviarMassa = new JButton("Satisfação");
		btEnviarMassa.setLocation(410, 605);
		btEnviarMassa.setSize(95, 40);

		// lbPedido
		lbPedido = new JLabel();
		lbPedido.setLocation(515, 45);
		lbPedido.setSize(320, 25);
		lbPedido.setForeground(Color.blue);

		// pnRecebimento
		pnRecebimento = new JPanel();
		pnRecebimento.setBorder(BorderFactory
				.createTitledBorder("Recebimento do Pedido"));
		pnRecebimento.setLocation(510, 80);
		pnRecebimento.setSize(320, 150);

		// lbEmailRecebimento
		lbEmailRecebimento = new JLabel("Email:");
		lbEmailRecebimento.setLocation(10, 30);
		lbEmailRecebimento.setSize(300, 25);

		// lbContatoRecebimento
		lbContatoRecebimento = new JLabel("Contato:");
		lbContatoRecebimento.setLocation(10, 65);
		lbContatoRecebimento.setSize(300, 25);

		// lbDataEnvioRecebimento
		lbDataEnvioRecebimento = new JLabel("Enviado:");
		lbDataEnvioRecebimento.setLocation(10, 100);
		lbDataEnvioRecebimento.setSize(300, 25);

		// lbIconeRec
		lbIconeRec = new JLabel();
		lbIconeRec.setLocation(263, 93);
		lbIconeRec.setSize(47, 47);

		// pnRecebimento add
		pnRecebimento.setLayout(null);
		pnRecebimento.add(lbEmailRecebimento);
		pnRecebimento.add(lbContatoRecebimento);
		pnRecebimento.add(lbDataEnvioRecebimento);
		pnRecebimento.add(lbIconeRec);

		// pnXML
		pnXML = new JPanel();
		pnXML.setBorder(BorderFactory.createTitledBorder("Nota Fiscal"));
		pnXML.setLocation(510, 240);
		pnXML.setSize(320, 150);

		// lbEmailNF
		lbEmailNF = new JLabel("Email:");
		lbEmailNF.setLocation(10, 30);
		lbEmailNF.setSize(40, 25);

		// tfEmailNf
		tfEmailNf = new JTextField();
		tfEmailNf.setLocation(60, 30);
		tfEmailNf.setSize(245, 25);

		// lbDataEnvioNF
		lbDataEnvioNF = new JLabel("Enviado:");
		lbDataEnvioNF.setLocation(10, 65);
		lbDataEnvioNF.setSize(300, 25);
		

		// btEnviaNF
		btEnviaNF = new JButton("Enviar NF");
		btEnviaNF.setLocation(10, 110);
		btEnviaNF.setSize(100, 25);

		// lbIconeNf
		lbIconeNf = new JLabel();
		lbIconeNf.setLocation(263, 93);
		lbIconeNf.setSize(47, 47);

		// pnXML add
		pnXML.setLayout(null);
		pnXML.add(lbEmailNF);
		pnXML.add(tfEmailNf);
		pnXML.add(lbDataEnvioNF);
		pnXML.add(btEnviaNF);
		pnXML.add(lbIconeNf);

		// pnSatisf
		pnSatisf = new JPanel();
		pnSatisf.setBorder(BorderFactory
				.createTitledBorder("Satisfação do cliente"));
		pnSatisf.setLocation(510, 400);
		pnSatisf.setSize(320, 195);

		// lbEmailSat
		lbEmailSat = new JLabel("Email:");
		lbEmailSat.setLocation(10, 30);
		lbEmailSat.setSize(40, 25);

		// tfEmailSat
		tfEmailSat = new JTextField();
		tfEmailSat.setLocation(60, 30);
		tfEmailSat.setSize(245, 25);

		// lbDataEnvioSat
		lbDataEnvioSat = new JLabel("Enviado:");
		lbDataEnvioSat.setLocation(10, 65);
		lbDataEnvioSat.setSize(300, 25);
		
		// lbDataRespSat
		lbDataRespSat = new JLabel("Pontuado:");
		lbDataRespSat.setLocation(10,100);
		lbDataRespSat.setSize(300,25);

		// btSatisf
		btSatisf = new JButton("Enviar Satisfação");
		btSatisf.setLocation(10, 145);
		btSatisf.setSize(150, 25);

		// lbIconeSat
		lbIconeSat = new JLabel();
		lbIconeSat.setLocation(263, 128);
		lbIconeSat.setSize(47, 47);

		// pnSatisf add
		pnSatisf.setLayout(null);
		pnSatisf.add(lbEmailSat);
		pnSatisf.add(tfEmailSat);
		pnSatisf.add(lbDataEnvioSat);
		pnSatisf.add(lbDataRespSat);
		pnSatisf.add(btSatisf);
		pnSatisf.add(lbIconeSat);
		
		//lbSatisfacao
		lbSatisfacao = new JLabel();
		lbSatisfacao.setBorder(BorderFactory.createLineBorder(Color.black));
		lbSatisfacao.setLocation(510,605);
		lbSatisfacao.setSize(320,40);
		lbSatisfacao.setFont(fonteSatisfacao);
		lbSatisfacao.setHorizontalAlignment(SwingConstants.CENTER);
		lbSatisfacao.setOpaque(true);

		setLayout(null);
		add(cbFiltrar);
		add(lbBuscar);
		add(tfBuscar);
		add(btFiltrar);
		add(pnPedidos);
		add(progressBar);
		add(btEnviarMassa);
		add(lbPedido);
		add(pnRecebimento);
		add(pnXML);
		add(pnSatisf);
		add(lbSatisfacao);
	}

	private void criarTabela(List<Pedido> lista) {
		if (lista == null) {
			tbPedidos.setModel(new PedidoFimTableModel());
		} else {
			tbPedidos.setModel(new PedidoFimTableModel(lista));
		}
		DefaultTableCellRenderer cellRight = new DefaultTableCellRenderer();
		cellRight.setHorizontalAlignment(SwingConstants.RIGHT);

		tbPedidos.setRowHeight(20);
		tbPedidos.getColumnModel().getColumn(0).setPreferredWidth(90);
		tbPedidos.getColumnModel().getColumn(1).setPreferredWidth(80);
		tbPedidos.getColumnModel().getColumn(2).setPreferredWidth(300);
		tbPedidos.getColumnModel().getColumn(3).setPreferredWidth(40);
	}

	private void definirEventos() {
		tfBuscar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Toolkit tk = Toolkit.getDefaultToolkit();
				tk.setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);
			}
		});

		tfBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == 10) {
					try {
						tfBuscar.commitEdit();
						btFiltrar.doClick();
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(PedidoFimPanel.this,
								"Digite um valor para buscar");
					}

				}
			}
		});

		btFiltrar.addActionListener(event -> {
			if (tfBuscar.getValue() != null) {
				String parametro = tfBuscar.getValue().toString();
				try {
					criarTabela(pedidos = daoPedido.buscarNumero(parametro));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(PedidoFimPanel.this,
							e.getMessage());
				}
			}
		});

		cbFiltrar.addActionListener(event -> {
			try {
				switch (cbFiltrar.getSelectedIndex()) {
				case 0:
					criarTabela(pedidos = daoPedido.listarNaoFaturados());
					break;
				case 1:
					criarTabela(pedidos = daoPedido.listarSemPesquisa());
					break;
				case 2:
					criarTabela(pedidos = daoPedido.listarTodos());
					break;
				default:
					break;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(PedidoFimPanel.this,
						e.getMessage());
			}
			limpar();
		});

		tbPedidos
				.getSelectionModel()
				.addListSelectionListener(
						event -> {
							if (tbPedidos.getSelectedRow() >= 0) {
								PedidoFimTableModel model = (PedidoFimTableModel) tbPedidos
										.getModel();
								pedido = model.getPedido(tbPedidos
										.getSelectedRow());
								exibirInformacoes();
							}

						});

		btEnviaNF.addActionListener(event -> {
			if (pedido != null) {
				int returnValue = fcDialog.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (!tfEmailNf.getText().isEmpty()) {
						pedido.setEmailContato(tfEmailNf.getText());
					}
					File xml = fcDialog.getSelectedFile();
					Principal.lerXML(pedido, xml);
				}
			}
		});

		tbPedidos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tbPedidos.getSelectedRow() >= 0 && e.getKeyCode() == 127) {
					int opcao = JOptionPane.showConfirmDialog(
							PedidoFimPanel.this, "Deseja excluir o pedido "
									+ pedido.getPedidoInterno() + "?",
							"Confirmar exclusão", JOptionPane.YES_NO_OPTION);
					if (opcao == 0) {
						try {
							daoPedido.excluir(pedido);
							cbFiltrar.setSelectedIndex(0);
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(
									PedidoFimPanel.this,
									"Erro ao excluir o pedido: "
											+ e2.getMessage());
						}
					}
				}
			}
		});

		btSatisf.addActionListener(event -> {
			if (sat == null) {
				new Thread() {
					public void run() {
						try {
							sat = new Satisfacao();
							if (!tfEmailSat.getText().isEmpty()) {
								sat.setEmail(tfEmailSat.getText());
							} else {
								sat.setEmail(pedido.getEmailContato());
							}
							final EmailSatisfacao emailSat = new EmailSatisfacao(
									pedido);
							emailSat.setAssunto("Conclusão de entrega");
							emailSat.setDestinatario(sat.getEmail());
							emailSat.enviar();
							daoSatisfacao.criar(sat, pedido.getNotaFiscal()
									.getNum());
							daoPedido.atualizaSat(pedido);
							alteraIcone(lbIconeSat, true);
							tfEmailSat.setEditable(false);
							tfEmailSat.setText(sat.getEmail());
							lbDataEnvioSat.setText("Enviado: "
									+ new SimpleDateFormat(
											"dd/MM/yyyy 'às' HH:mm:ss")
											.format(Calendar.getInstance()
													.getTime()));
						} catch (Exception erro) {
							JOptionPane.showMessageDialog(PedidoFimPanel.this,
									"Erro: " + erro.getMessage());
						}
					};
				}.start();
			} else {
				new PesquisaFrame(pedido, sat);
				calculaSatisfacao(sat.getQuestoes());
			}
		});
		
		btEnviarMassa.addActionListener(e -> {
			progressBar.setMaximum(pedidos.stream()
					.filter((pedido) -> pedido.isEnviarSatisf()).toArray().length);
			for (final Pedido pedido : pedidos) {
				if (pedido.isEnviarSatisf()) {
					if (pedido.getEmailContato() == null
							|| pedido.getContato() == null) {
						JOptionPane.showMessageDialog(null, "Pedido  "
								+ pedido.getPedidoInterno()
								+ " sem e-mail ou sem contato.Não enviado",
								"Erro", JOptionPane.ERROR_MESSAGE);
						continue;
					}					
					new Thread() {
						public void run() {
							try {								
								final EmailSatisfacao email = new EmailSatisfacao(
										pedido);
								email.enviar();																
								daoSatisfacao.criar(sat, pedido.getNotaFiscal()
										.getNum());
								daoPedido.atualizaSat(pedido);
								progressBar.setValue(progressBar.getValue() + 1);
							} catch (EmailException e) {
								JOptionPane.showMessageDialog(
										null,
										"Erro ao enviar e-mail: "
												+ pedido.getCliente()
												+ "\n" + e.getMessage(),
										"Erro de envio",
										JOptionPane.ERROR_MESSAGE);
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null,
										"Erro ao gravar e-mail no banco de dados: "
												+ pedido.getCliente()
												+ "\n" + e.getMessage(),
										"Erro de gravação",
										JOptionPane.ERROR_MESSAGE);
							}
						};
					}.start();
				}
			}
			cbFiltrar.setSelectedIndex(1);			
		});
	}

	private void exibirInformacoes() {
		if (pedido != null) {
			lbPedido.setText(pedido.getPedidoInterno() + " - "
					+ pedido.getCliente());
			if (pedido.getDataEnvioReceb() != null) {
				lbDataEnvioRecebimento.setText("Enviado: "
						+ new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss")
								.format(pedido.getDataEnvioReceb().getTime()));
				lbEmailRecebimento
						.setText("Email: " + pedido.getEmailContato());
				lbContatoRecebimento.setText("Contato: " + pedido.getContato());
				alteraIcone(lbIconeRec, true);
			} else {
				alteraIcone(lbIconeRec, false);
			}

			if (pedido.getNotaFiscal() != null) {
				lbDataEnvioNF.setText("Enviado: "
						+ new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss")
								.format(pedido.getNotaFiscal().getDataEnvio()
										.getTime()));
				tfEmailNf.setText(pedido.getNotaFiscal().getEmail());
				alteraIcone(lbIconeNf, true);
				btEnviaNF.setEnabled(false);
				tfEmailNf.setEditable(false);
			} else {
				alteraIcone(lbIconeNf, false);
			}

			if (pedido.getDataEnvioSatisf() != null) {
				try {
					sat = daoSatisfacao.buscar(pedido.getNotaFiscal().getNum());
					tfEmailSat.setText(sat.getEmail());
					tfEmailSat.setEditable(false);
					lbDataEnvioSat.setText("Enviado: "
							+ new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss")
									.format(pedido.getDataEnvioSatisf()
											.getTime()));
					alteraIcone(lbIconeSat, true);
					if (sat.getDataResposta() == null) {
						btSatisf.setText("Pontuar");
						lbSatisfacao.setText("Não pontuado");
					} else {
						btSatisf.setText("Respostas");
						lbDataRespSat.setText("Pontuado: "
								+ new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss")
										.format(sat.getDataResposta()
												.getTime()));
						calculaSatisfacao(sat.getQuestoes());
					}
				} catch (SQLException erro) {

					JOptionPane.showMessageDialog(this,
							"Erro: " + erro.getMessage());
				}
			} else {
				alteraIcone(lbIconeSat, false);
			}
		}
	}

	private void alteraIcone(JLabel label, boolean opcao) {
		if (opcao) {
			label.setIcon(new ImageIcon(getClass().getResource(
					"/imagens/icone_ok.png")));
		} else {
			label.setIcon(new ImageIcon(getClass().getResource(
					"/imagens/icone_nao.png")));
		}
	}
	
	private void calculaSatisfacao(int[] respostas){
		int soma = 0;
		for (int i = 0; i < respostas.length; i++) {
			soma += respostas[i];
		}
		if (soma <=3) {
			lbSatisfacao.setBackground(Color.red);
			lbSatisfacao.setText("Ruim");
		}else if (soma <= 5) {
			lbSatisfacao.setBackground(Color.yellow);
			lbSatisfacao.setText("Regular");
		}else if (soma <= 8) {
			lbSatisfacao.setBackground(new Color(0,200,0));
			lbSatisfacao.setText("Bom");
		}else {
			lbSatisfacao.setBackground(Color.green);
			lbSatisfacao.setText("Ótimo");	
		}
	}

	private void limpar() {
		lbPedido.setText(null);
		lbDataEnvioRecebimento.setText("Enviado:");
		lbEmailRecebimento.setText("Email:");
		lbContatoRecebimento.setText("Contato:");
		lbIconeRec.setIcon(null);
		btEnviaNF.setEnabled(true);
		lbDataEnvioNF.setText("Enviado:");
		tfEmailNf.setEditable(true);
		tfEmailNf.setText(null);
		lbIconeNf.setIcon(null);
		btSatisf.setEnabled(true);
		lbDataEnvioSat.setText("Enviado:");
		tfEmailSat.setText(null);
		btSatisf.setText("Enviar Satisfação");
		lbDataEnvioSat.setText("Enviado:");
		lbDataRespSat.setText("Pontuado:");
		lbEmailSat.setText("Email:");
		lbIconeSat.setIcon(null);
		pedido = null;
		sat = null;
		lbSatisfacao.setBackground(null);
		lbSatisfacao.setText(null);
	}
}
