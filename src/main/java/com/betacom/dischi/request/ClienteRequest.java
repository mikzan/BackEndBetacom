package com.betacom.dischi.request;


public class ClienteRequest {
	

	private Integer idCliente;
	
	private String nome;
	
	private String cognome;
	
	private String telefono;
	
	private String immagineCliente;
	


	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getImmagineCliente() {
		return immagineCliente;
	}

	public void setImmagineCliente(String immagineCliente) {
		this.immagineCliente = immagineCliente;
	}

	@Override
	public String toString() {
		return "ClienteRequest [idCliente=" + idCliente + ", nome=" + nome + ", cognome=" + cognome + ", telefono="
				+ telefono + ", immagineCliente=" + immagineCliente + "]";
	}


    

}
