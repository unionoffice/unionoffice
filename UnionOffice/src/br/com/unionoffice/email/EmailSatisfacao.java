package br.com.unionoffice.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.com.unionoffice.model.Pedido;

public class EmailSatisfacao {
	// parâmetros da mensagem
	private String destinatario;
	private String copias;
	private String assunto;
	private String mensagem;
	private HtmlEmail email;
	private Pedido pedido;

	public EmailSatisfacao(Pedido pedido) throws EmailException {
		this.email = new HtmlEmail();
		email.setHostName(EmailConfig.HOSTNAME);
		email.setFrom(EmailConfig.USERNAME, "Adm. Vendas - Union Office");
		email.setAuthentication(EmailConfig.USERNAME, EmailConfig.PASSWORD);
		email.setSmtpPort(EmailConfig.PORTASMTP);
		// preenche o e-mail com as informações do pedido
		mensagem = pedido.getMsgSatisfacao();
		destinatario = "roberto@unionoffice.com.br";//*/pedido.getEmailContato();
		copias = "roberto@unionoffice.com.br";// */EmailConfig.USERNAME;
		assunto = "Conclusão do Pedido " + pedido.getPedidoInterno() + " - "
				+ pedido.getCliente();
	}

	public void enviar() throws EmailException {
		email.addTo(this.destinatario);
		email.addBcc(copias);		
		email.setSubject(assunto);
		email.setHtmlMsg(this.mensagem);
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

	public String getCopias() {
		return copias;
	}

	public void setCopias(String copias) {
		this.copias = copias;
	}


	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
}
