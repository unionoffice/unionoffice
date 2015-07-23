package br.com.unionoffice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.unionoffice.model.NotaFiscal;
import br.com.unionoffice.model.Pedido;
import br.com.unionoffice.model.Satisfacao;

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
	
	public List<Pedido> listarNaoFaturados() throws SQLException{
		List<Pedido> lista = new ArrayList<Pedido>();
		String sql = "SELECT * FROM view_pedidos WHERE numero_nf IS NULL ORDER BY numero_pedido DESC";
		stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Pedido p = new Pedido();
			p.setPedidoInterno(rs.getString("numero_pedido"));
			p.setCliente(rs.getString("cliente"));
			p.setEmailContato(rs.getString("email"));
			p.setContato(rs.getString("contato"));
			Calendar dataEnvioReceb = Calendar.getInstance();
			dataEnvioReceb.setTime(rs.getTimestamp("data_envio_receb"));
			p.setDataEnvioReceb(dataEnvioReceb);
			Date dataSatisf = rs.getTimestamp("data_envio_satisf");
			if (dataSatisf != null) {
				Calendar dataSat = Calendar.getInstance();
				dataSat.setTime(dataSatisf);
				p.setDataEnvioSatisf(dataSat);
			}			
			lista.add(p);
		}
		rs.close();
		stmt.close();
		return lista;
	}
	
	public List<Pedido> buscarNumero(String numero) throws SQLException{
		List<Pedido> lista = new ArrayList<Pedido>();
		Pedido p = null;
		String sql = "SELECT * FROM view_pedidos WHERE numero_pedido = ?";
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, numero);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			p = new Pedido();
			p.setPedidoInterno(rs.getString("numero_pedido"));
			p.setCliente(rs.getString("cliente"));
			p.setEmailContato(rs.getString("email"));
			p.setContato(rs.getString("contato"));
			Calendar dataEnvioReceb = Calendar.getInstance();
			dataEnvioReceb.setTime(rs.getTimestamp("data_envio_receb"));
			p.setDataEnvioReceb(dataEnvioReceb);
			Date dataSatisf = rs.getTimestamp("data_envio_satisf");
			if (dataSatisf != null) {
				Calendar dataSat = Calendar.getInstance();
				dataSat.setTime(dataSatisf);
				p.setDataEnvioSatisf(dataSat);
			}
			String nf = rs.getString("numero_nf");
			if (nf != null) {				
				NotaFiscal nota = new NotaFiscal();
				nota.setNumero(rs.getString("serie"), nf);
				nota.setChave(rs.getString("chave"));
				nota.setEmail(rs.getString("email_nf"));
				Date dataEnvioNF = rs.getTimestamp("data_envio");
				if (dataEnvioNF != null) {
					Calendar dataEnv = Calendar.getInstance();
					dataEnv.setTime(dataEnvioNF);
					nota.setDataEnvio(dataEnv);
				}
				p.setNotaFiscal(nota);
			}
			lista.add(p);
		}
		rs.close();
		stmt.close();
		return lista;
	}
	
	
	public List<Pedido> listarSemPesquisa() throws SQLException{
		List<Pedido> lista = new ArrayList<Pedido>();
		String sql = "SELECT * FROM view_pedidos WHERE data_envio_satisf IS NULL ORDER BY numero_nf DESC";
		stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Pedido p = new Pedido();
			p.setPedidoInterno(rs.getString("numero_pedido"));
			p.setCliente(rs.getString("cliente"));
			p.setEmailContato(rs.getString("email"));
			p.setContato(rs.getString("contato"));
			Calendar dataEnvioReceb = Calendar.getInstance();
			dataEnvioReceb.setTime(rs.getTimestamp("data_envio_receb"));
			p.setDataEnvioReceb(dataEnvioReceb);					
			String nf = rs.getString("numero_nf");			
			if (nf != null) {				
				NotaFiscal nota = new NotaFiscal();
				nota.setNumero(rs.getString("serie"), nf);
				nota.setChave(rs.getString("chave"));
				nota.setEmail(rs.getString("email_nf"));
				Date dataEnvioNF = rs.getTimestamp("data_envio");
				if (dataEnvioNF != null) {
					Calendar dataEnv = Calendar.getInstance();
					dataEnv.setTime(dataEnvioNF);
					nota.setDataEnvio(dataEnv);
				}				
				p.setNotaFiscal(nota);
			}
			lista.add(p);
		}
		rs.close();
		stmt.close();
		return lista;
	}
	
	public List<Pedido> listarTodos() throws SQLException{
		List<Pedido> lista = new ArrayList<Pedido>();
		String sql = "SELECT * FROM view_pedidos ORDER BY numero_pedido DESC";
		stmt = conexao.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Pedido p = new Pedido();
			p.setPedidoInterno(rs.getString("numero_pedido"));
			p.setCliente(rs.getString("cliente"));
			p.setEmailContato(rs.getString("email"));
			p.setContato(rs.getString("contato"));
			Calendar dataEnvioReceb = Calendar.getInstance();
			dataEnvioReceb.setTime(rs.getTimestamp("data_envio_receb"));
			p.setDataEnvioReceb(dataEnvioReceb);
			Date dataSatisf = rs.getTimestamp("data_envio_satisf");
			if (dataSatisf != null) {
				Calendar dataSat = Calendar.getInstance();
				dataSat.setTime(dataSatisf);
				p.setDataEnvioSatisf(dataSat);
			}
			String nf = rs.getString("numero_nf");
			if (nf != null) {				
				NotaFiscal nota = new NotaFiscal();
				nota.setNumero(rs.getString("serie"), nf);
				nota.setChave(rs.getString("chave"));
				nota.setEmail(rs.getString("email_nf"));
				Date dataEnvioNF = rs.getTimestamp("data_envio");
				if (dataEnvioNF != null) {
					Calendar dataEnv = Calendar.getInstance();
					dataEnv.setTime(dataEnvioNF);
					nota.setDataEnvio(dataEnv);
				}
				p.setNotaFiscal(nota);
			}
			lista.add(p);
		}
		rs.close();
		stmt.close();
		return lista;
	}
	
	public void excluir(Pedido pedido) throws SQLException{		
		conexao.setAutoCommit(false);
		String sql = "DELETE FROM pedido WHERE numero = ?";
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, pedido.getPedidoInterno());
		if (pedido.getNotaFiscal() != null) {
			String sql2 = "DELETE FROM nota_fiscal WHERE numero = ?";
			PreparedStatement stmt2 = conexao.prepareStatement(sql2);
			stmt2.setString(1, pedido.getNotaFiscal().getNum());
			stmt2.execute();
		}
		stmt.execute();
		conexao.commit();
		stmt.close();
	}
	
	public void gravarNf(Pedido pedido) throws SQLException{
		String sqlNf = "INSERT INTO nota_fiscal(numero,serie,chave,data_envio,email_nf) VALUES(?,?,?,NOW(),?)";
		String sqlPedido = "UPDATE pedido SET numero_nf = ? WHERE numero = ?";
		conexao.setAutoCommit(false);
		stmt = conexao.prepareStatement(sqlNf);
		PreparedStatement stmt2 = conexao.prepareStatement(sqlPedido);
		stmt.setString(1, pedido.getNotaFiscal().getNum());
		stmt.setString(2, pedido.getNotaFiscal().getSerie());
		stmt.setString(3,pedido.getNotaFiscal().getChave());
		stmt.setString(4, pedido.getNotaFiscal().getEmail());
		stmt2.setString(1, pedido.getNotaFiscal().getNum());
		stmt2.setString(2, pedido.getPedidoInterno());
		stmt.execute();
		stmt2.execute();
		conexao.commit();
		stmt.close();
		stmt2.close();
	}
	
	public void atualizaSat(Pedido pedido) throws SQLException {
		String sql = "UPDATE pedido SET data_envio_satisf = NOW() WHERE numero = ?";
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, pedido.getPedidoInterno());
		stmt.execute();
		stmt.close();
	}
	
	public void pontuar(Satisfacao satisfacao) throws SQLException{
		String sql = "UPDATE satisfacao SET data_resposta = NOW(), quest1 = ?, quest2 = ?, quest3 = ?, comentarios = ? WHERE id = ?";
		stmt = conexao.prepareStatement(sql);
		stmt.setInt(1, satisfacao.getQuestoes()[0]);
		stmt.setInt(2, satisfacao.getQuestoes()[1]);
		stmt.setInt(3, satisfacao.getQuestoes()[2]);
		stmt.setString(4, satisfacao.getComentarios());
		stmt.setInt(5, satisfacao.getId());
		stmt.execute();
		stmt.close();
	}
		
}
