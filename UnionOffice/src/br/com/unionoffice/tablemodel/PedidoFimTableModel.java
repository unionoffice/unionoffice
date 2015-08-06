package br.com.unionoffice.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.unionoffice.model.Pedido;

public class PedidoFimTableModel extends AbstractTableModel {
	private final String[] COLUNAS = { "Pedido", "NF", "Cliente" };
	private List<Pedido> pedidos;

	public PedidoFimTableModel(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public PedidoFimTableModel() {
		this.pedidos = new ArrayList<Pedido>();
	}

	@Override
	public int getRowCount() {
		return pedidos.size();
	}

	@Override
	public int getColumnCount() {
		return COLUNAS.length;
	}

	@Override
	public String getColumnName(int column) {
		return COLUNAS[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return super.getColumnClass(columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Pedido p = pedidos.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return p.getPedidoInterno();
		case 1:
			if (p.getNotaFiscal() != null) {
				return p.getNotaFiscal().getNumero();
			}
			return "";
		case 2:
			return p.getCliente();

		}
		throw new IndexOutOfBoundsException("Coluna inválida");
	}

	public Pedido getPedido(int linha) {
		return pedidos.get(linha);
	}

}
