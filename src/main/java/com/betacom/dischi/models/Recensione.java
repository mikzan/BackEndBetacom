package com.betacom.dischi.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "recensioni")
public class Recensione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRecensione;
	
	@Column(length = 1000)
	private String descrizione;
	
	@Column(nullable = false)
	private Integer stelle;
	
	@Column
	private LocalDate dataCreazione;
	@ManyToOne()
	@JoinColumn(name= "id_cliente" , nullable=false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="id_prodotto")
	private Prodotto prodotto;
	
	public Prodotto getProdotto() {
		return prodotto;
	}
	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}
	public Integer getIdRecensione() {
		return idRecensione;
	}
	public void setIdRecensione(Integer idRecensione) {
		this.idRecensione = idRecensione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Integer getStelle() {
		return stelle;
	}
	public void setStelle(Integer stelle) {
		this.stelle = stelle;
	}
	public LocalDate getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
}
