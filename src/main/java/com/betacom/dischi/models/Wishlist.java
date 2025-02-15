package com.betacom.dischi.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="wishlist")
public class Wishlist {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private Integer idWishlist;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "prodotto_wishlist",
			joinColumns = @JoinColumn(name="id_wishlist"),
			inverseJoinColumns = @JoinColumn(name="id_prodotto")
	)
	private List<Prodotto> prodotti;
	
	@OneToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	public Integer getIdWishlist() {
		return idWishlist;
	}

	public void setIdWishlist(Integer idWishlist) {
		this.idWishlist = idWishlist;
	}

	public List<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(List<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
