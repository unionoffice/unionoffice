package br.com.unionoffice.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.unionoffice.model.Pedido;

public class PedidoTableModel extends AbstractTableModel {
	private String[] colunas = { "Nº", "Cliente", "Contato", "E-mail", "X" };
	private List<Pedido> pedidos;

	public PedidoTableModel(){
		pedidos = new ArrayList<Pedido>();
	}
	
	public PedidoTableModel(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return pedidos.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Pedido pedido = pedidos.get(linha);
		switch (coluna) {
		case 0:
			return pedido.getPedidoInterno();
		case 1:
			return pedido.getCliente();
		case 2:
			return pedido.getContato();
		case 3:
			return pedido.getEmailContato();
		case 4:
			return pedido.isEnviar();
		default:
			throw new IndexOutOfBoundsException("Índice inválido");
		}
	}
	
	@Override
	public String getColumnName(int column) {	
		return colunas[column];
	}

}
