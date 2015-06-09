package br.com.unionoffice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.unionoffice.model.NotaFiscal;
import br.com.unionoffice.model.Pedido;

public class PedidoDao {
	private Connection conexao;
	PreparedStatement stmt;
	
	public PedidoDao() throws SQLException{
		conexao = ConnectionFactory.getConnection();
	}
	
	public void gravarPedido(Pedido pedido) throws SQLException{
		String sql = "INSERT INTO pedido(numero,cliente,contato,email,data_envio_receb) VALUES(?,?,?,?,NOW())";
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, pedido.getPedidoInterno());
		stmt.setString(2, pedido.getCliente());
		stmt.setString(3, pedido.getContato());
		stmt.setString(4, pedido.getEmailContato());
		stmt.execute();
		stmt.close();
	}
	
	public List<Pedido> listarAbertos() throws SQLException{
		List<Pedido> lista = new ArrayList<Pedido>();
		String sql = "SELECT * FROM view_pedidos WHERE data_envio_satisf IS NULL";
		stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Pedido p = new Pedido();
			p.setPedidoInterno(rs.getString("numero_pedido"));
			p.setCliente(rs.getString("cliente"));
			p.setContato(rs.getString("contato"));
			String nf = rs.getString("numero_nf");
			System.out.println(nf);
			if (nf != null) {
				
			}
			
		}
		rs.close();
		return null;
	}
		
}
