package br.com.unionoffice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	// private static final String URL =
	// "jdbc:mysql://192.168.0.200/union_office_db"; // bd da locaweb
	// pedido_union
	private static final String URL = "jdbc:mysql://179.188.16.185:3306/pedido_union?autoReconnect=true"; // bd
	// da
	// locaweb
	// pedido_union
	// private static final String USER = "root";
	private static final String USER = "pedido_union";
	private static final String PASSWORD = "Uni*office"; // mesma senha para o
															// banco da locaweb
	private static Connection conexao;

	public static Connection getConnection() throws SQLException {
		if (conexao == null || conexao.isClosed() || !conexao.isValid(10)) {
			conexao = DriverManager.getConnection(URL, USER, PASSWORD);
		}
		return conexao;
	}

	public static void close() throws SQLException {
		if (conexao != null) {
			conexao.close();
		}
	}
}
