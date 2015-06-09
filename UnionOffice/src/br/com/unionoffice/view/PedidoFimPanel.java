package br.com.unionoffice.view;

import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import br.com.unionoffice.model.Pedido;
import br.com.unionoffice.tablemodel.PedidoFimTableModel;

import com.sun.glass.events.KeyEvent;

public class PedidoFimPanel extends JPanel{
	JComboBox<String> cbFiltrar;
	JFormattedTextField tfBuscar;
	JButton btFiltrar;
	JLabel lbBuscar;
	List<Pedido> pedidos;
	JTable tbPedidos;
	JScrollPane pnPedidos;
	
	public PedidoFimPanel(){
		inicializarComponentes();
		definirEventos();
	}
	
	private void inicializarComponentes(){
		// cbFiltrar
		cbFiltrar = new JComboBox<String>();
		cbFiltrar.setLocation(5,10);
		cbFiltrar.setSize(130,25);
		cbFiltrar.addItem("Não faturados");
		cbFiltrar.addItem("Sem pesquisa");
		cbFiltrar.addItem("Todos");
		
		//lbBuscar
		lbBuscar = new JLabel("Buscar:");
		lbBuscar.setLocation(145,10);
		lbBuscar.setSize(45,25);
		
		// tfBuscar
		try {
			MaskFormatter mskPedido = new MaskFormatter("AA####/####");
			tfBuscar = new JFormattedTextField(mskPedido);
			tfBuscar.setLocation(200,10);
			tfBuscar.setSize(100,25);
			tfBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// btFiltrar
		btFiltrar = new JButton("Buscar");
		btFiltrar.setLocation(310,10);
		btFiltrar.setSize(90,25);
		
		// tbPedidos
		tbPedidos = new JTable();
		tbPedidos.getTableHeader().setReorderingAllowed(false);
		tbPedidos.getTableHeader().setResizingAllowed(false);
		tbPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		criarTabela(null);
		
		// pnPedidos
		pnPedidos = new JScrollPane(tbPedidos);
		pnPedidos.setLocation(5,45);
		pnPedidos.setSize(500,550);
		
		setLayout(null);
		add(cbFiltrar);
		add(lbBuscar);
		add(tfBuscar);
		add(btFiltrar);
		add(pnPedidos);
	}
	
	private void criarTabela(List<Pedido> lista){
		if (lista == null) {
			tbPedidos.setModel(new PedidoFimTableModel());
		}else{
			tbPedidos.setModel(new PedidoFimTableModel(lista));
		}
		DefaultTableCellRenderer cellRight = new DefaultTableCellRenderer();
		cellRight.setHorizontalAlignment(SwingConstants.RIGHT);

		tbPedidos.setRowHeight(20);
		tbPedidos.getColumnModel().getColumn(0).setPreferredWidth(80);
		tbPedidos.getColumnModel().getColumn(1).setPreferredWidth(80);
		tbPedidos.getColumnModel().getColumn(2).setPreferredWidth(290);
		tbPedidos.getColumnModel().getColumn(3).setPreferredWidth(50);
	}
	
	private void definirEventos(){
		tfBuscar.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Toolkit tk = Toolkit.getDefaultToolkit();
				tk.setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);				
			}
		});
	}
}
