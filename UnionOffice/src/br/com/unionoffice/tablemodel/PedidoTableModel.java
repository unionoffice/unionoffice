package br.com.unionoffice.tablemodel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.unionoffice.model.Pedido;

public class PedidoTableModel extends AbstractTableModel {
	private String[] colunas = { "Nº", "Cliente", "Valor", "Contato", "E-mail",
			"X" };
	private List<Pedido> pedidos;

	public PedidoTableModel() {
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
			DecimalFormat formatador = new DecimalFormat("###0.00");
			return formatador.format(pedido.getValor());
		case 3:
			return pedido.getContato();
		case 4:
			return pedido.getEmailContato();
		case 5:
			return pedido.isEnviar();
		default:
			throw new IndexOutOfBoundsException("Índice inválido");
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex >= 2 && columnIndex <= 5) {
			return true;
		}
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Pedido p = pedidos.get(rowIndex);
		if (columnIndex == 2) {
			String preco = aValue.toString().replace(',', '.');
			p.setValor(new BigDecimal(preco));
			p.setMensagem(null);
		} else if (columnIndex == 3) {
			p.setContato(aValue.toString());
			p.setMensagem(null);
		} else if (columnIndex == 4) {
			p.setEmailContato(aValue.toString());
			p.setMensagem(null);
		} else if (columnIndex == 5) {
			p.setEnviar((Boolean) aValue);
		}
	}

	public Pedido getPedido(int linha) {
		return pedidos.get(linha);
	}

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 5) {
			return Boolean.class;
		}
		return super.getColumnClass(columnIndex);
	}

}
