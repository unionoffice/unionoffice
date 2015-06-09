package br.com.unionoffice.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.unionoffice.model.Pedido;

public class PedidoFimTableModel extends AbstractTableModel {
	private final String[] COLUNAS = { "Pedido", "NF", "Cliente", "Enviar" };
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		Pedido p = pedidos.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return p.getPedidoInterno();
		case 1:
			return p.getNotaFiscal().getNumero();
		case 2:
			return p.getCliente();
		case 3:
			return p.isEnviarSatisf();
		}
		throw new IndexOutOfBoundsException("Coluna inválida");
	}

}
