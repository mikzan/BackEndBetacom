package com.betacom.dischi.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="clienti")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private Integer idCliente;
	
	@Column(nullable = false, length = 50)
	private String nome;
	
	@Column(nullable= false, length = 50)
	private String cognome;
	
	@Column(nullable = false , unique=true, length = 15)
	private String telefono;
	
	@Column(length = 2000)
	private String immagineCliente;
	
	@Column(nullable=false)
    private LocalDate dataRegistrazione;	
	
	@Column
	private String via;
	
	@Column(length=5)
	private String cap;
	
	@Column(length=2)
	private String provincia;
	
	@Column
	private String comune;
	
	// campo indirizzo aggiunto, importante che un cliente abbia un indirizzo
//	@Column(nullable=false)
//	private String indirizzo;
	
	
	@OneToMany(mappedBy="cliente", fetch= FetchType.EAGER, cascade= CascadeType.REMOVE)
	private List<Ordine> ordini;
	
	@OneToOne(mappedBy= "cliente", cascade= CascadeType.REMOVE)
	private Carrello carrello;
	
	@OneToOne(mappedBy= "cliente", cascade= CascadeType.REMOVE)
	private Utente utente;
	
	@OneToOne(mappedBy= "cliente", cascade= CascadeType.REMOVE)
    private Wishlist wishlist;
	
	@OneToMany(mappedBy="cliente", fetch= FetchType.EAGER, cascade= CascadeType.REMOVE)
    private List<Recensione> recensioni;

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

	public List<Ordine> getOrdini() {
		return ordini;
	}

	public void setOrdini(List<Ordine> ordini) {
		this.ordini = ordini;
	}

	public Carrello getCarrello() {
		return carrello;
	}

	public void setCarrello(Carrello carrello) {
		this.carrello = carrello;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Wishlist getWishlist() {
		return wishlist;
	}

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}

	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}

	public String getImmagineCliente() {
		return immagineCliente;
	}

	public void setImmagineCliente(String immagineCliente) {
		this.immagineCliente = immagineCliente;
	}

	public LocalDate getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(LocalDate dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	
  

	public String getVia() {
		return via;
	}

	public String getCap() {
		return cap;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}
	
	


	
}
