package br.com.unionoffice.model;

import java.math.BigDecimal;
import java.util.Calendar;

public class Pedido {
	private String numero;
	private String cliente;
	private String contato;
	private String emailContato;
	private String pedidoCliente;
	private Calendar dataEntrega;
	private String representante;
	private BigDecimal valor;
	private boolean enviar;

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
		System.out.println("NUMERO: "+this.numero);
		String pedidoInterno = this.representante + this.numero.substring(4)
				+ "/" + this.numero.substring(0, 4);
		return pedidoInterno;
	}

	public Calendar getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Calendar dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}
}
