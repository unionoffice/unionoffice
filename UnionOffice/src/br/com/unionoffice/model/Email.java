package br.com.unionoffice.model;

import java.io.File;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class Email {
	// constantes
	private final String REMETENTE = "Faturamento - Union Office";
	private final String USERNAME = "faturamento@unionoffice.com.br";
	private final String PASSWORD = "faturamento321";
	private final int PORTASMTP = 58;
	private final String HOSTNAME = "smtp.unionoffice.com.br";
	// parâmetros da mensagem
	private String[] destinatario;
	private String[] copias;
	private String[] copiasOculas;
	private String assunto;
	private String mensagem;
	private HtmlEmail email;
	private List<File> anexos;

	public Email() throws EmailException {
		this.email = new HtmlEmail();
		email.setHostName(HOSTNAME);
		email.setFrom(USERNAME, REMETENTE);
		email.setAuthentication(USERNAME, PASSWORD);
		email.setSmtpPort(PORTASMTP);
	}

	public void enviar() throws EmailException {
		email.addTo(this.destinatario);
		for (String dest : copias) {
			if (dest.length() != 0) {
				email.addCc(dest);	
			}	
		}
		for (String dest : copiasOculas) {
			if (dest.length() != 0) {
				email.addBcc(dest);	
			}			
		}
		email.setSubject(assunto);
		email.setHtmlMsg(this.mensagem);		
		for (File f : anexos) {
			email.attach(f);
		}
		email.send();		
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public HtmlEmail getEmail() {
		return email;
	}

	public void setEmail(HtmlEmail email) {
		this.email = email;
	}

	public static String criaMensagem(NotaFiscal nota, boolean deposito) {
		String retorno = new String();
		retorno = "<html><body>";
		retorno += "<font size=\"4\" face=\"Arial\" color=\"#ba1419\"><b>Prezado cliente,<br> <br></b>";
		retorno += "Esta mensagem refere-se a Nota Fiscal Eletrônica Nacional:<br><br>";
		retorno += "<b>Série / Número: <font color=\"#2f3699\">"
				+ nota.getNumero() + "</b><br>";
		retorno += "<b>Nome / Razão Social: <font color=\"#2f3699\">"
				+ nota.getDestinatario() + "</b><br>";
		retorno += "<b>CPF / CNPJ: <font color=\"#2f3699\">"
				+ nota.getDocumento() + "</b><br><br>";
		retorno += "Para verificar a autorização da SEFAZ referente à nota acima mencionada, acesse o endereço:<br>";
		retorno += "<a href=\"http://www.nfe.fazenda.gov.br/portal/consulta.aspx?tipoConsulta=completa&tipoConteudo=Xb\"><font color=\"#2f3699\">http://www.nfe.fazenda.gov.br/portal/consulta.aspx?tipoConsulta=completa&tipoConteudo=Xb</a><br><br>";
		retorno += "<b>Chave de acesso: <font color=\"#2f3699\">"
				+ nota.getChave() + "</b><br><br>";
		retorno += "Segue anexo o Danfe e o arquivo XML da referida nota, que pode ser acessado com o visualizador da NFe, obtido no seguinte endereço:<br>";
		retorno += "<a href =\"http://www.nfe.fazenda.gov.br/portal/download.aspx?tipoConteudo=s/eYl\"><font color=\"#2f3699\">http://www.nfe.fazenda.gov.br/portal/download.aspx?tipoConteudo=s/eYl</a><br><br>";
		if (!deposito) {
			retorno += "Segue anexo o(s) boleto(s) bancário(s) para pagamento.<br><br>";
		} else {
			retorno += "Segue abaixo os dados de nossa conta corrente para pagamento:<br>";
			retorno += "<b>Banco:</b> Itaú - ";
			retorno += "<b>Agência:</b> 8195 - ";
			retorno += "<b>C/C:</b> 07742-8<br><br>";
		}
		retorno += "Qualquer dúvida estamos à disposição, no telefone: (11) 5521-1664<br><br>";
		retorno += "Somos gratos por vossa atenção";
		retorno += "</body></html>";

		return retorno;
		// message.setHeader( "Disposition-Notification-To", "emailDeEnvio" );
	}

	public List<File> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<File> anexos) {
		this.anexos = anexos;
	}

	public String[] getCopias() {
		return copias;
	}

	public void setCopias(String[] copias) {
		this.copias = copias;
	}

	public String[] getCopiasOculas() {
		return copiasOculas;
	}

	public void setCopiasOculas(String[] copiasOculas) {
		this.copiasOculas = copiasOculas;
	}

	public String[] getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String[] destinatario) {
		this.destinatario = destinatario;
	}
}
