package br.com.unionoffice.model;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class NotaFiscal {
	private String serie;
	private String numero;
	private String destinatario;
	private String documento;
	private String chave;	
	private String email;
	private Calendar dataEnvio;
	
	public NotaFiscal() {
		
	}
	

	public NotaFiscal(File arquivo) throws SAXException, IOException,
			ParserConfigurationException {
		lerXML(arquivo);
	}

	public String getNumero() {
		return serie + " / " + numero;
	}
	
	public String getNum(){
		return numero;
	}
	
	public String getSerie(){
		return serie;
	}

	public void setNumero(String serie, String numero) {
		this.serie = serie;
		this.numero = numero;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		if (documento.length() == 14) {
			this.documento = documento.substring(0, 2) + "."
					+ documento.substring(2, 5) + "."
					+ documento.substring(5, 8) + "/"
					+ documento.substring(8, 12) + "-"
					+ documento.substring(12);
		} else {
			this.documento = documento.substring(0, 3) + "."
					+ documento.substring(3, 6) + "."
					+ documento.substring(6, 9) + "-" + documento.substring(9);
		}

	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	private void lerXML(File arquivo) throws SAXException, IOException,
			ParserConfigurationException {
		// Element nota = doc.getRootElement();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		Document document = factory.newDocumentBuilder().parse(arquivo);
		Node serie = document.getDocumentElement()
				.getElementsByTagName("serie").item(0);
		Node nNF = document.getDocumentElement().getElementsByTagName("nNF")
				.item(0);
		NodeList docList = document.getDocumentElement().getElementsByTagName(
				"CNPJ");
		Node doc;
		if (docList.getLength() <= 2 && !arquivo.getName().contains("procNfe")) {
			doc = document.getDocumentElement().getElementsByTagName("CPF")
					.item(0);
		} else {
			doc = docList.item(1);
		}
		Node chave = document.getDocumentElement()
				.getElementsByTagName("chNFe").item(0);
		Node dest = document.getDocumentElement().getElementsByTagName("xNome")
				.item(1);
		this.setNumero(serie.getTextContent(), nNF.getTextContent());
		this.setDocumento(doc.getTextContent());
		this.setDestinatario(dest.getTextContent());
		this.setChave(chave.getTextContent());
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
}
