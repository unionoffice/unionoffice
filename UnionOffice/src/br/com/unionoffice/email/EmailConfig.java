package br.com.unionoffice.email;

public class EmailConfig {
	// constantes
	public static class ConfigNfe {
		public static final String REMETENTE = "Faturamento - Union Office";
		public static final String USERNAME = "faturamento@unionoffice.com.br";
		public static final String PASSWORD = "faturamento321";
		public static final int PORTASMTP = 587;
		public static final String HOSTNAME = "smtp.unionoffice.com.br";
	}
	
	public static class ConfigVendas {
		public static final String REMETENTE = "Vendas - Union Office";
		public static final String USERNAME = "vendas@unionoffice.com.br";
		public static final String PASSWORD = "Vendas2016";
		public static final int PORTASMTP = 587;
		public static final String HOSTNAME = "smtp.unionoffice.com.br";
	}
}
