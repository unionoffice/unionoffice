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

public class SatisfacaoDao {
	private Connection conexao;
	PreparedStatement stmt;

	public SatisfacaoDao() throws SQLException {
		conexao = ConnectionFactory.getConnection();
	}

	public Satisfacao buscar(String numeroNf) throws SQLException {
		Satisfacao sat = null;
		String sql = "SELECT * FROM satisfacao WHERE numero_nf = ?";
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, numeroNf);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			sat = new Satisfacao();
			sat.setId(rs.getInt("id"));
			sat.setEmail(rs.getString("email"));
			Calendar dataEnvio = Calendar.getInstance();
			dataEnvio.setTime(rs.getTimestamp("data_envio"));
			sat.setDataEnvio(dataEnvio);
			Date dataResposta = rs.getDate("data_resposta");
			if (dataResposta != null) {
				Calendar dataResp = Calendar.getInstance();
				dataResp.setTime(dataResposta);
				sat.setDataResposta(dataResp);
				int respostas[] = new int[3];
				respostas[0] = rs.getInt("quest1");
				respostas[1] = rs.getInt("quest2");
				respostas[2] = rs.getInt("quest3");
				sat.setQuestoes(respostas);
				sat.setComentarios(rs.getString("comentarios"));
			}
		}
		return sat;
	}
	
	public void criar(Satisfacao sat, String nf) throws SQLException{
		String sql = "INSERT INTO satisfacao(numero_nf, email, data_envio) VALUES(?,?,NOW())";
		stmt = conexao.prepareStatement(sql);
		stmt.setString(1, nf);
		stmt.setString(2,sat.getEmail());
		stmt.execute();
		stmt.close();
	}
	
	public void pontuar(Satisfacao sat) throws SQLException{
		String sql = "UPDATE satisfacao SET data_resposta = NOW(), quest1 = ?, quest2 = ?, quest 3 = ?, comentarios = ? WHERE id = ?";
		stmt = conexao.prepareStatement(sql);
		stmt.setInt(1, sat.getQuestoes()[0]);
		stmt.setInt(2, sat.getQuestoes()[1]);
		stmt.setInt(3, sat.getQuestoes()[2]);
		stmt.setString(4, sat.getComentarios());
		stmt.setInt(5, sat.getId());
		stmt.execute();
		stmt.close();
	}

}
