package br.com.unionoffice.model;

import java.util.Calendar;
import java.util.Date;

public class Satisfacao {
	private int id;	
	private String email;
	private Calendar dataEnvio;
	private Calendar dataResposta;
	private int[] questoes;
	private String comentarios;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Calendar dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Calendar getDataResposta() {
		return dataResposta;
	}

	public void setDataResposta(Calendar dataResposta) {
		this.dataResposta = dataResposta;
	}

	public int[] getQuestoes() {
		return questoes;
	}

	public void setQuestoes(int[] questoes) {
		this.questoes = questoes;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

}
