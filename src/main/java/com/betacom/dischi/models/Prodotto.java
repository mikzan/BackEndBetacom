package com.betacom.dischi.models;

import java.util.List;

import com.betacom.dischi.utilities.enums.Formato;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "prodotti")
public class Prodotto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProdotto; 
	
	@Column(nullable = false, length = 50)
	private Formato formato;
	
	@Column(nullable = false, length = 100)
	private String titolo;
	
	@Column(nullable = false,length = 100)
	private String artista;
	
	@Column(nullable = false,length = 100)
	private String genere;
	
	@Column(nullable = false, length = 1000)
	private String descrizione;
	
	@Column(nullable = false)
	private Integer annoPubblicazione;
	
	@Column(nullable = false)
	private Double prezzo;
	
	@Column
	private Integer quantita;
	
	@Column(length = 2000)
	private String immagineProdotto;
	
	@OneToMany(mappedBy = "prodotto",
			cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdottoOrdine> prodottiOrdine;
	
	@OneToMany(
			mappedBy = "prodotto",
			cascade = CascadeType.REMOVE)
	private List<Recensione> recensioni;
	
	@OneToMany(mappedBy = "prodotto",
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProdottoCarrello> prodottiCarrello;
	
	@ManyToMany(mappedBy = "prodotti")
	private List<Wishlist> prodottiWishlist;
	

	public Integer getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(Integer idProdotto) {
		this.idProdotto = idProdotto;
	}

	public Formato getFormato() {
		return formato;
	}

	public void setFormato(Formato formato) {
		this.formato = formato;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getAnnoPubblicazione() {
		return annoPubblicazione;
	}

	public void setAnnoPubblicazione(Integer annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public String getImmagineProdotto() {
		return immagineProdotto;
	}

	public void setImmagineProdotto(String immagineProdotto) {
		this.immagineProdotto = immagineProdotto;
	}

	public List<ProdottoOrdine> getProdottiOrdine() {
		return prodottiOrdine;
	}

	public void setProdottiOrdine(List<ProdottoOrdine> prodottiOrdine) {
		this.prodottiOrdine = prodottiOrdine;
	}

	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}

	public List<ProdottoCarrello> getProdottiCarrello() {
		return prodottiCarrello;
	}

	public void setProdottiCarrello(List<ProdottoCarrello> prodottiCarrello) {
		this.prodottiCarrello = prodottiCarrello;
	}

	public List<Wishlist> getProdottiWishlist() {
		return prodottiWishlist;
	}

	public void setProdottiWishlist(List<Wishlist> prodottiWishlist) {
		this.prodottiWishlist = prodottiWishlist;
	}

	
}
