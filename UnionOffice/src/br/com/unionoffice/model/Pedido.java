package br.com.unionoffice.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Pedido {
	private String numero;
	private String cliente;
	private String contato;
	private String emailContato;
	private String pedidoCliente;
	private Calendar dataEntrega;
	private Calendar dataEnvioReceb;
	private Calendar dataEnvioSatisf;
	private Representante representante;
	private BigDecimal valor;
	private boolean enviar;	
	private String mensagem;
	private NotaFiscal notaFiscal;
	private String pedidoInterno;

	public void setPedidoInterno(String pedidoInterno) {
		this.pedidoInterno = pedidoInterno;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getEmailContato() {
		return emailContato;
	}

	public void setEmailContato(String emailContato) {
		this.emailContato = emailContato;
	}

	public boolean isEnviar() {
		return enviar;
	}

	public void setEnviar(boolean enviar) {
		this.enviar = enviar;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getPedidoCliente() {
		return pedidoCliente;
	}

	public void setPedidoCliente(String pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}

	public String getPedidoInterno() {
		if (pedidoInterno == null) {
			pedidoInterno = this.representante.getSigla()
					+ this.numero.substring(4) + "/"
					+ this.numero.substring(0, 4);
		}
		return pedidoInterno;
	}

	public Calendar getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Calendar dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public String getMensagem() {
		if (mensagem == null) {
			mensagem = "<html><body>";
			mensagem += "<font size=\"4\" face=\"Arial\" color=\"#ba1419\"><b>Recebimento do Pedido:<br> <br></b>";
			mensagem += "À <b>" + this.getCliente() + "</b><br><br>";
			Calendar calendar = Calendar.getInstance();
			mensagem += "<b>"
					+ (this.getContato().toLowerCase().startsWith("sr") ? this
							.getContato() : "Sr(a)" + this.getContato())
					+ "</b>, "
					+ (calendar.get(Calendar.HOUR_OF_DAY) >= 12 ? "boa tarde!"
							: "bom dia!") + "<br><br>";
			mensagem += "É com satisfação que acusamos o recebimento de vosso pedido conforme dados abaixo:<br><br><br>";
			if (this.getPedidoCliente() != null
					&& !this.getPedidoCliente().trim().isEmpty()) {
				mensagem += "- Pedido Cliente: <b>" + this.getPedidoCliente()
						+ "</b><br><br>";
			}
			mensagem += "- Valor: <b>R$ "
					+ new DecimalFormat("###0.00").format(this.getValor())
					+ "</b><br><br>";
			mensagem += "- Previsão de entrega: até <b>"
					+ new SimpleDateFormat("dd/MM/yyyy").format(this
							.getDataEntrega().getTime())
					+ "</b>, em horário comercial<br><br>";
			mensagem += "- Pedido interno: <b>" + this.getPedidoInterno()
					+ "</b><br><br><br>";

			mensagem += "O referido pedido entrará em nossa linha de produção e em breve entraremos em contato com V.S. para o agendamento da entrega.<br><br>";
			mensagem += "Para agilizar a entrega, favor nos informar:<br><br>";
			mensagem += "1 - Contato (nome/tel/email):<br><br>";
			mensagem += "2 - Horário: <br><br>";
			mensagem += "3 - Restrições:<br><br>";
			mensagem += "4 - Observações:<br><br>";
			mensagem += "Qualquer discordância dos dados acima, favor entrar em contato através do telefone: (11) 5521-1664 ou e-mail: <a href=\"mailto:admvendas@unionoffice.com.br\"><font color=\"#2f3699\">admvendas@unionoffice.com.br</font></a><br><br>";
			mensagem += "Somos gratos por vossa atenção";
			mensagem += "</body></html>";
		}
		return mensagem;
	}

	public String getMsgSatisfacao() {
		String mensagem = "<html><body>";
		mensagem += "<font size=\"4\" face=\"Arial\" color=\"#ba1419\">";
		mensagem += "À <b>" + this.getCliente() + "</b><br><br>";
		Calendar calendar = Calendar.getInstance();
		mensagem += "<b>"
				+ (this.getContato().toLowerCase().startsWith("sr") ? this
						.getContato() : "Sr(a)" + this.getContato())
				+ "</b>, "
				+ (calendar.get(Calendar.HOUR_OF_DAY) >= 12 ? "boa tarde!"
						: "bom dia!") + "<br><br>";
		mensagem += "Comunicamos a conclusão da entrega de seu pedido <b>"
				+ this.getPedidoInterno() + "</b>, através da NF <b>"
				+ this.getNotaFiscal().getNum() + "</b>.<br><br>";
		mensagem += "Solicitamos a gentileza do preenchimento da Pesquisa de Satisfação abaixo:<br><br>";
		mensagem += "<b>1 - </b>O prazo de entrega estabelecido foi cumprido? <br><br>";
		mensagem += "(&nbsp;&nbsp;&nbsp;)Sim&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(&nbsp;&nbsp;&nbsp;)Não<br><br>";
		mensagem += "<b>2 - </b>A qualidade dos produtos recebidos atendeu às suas expectativas? <br><br>";
		mensagem += "(&nbsp;&nbsp;&nbsp;)Sim&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(&nbsp;&nbsp;&nbsp;)Não<br><br>";
		mensagem += "<b>3 - </b>Como foi o atendimento dado pelos colaboradores da Union durante este processo de compra? <br><br>";
		mensagem += "(&nbsp;&nbsp;&nbsp;)Ótimo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(&nbsp;&nbsp;&nbsp;)Bom&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		mensagem += "(&nbsp;&nbsp;&nbsp;)Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(&nbsp;&nbsp;&nbsp;)Ruim&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>";
		mensagem += "<b>4 - </b>Há alguma sugestão para a melhoria contínua de nossos serviços? Escreva abaixo. <br><br><br><br>";
		mensagem += "Qualquer anormalidade que eventualmente possa ocorrer em nossos produtos, ";
		mensagem += "nos contate através do tel.:<b>(11)5521-1664</b> ou e-mail: <a href=\"mailto:sac@unionoffice.com.br\">";
		mensagem += "<font color=\"#2f3699\">sac@unionoffice.com.br</font></a> mencionando o número da NF de entrega.</a><br><br>";
		mensagem += "Somos gratos por vossa atenção";
		mensagem += "</body></html>";
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public Calendar getDataEnvioReceb() {
		return dataEnvioReceb;
	}

	public void setDataEnvioReceb(Calendar dataEnvioReceb) {
		this.dataEnvioReceb = dataEnvioReceb;
	}

	public Calendar getDataEnvioSatisf() {
		return dataEnvioSatisf;
	}

	public void setDataEnvioSatisf(Calendar dataEnvioSatisf) {
		this.dataEnvioSatisf = dataEnvioSatisf;
	}



}
