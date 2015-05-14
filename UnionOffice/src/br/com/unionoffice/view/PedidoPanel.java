package br.com.unionoffice.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.unionoffice.model.Pedido;
import br.com.unionoffice.tablemodel.PedidoTableModel;

public class PedidoPanel extends JPanel {
	List<Pedido> pedidos;
	JFileChooser fcDialog;
	JLabel lbArquivo;
	JTextField tfArquivo;
	JButton btArquivo;
	JScrollPane spPedidos;
	JTable tbPedidos;

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
		tbPedidos.setModel(new PedidoTableModel());
		tbPedidos.getColumnModel().getColumn(0).setPreferredWidth(70);
		tbPedidos.getColumnModel().getColumn(1).setPreferredWidth(100);
		tbPedidos.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbPedidos.getColumnModel().getColumn(3).setPreferredWidth(200);
		tbPedidos.getColumnModel().getColumn(4).setPreferredWidth(30);

		// spPedidos
		spPedidos = new JScrollPane(tbPedidos);
		spPedidos.setLocation(5, 45);
		spPedidos.setSize(550, 555);

		setLayout(null);
		add(lbArquivo);
		add(tfArquivo);
		add(btArquivo);
		add(spPedidos);
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
	}

	private void lerPedidos(File file) {
		long inicio = System.currentTimeMillis();
		pedidos = new ArrayList<Pedido>();
		List<Map<String, String>> contatos = new ArrayList<Map<String, String>>();
		try {
			Scanner leitor = new Scanner(file);
			while (leitor.hasNext()) {
				String linha = leitor.nextLine();				
				// ler os contatos
				if (linha.startsWith("03")) {
					linha = linha.substring(3);
					linha = linha.replace('|', ';');
					String[] campos = linha.split(";");
					Map<String, String> map = new HashMap<String, String>();
					for (int i = 0; i < campos.length - 1; i++) {						 
						map.put(campos[i], campos[i + 1]);
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
						map.put(campos[i], campos[i + 1]);
					}
					Pedido p = new Pedido();
					p.setRepresentante(map.get("000000"));
					p.setNumero(map.get("000001"));
					p.setCliente(map.get("000003"));
					p.setContato(map.get("000059"));
					for (Map<String, String> item : contatos) {
						if (item.get("000000").equals(p.getCliente())
								&& item.get("000001").equals(p.getContato())) {
							p.setEmailContato(item.get("000006"));
							break;
						}						
					}
				}
			}
			leitor.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo",
					e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
		long fim = System.currentTimeMillis()-inicio;
		System.out.println(fim);
	}

	private void gerarLog(Pedido pedido) {

	}
}
