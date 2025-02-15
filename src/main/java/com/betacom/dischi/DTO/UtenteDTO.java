package com.betacom.dischi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UtenteDTO {
	//@JsonIgnore // evito di esporre un campo nel mapping

	private Integer idUtente;
	private String username;
	private String email;
	private String roles;
	private ClienteDTO cliente;
	private UtenteDTO() {
		super();
	}
	public static class Builder{
		private Integer idUtente;
		private String username;
		private String email;
		private String roles;
		private ClienteDTO cliente;
		
		public Builder() {}

	
		public Builder idUtente(Integer idUtente) {
			this.idUtente = idUtente;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		
		public Builder roles(String roles) {
			this.roles = roles;
			return this;
		}

		public Builder cliente(ClienteDTO cliente) {
			this.cliente = cliente;
			return this;
		}
		
		public UtenteDTO build() {
			UtenteDTO dto = new UtenteDTO();
			dto.idUtente= this.idUtente;
			dto.username= this.username;
			dto.email = this.email;
			dto.roles = this.roles;
			dto.cliente = this.cliente;
			return dto;
		}
		
		
		
	}

	public Integer getIdUtente() {
		return idUtente;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	

	
	public String getRoles() {
		return roles;
	}
	
	public ClienteDTO getCliente() {
		return cliente;
	}
	
	@Override
	public String toString() {
		return "UtenteDTO [idUtente=" + idUtente + ", username=" + username + ", email=" + email  + ", roles=" + roles + ", cliente=" + cliente + "]";
	}
	
	
}
