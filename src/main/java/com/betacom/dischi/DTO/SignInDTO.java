package com.betacom.dischi.DTO;

public class SignInDTO {
	
	private Boolean logged;
	private String role;
    private Integer idUtente; 
    private Integer idCliente;

	
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

	

}
