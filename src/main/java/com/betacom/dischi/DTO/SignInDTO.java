package com.betacom.dischi.DTO;

import java.time.LocalDate;

public class SignInDTO {

	private Boolean logged;
	private String role;
    private Integer idUtente;
    private Integer idCliente;
    private LocalDate dataRegistrazione;
    private String username;
    private String email;
    private String token;
 
	public SignInDTO() {
		super();
	}
	public SignInDTO(Boolean logged, String role) {
		super();
		this.logged = logged;
		this.role = role;
	}
	public Boolean isLogged() {
		return logged;
	}
	public String getRole() {
		return role;
	}
	public void setLogged(Boolean logged) {
		this.logged = logged;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Boolean getLogged() {
		return logged;
	}
	public Integer getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public LocalDate getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(LocalDate dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}



}
