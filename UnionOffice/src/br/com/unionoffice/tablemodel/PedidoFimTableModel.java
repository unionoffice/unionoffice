package br.com.unionoffice.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.unionoffice.model.Pedido;

public class PedidoFimTableModel extends AbstractTableModel {
	private final String[] COLUNAS = { "Pedido", "NF", "Cliente", "X" };
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
		if (columnIndex == 3) {
			return Boolean.class;
		}
		return super.getColumnClass(columnIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {	
		if (columnIndex == 3) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Pedido p = pedidos.get(rowIndex);
		if (columnIndex == 3) {						
			p.setEnviarSatisf((Boolean)aValue);
		}		
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
		case 3:
			return p.isEnviarSatisf();
		}
		throw new IndexOutOfBoundsException("Coluna inválida");
	}
	
	public Pedido getPedido(int linha) {
		return pedidos.get(linha);
	}

}
