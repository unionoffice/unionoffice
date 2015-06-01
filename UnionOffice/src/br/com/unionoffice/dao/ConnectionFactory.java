package br.com.unionoffice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String URL = "jdbc:mysql://192.168.0.200/union_office_db";
	// private static final String DRIVER = "com.mysql.jdbc.DRIVER";
	private static final String USER = "root";
	private static final String PASSWORD = "Uni*office";
	private static Connection conexao;

	public static Connection getConnection() throws SQLException {
		if (conexao == null) {
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
