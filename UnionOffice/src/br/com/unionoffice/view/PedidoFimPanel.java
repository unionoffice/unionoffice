package br.com.unionoffice.view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import br.com.unionoffice.dao.PedidoDao;
import br.com.unionoffice.model.Pedido;
import br.com.unionoffice.tablemodel.PedidoFimTableModel;

public class PedidoFimPanel extends JPanel {
	JComboBox<String> cbFiltrar;
	JFormattedTextField tfBuscar;
	JButton btFiltrar, btEnviaNF, btEnviaSat;
	JLabel lbBuscar, lbEmailRecebimento, lbContatoRecebimento,
			lbDataEnvioRecebimento, lbEmailNF, lbDataEnvioNF, lbEmailSat,
			lbDataEnvioSat, lbPedido, lbIconeRec, lbIconeNf, lbIconeSat;
	List<Pedido> pedidos;
	JTable tbPedidos;
	JScrollPane pnPedidos;
	JPanel pnRecebimento, pnXML, pnSatisf;
	JButton btEnviarMassa;
	PedidoDao daoPedido;
	Pedido pedido;
	JFileChooser fcDialog;
	final JProgressBar progressBar = new JProgressBar();

	public PedidoFimPanel() {
		try {
			daoPedido = new PedidoDao();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(PedidoFimPanel.this, e.getMessage());
		}
		inicializarComponentes();
		definirEventos();
	}

	private void inicializarComponentes() {
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
		lbEmailNF.setSize(300, 25);

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
		pnXML.add(lbDataEnvioNF);
		pnXML.add(btEnviaNF);
		pnXML.add(lbIconeNf);

		// pnSatisf
		pnSatisf = new JPanel();
		pnSatisf.setBorder(BorderFactory
				.createTitledBorder("Satisfação do cliente"));
		pnSatisf.setLocation(510, 400);
		pnSatisf.setSize(320, 150);

		// lbEmailSat
		lbEmailSat = new JLabel("Email:");
		lbEmailSat.setLocation(10, 30);
		lbEmailSat.setSize(300, 25);

		// lbDataEnvioSat
		lbDataEnvioSat = new JLabel("Enviado:");
		lbDataEnvioSat.setLocation(10, 65);
		lbDataEnvioSat.setSize(300, 25);

		// btEnviaSat
		btEnviaSat = new JButton("Enviar Satisfação");
		btEnviaSat.setLocation(10, 110);
		btEnviaSat.setSize(150, 25);

		// lbIconeSat
		lbIconeSat = new JLabel();
		lbIconeSat.setLocation(263, 93);
		lbIconeSat.setSize(47, 47);

		// pnSatisf add
		pnSatisf.setLayout(null);
		pnSatisf.add(lbEmailSat);
		pnSatisf.add(lbDataEnvioSat);
		pnSatisf.add(btEnviaSat);
		pnSatisf.add(lbIconeSat);

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
					criarTabela(daoPedido.buscarNumero(parametro));
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
					criarTabela(daoPedido.listarNaoFaturados());
					break;
				case 1:
					criarTabela(daoPedido.listarSemPesquisa());
					break;
				case 2:
					criarTabela(daoPedido.listarTodos());
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
							JOptionPane.showMessageDialog(PedidoFimPanel.this, "Erro ao excluir o pedido: "+e2.getMessage());
						}
					}
				}
				System.out.println(e.getKeyCode());

			}
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
				lbEmailNF
						.setText("Email: " + pedido.getNotaFiscal().getEmail());
				alteraIcone(lbIconeNf, true);
				btEnviaNF.setEnabled(false);
			} else {
				alteraIcone(lbIconeNf, false);
			}

			if (pedido.getDataEnvioSatisf() != null) {
				lbDataEnvioSat.setText("Enviado: "
						+ new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss")
								.format(pedido.getDataEnvioSatisf().getTime()));
				lbEmailSat.setText("Email: " + pedido.getEmailContato());
				alteraIcone(lbIconeSat, true);
				btEnviaSat.setEnabled(false);
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

	private void limpar() {
		lbPedido.setText(null);
		lbDataEnvioRecebimento.setText("Enviado:");
		lbEmailRecebimento.setText("Email:");
		lbContatoRecebimento.setText("Contato:");
		lbIconeRec.setIcon(null);
		btEnviaNF.setEnabled(true);
		lbDataEnvioNF.setText("Enviado:");
		lbEmailNF.setText("Email:");
		lbIconeNf.setIcon(null);
		btEnviaSat.setEnabled(true);
		lbDataEnvioSat.setText("Enviado:");
		lbEmailSat.setText("Email:");
		lbIconeSat.setIcon(null);
		pedido = null;
	}
}
