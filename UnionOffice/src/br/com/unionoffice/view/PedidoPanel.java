package br.com.unionoffice.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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

import org.apache.commons.mail.EmailException;

import br.com.unionoffice.dao.PedidoDao;
import br.com.unionoffice.email.EmailPedido;
import br.com.unionoffice.model.Pedido;
import br.com.unionoffice.model.Representante;
import br.com.unionoffice.tablemodel.PedidoTableModel;

public class PedidoPanel extends JPanel {
	List<Pedido> pedidos;
	JFileChooser fcDialog;
	JLabel lbArquivo;
	JTextField tfArquivo;
	JButton btArquivo, btEnviar;
	JScrollPane spPedidos;
	JTable tbPedidos;
	final JProgressBar progressBar = new JProgressBar();

	public PedidoPanel() {
		inicializarComponentes();
		definirEventos();
	}

	private void inicializarComponentes() {
		// fcDialog
		fcDialog = new JFileChooser();
		String userDir = System.getProperty("user.home");
		fcDialog.setCurrentDirectory(new File(userDir + "/Desktop"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Arquivo TXT", "txt");
		fcDialog.setFileFilter(filter);

		// lbArquivo
		lbArquivo = new JLabel("Arquivo:");
		lbArquivo.setLocation(5, 10);
		lbArquivo.setSize(60, 25);

		// tfArquivo
		tfArquivo = new JTextField();
		tfArquivo.setLocation(70, 10);
		tfArquivo.setSize(390, 25);

		// btArquivo
		btArquivo = new JButton("Escolher");
		btArquivo.setLocation(465, 10);
		btArquivo.setSize(90, 25);

		// tbPedidos
		tbPedidos = new JTable();
		tbPedidos.getTableHeader().setReorderingAllowed(false);
		tbPedidos.getTableHeader().setResizingAllowed(false);
		tbPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		criarTabela(null);

		// spPedidos
		spPedidos = new JScrollPane(tbPedidos);
		spPedidos.setLocation(5, 45);
		spPedidos.setSize(680, 555);

		// progressBar
		progressBar.setLocation(5, 610);
		progressBar.setSize(560, 40);
		progressBar.setStringPainted(true);

		// btEnviar
		btEnviar = new JButton("Enviar");
		btEnviar.setLocation(575, 610);
		btEnviar.setSize(110, 40);
		btEnviar.setEnabled(false);

		setLayout(null);
		add(lbArquivo);
		add(tfArquivo);
		add(btArquivo);
		add(spPedidos);
		add(progressBar);
		add(btEnviar);
	}

	private void criarTabela(List<Pedido> pedidos) {
		if (pedidos == null) {
			tbPedidos.setModel(new PedidoTableModel());
		} else {
			tbPedidos.setModel(new PedidoTableModel(pedidos));
		}
		DefaultTableCellRenderer cellRight = new DefaultTableCellRenderer();
		cellRight.setHorizontalAlignment(SwingConstants.RIGHT);

		tbPedidos.setRowHeight(20);
		tbPedidos.getColumnModel().getColumn(0).setPreferredWidth(80);
		tbPedidos.getColumnModel().getColumn(1).setPreferredWidth(150);
		tbPedidos.getColumnModel().getColumn(2).setPreferredWidth(50);
		tbPedidos.getColumnModel().getColumn(2).setCellRenderer(cellRight);
		tbPedidos.getColumnModel().getColumn(3).setPreferredWidth(100);
		tbPedidos.getColumnModel().getColumn(4).setPreferredWidth(210);
		tbPedidos.getColumnModel().getColumn(5).setPreferredWidth(20);

	}

	private void definirEventos() {
		btArquivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fcDialog.setFileFilter(fcDialog.getChoosableFileFilters()[1]);
				int returnValue = fcDialog.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fcDialog.getSelectedFile();
					tfArquivo.setText(file.getAbsolutePath());
					lerPedidos(file);
				}
			}
		});

		tbPedidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					PedidoTableModel model = (PedidoTableModel) tbPedidos
							.getModel();
					Pedido p = model.getPedido(tbPedidos.getSelectedRow());
					new VisualizaEmail(p);
				}
				super.mouseClicked(e);
			}
		});

		btEnviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				progressBar.setMaximum(pedidos.stream()
						.filter((pedido) -> pedido.isEnviar()).toArray().length);
				for (final Pedido pedido : pedidos) {
					if (pedido.isEnviar()) {
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

									final EmailPedido email = new EmailPedido(
											pedido);
									email.enviar();
									new PedidoDao().gravarPedido(pedido);
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
			}
		});
	}

	private void lerPedidos(File file) {
		// long inicio = System.currentTimeMillis();
		pedidos = new ArrayList<Pedido>();
		List<Map<String, String>> contatos = new ArrayList<Map<String, String>>();
		List<Map<String, String>> representantes = new ArrayList<Map<String, String>>();
		try {
			Scanner leitor = new Scanner(file);
			Representante rep = new Representante();
			while (leitor.hasNext()) {
				// ler os representantes
				String linha = leitor.nextLine();
				if (linha.startsWith("01")) {
					linha = linha.substring(3);
					linha = linha.replace('|', ';');
					String[] campos = linha.split(";");
					Map<String, String> map = new HashMap<String, String>();
					for (int i = 0; i < campos.length - 1; i++) {
						// System.out.println(campos[i]+"|"+ campos[++i]);
						map.put(campos[i], campos[++i]);
					}
					representantes.add(map);

				}
				// ler os contatos
				if (linha.startsWith("03")) {
					linha = linha.substring(3);
					linha = linha.replace('|', ';');
					String[] campos = linha.split(";");
					Map<String, String> map = new HashMap<String, String>();
					for (int i = 0; i < campos.length - 1; i++) {
						map.put(campos[i], campos[++i]);
					}
					contatos.add(map);
				}
				// ler os pedidos
				else if (linha.startsWith("04")) {
					linha = linha.substring(3);
					linha = linha.replace('|', ';');
					String[] campos = linha.split(";");
					Map<String, String> map = new HashMap<String, String>();
					for (int i = 0; i < campos.length - 1; i++) {
						// System.out.println(campos[i]+"|"+campos[++i]);
						map.put(campos[i], campos[++i]);
					}
					Pedido p = new Pedido();
					rep.setSigla(map.get("000000"));
					p.setNumero(map.get("000001"));
					p.setCliente(map.get("000003"));
					p.setContato(map.get("000059"));
					p.setPedidoCliente(map.get("000071"));
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

					Date data = format.parse(map.get("000043"));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(data);
					p.setDataEntrega(calendar);
					p.setEnviar(true);
					for (Map<String, String> item : representantes) {
						if (item.get("000000").equals(rep.getSigla())) {
							rep.setNome(item.get("000001"));
							rep.setEmail(item.get("000011"));
							break;
						}
					}
					p.setRepresentante(rep);
					for (Map<String, String> item : contatos) {
						if (item.get("000000").equals(p.getCliente())
								&& item.get("000001").equals(p.getContato())) {
							p.setEmailContato(item.get("000006"));
							break;
						}
					}
					p.setValor(new BigDecimal(map.get("000020").replace(',',
							'.')));
					pedidos.add(p);
				}

			}
			leitor.close();
			criarTabela(pedidos);
			progressBar.setValue(0);
			btEnviar.setEnabled(true);
			// System.out.println(System.currentTimeMillis()-inicio);

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo",
					e.getMessage(), JOptionPane.ERROR_MESSAGE);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this,
					"Erro ao ler o data de entrega", e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
