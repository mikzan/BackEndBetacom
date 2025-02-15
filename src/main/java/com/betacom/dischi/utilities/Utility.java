package com.betacom.dischi.utilities;

import com.betacom.dischi.DTO.CarrelloDTO;
import com.betacom.dischi.DTO.ClienteDTO;
import com.betacom.dischi.DTO.OrdineDTO;
import com.betacom.dischi.DTO.ProdottoCarrelloDTO;
import com.betacom.dischi.DTO.ProdottoDTO;
import com.betacom.dischi.DTO.ProdottoOrdineDTO;
import com.betacom.dischi.DTO.RecensioneDTO;
import com.betacom.dischi.DTO.UtenteDTO;
import com.betacom.dischi.DTO.WishlistDTO;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Carrello;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.models.Ordine;
import com.betacom.dischi.models.Prodotto;
import com.betacom.dischi.models.ProdottoCarrello;
import com.betacom.dischi.models.ProdottoOrdine;
import com.betacom.dischi.models.Recensione;
import com.betacom.dischi.models.Utente;
import com.betacom.dischi.models.Wishlist;
import com.betacom.dischi.request.ProdottoRequest;

public class Utility {

	public static void validazioneValoriProdotto(ProdottoRequest req) throws CustomException{
		if(req.getFormato() == null)
			throw new CustomException("Inserisci il formato del prodotto");
		if(req.getTitolo() == null)
			throw new CustomException("Inserisci il titolo del prodotto");
		if(req.getArtista() == null)
			throw new CustomException("Inserisci l'artista del prodotto");
		if(req.getGenere() == null)
			throw new CustomException("Inserisci il genere del prodotto");	
		if(req.getDescrizione() == null)
			throw new CustomException("Inserisci una descrizione del prodotto");
		if(req.getAnnoPubblicazione() == null)
			throw new CustomException("Inserisci l'anno di pubblicazione del prodotto");
		if(req.getPrezzo() == null)
			throw new CustomException("Inserisci il prezzo del prodotto");
		if(req.getQuantita() == null)
			throw new CustomException("Inserisci una quantità disponibile del prodotto");
	}
	
	public static ProdottoDTO buildProdottoDTO(Prodotto prodotto) {
		return new ProdottoDTO.Builder()
				.idProdotto(prodotto.getIdProdotto())
				.formato(prodotto.getFormato().toString())
				.titolo(prodotto.getTitolo())
				.artista(prodotto.getArtista())
				.genere(prodotto.getGenere())
				.descrizione(prodotto.getDescrizione())
				.annoPubblicazione(prodotto.getAnnoPubblicazione())
				.prezzo(prodotto.getPrezzo())
				.quantita(prodotto.getQuantita())
				.immagineProdotto(prodotto.getImmagineProdotto())
				.prodottiCarrello(prodotto.getProdottiCarrello().stream().map(p -> buildProdottoCarrelloNoProdottoDTO(p)).toList())
				.prodottiWishlist(prodotto.getProdottiWishlist().stream().map(p -> buildWishlistDTOnoProdotto(p)).toList())
				.recensioni(prodotto.getRecensioni().stream().map(r -> buildRecensioneDTOnoProdotto(r)).toList())
				.build();
	}
	private static RecensioneDTO buildRecensioneDTOnoProdotto(Recensione recensione) {
		return new RecensioneDTO.Builder()
				.idRecensione(recensione.getIdRecensione())
				.descrizione(recensione.getDescrizione())
				.stelle(recensione.getStelle())
				.dataCreazione(recensione.getDataCreazione())
				.cliente(buildClienteDTOperRecensioneNoPodotto(recensione.getCliente()))
				.build();
	}
	private static ClienteDTO buildClienteDTOperRecensioneNoPodotto(Cliente cliente) {
		return new ClienteDTO.Builder()
				.idCliente(cliente.getIdCliente())
				.nome(cliente.getNome())
				.cognome(cliente.getCognome())
				.immagineCliente(cliente.getImmagineCliente())
				.build();
	}
	private static ClienteDTO buildClienteDTOnoRecensione(Cliente cliente) {
		return new ClienteDTO.Builder()
				.idCliente(cliente.getIdCliente())
				.nome(cliente.getNome())
				.cognome(cliente.getCognome())
				.telefono(cliente.getTelefono())
				.immagineCliente(cliente.getImmagineCliente())
				.via(cliente.getVia())
				.cap(cliente.getCap())
				.provincia(cliente.getProvincia())
				.comune(cliente.getComune())
				.dataRegistrazione(cliente.getDataRegistrazione())
				.ordini(cliente.getOrdini().stream().map(o -> buildOrdineDTO(o)).toList())
				.carrello(buildCarrelloDTO(cliente.getCarrello())) 
				.utente(buildUtenteDTOnoCliente(cliente.getUtente()))
				.wishlist(buildWishlistDTO(cliente.getWishlist()))
				.build();
	}
	public static RecensioneDTO buildRecensioneDTO(Recensione recensione) {
		return new RecensioneDTO.Builder()
				.idRecensione(recensione.getIdRecensione())
				.descrizione(recensione.getDescrizione())
				.stelle(recensione.getStelle())
				.dataCreazione(recensione.getDataCreazione())
				.prodotto(buildProdottoDTOnoRecensione(recensione.getProdotto()))
				.cliente(buildClienteDTOnoRecensione(recensione.getCliente()))
				.build();
	}
	private static ProdottoDTO buildProdottoDTOnoRecensione(Prodotto prodotto) {
			return new ProdottoDTO.Builder()
					.idProdotto(prodotto.getIdProdotto())
					.formato(prodotto.getFormato().toString())
					.titolo(prodotto.getTitolo())
					.artista(prodotto.getArtista())
					.genere(prodotto.getGenere())
					.descrizione(prodotto.getDescrizione())
					.annoPubblicazione(prodotto.getAnnoPubblicazione())
					.prezzo(prodotto.getPrezzo())
					.quantita(prodotto.getQuantita())
					.immagineProdotto(prodotto.getImmagineProdotto())
					.build();
		}

	 
	public static ClienteDTO buildClienteDTO(Cliente cliente) {
		return new ClienteDTO.Builder()
				.idCliente(cliente.getIdCliente())
				.nome(cliente.getNome())
				.cognome(cliente.getCognome())
				.telefono(cliente.getTelefono())
				.immagineCliente(cliente.getImmagineCliente())
				.via(cliente.getVia())
				.cap(cliente.getCap())
				.provincia(cliente.getProvincia())
				.comune(cliente.getComune())
				.dataRegistrazione(cliente.getDataRegistrazione())
				.ordini(cliente.getOrdini().stream().map(o -> buildOrdineDTO(o)).toList())
				.carrello(buildCarrelloDTO(cliente.getCarrello())) 
				.utente(buildUtenteDTOnoCliente(cliente.getUtente())) //DA FARE
			    .wishlist(buildWishlistDTO(cliente.getWishlist()))
				.recensioni(cliente.getRecensioni().stream().map(r -> buildRecensioneDTO(r)).toList())
				.build();
	} 
	private static UtenteDTO buildUtenteDTOnoCliente(Utente utente) {
		if (utente == null) {
			return null;
		}
		return new UtenteDTO.Builder()
				.idUtente(utente.getIdUtente())
				.username(utente.getUsername())
				.email(utente.getEmail())
				.roles(utente.getRoles().toString())
				.build();
	}
	
	public static UtenteDTO buildUtenteDTO(Utente utente) {
		return new UtenteDTO.Builder()
				.idUtente(utente.getIdUtente())
				.username(utente.getUsername())
				.email(utente.getEmail())
				.roles(utente.getRoles().toString())
				.cliente(buildClienteDTOnoUtente(utente.getCliente()))
				.build();
	}
	private static ClienteDTO buildClienteDTOnoUtente(Cliente cliente) {
		if (cliente == null) {
			return null;
		}
		return new ClienteDTO.Builder()
				.idCliente(cliente.getIdCliente())
				.nome(cliente.getNome())
				.cognome(cliente.getCognome())
				.telefono(cliente.getTelefono())
				.immagineCliente(cliente.getImmagineCliente())
				.via(cliente.getVia())
				.cap(cliente.getCap())
				.provincia(cliente.getProvincia())
				.comune(cliente.getComune())
				.dataRegistrazione(cliente.getDataRegistrazione())
				.ordini(cliente.getOrdini().stream().map(o -> buildOrdineDTO(o)).toList())
				.carrello(buildCarrelloDTO(cliente.getCarrello())) 
			    .wishlist(buildWishlistDTO(cliente.getWishlist()))
				.recensioni(cliente.getRecensioni().stream().map(r -> buildRecensioneDTO(r)).toList())
				.build();
	}

	public static OrdineDTO buildOrdineDTO(Ordine ordine) {
		 return new OrdineDTO.Builder()
				 .idOrdine(ordine.getIdOrdine())
				 .dataOrdine(ordine.getDataOrdine())
				 .totaleImporto(ordine.getTotaleImporto())
				 .spedito(ordine.getSpedito())
				 .prodotti(ordine.getProdotti().stream().map(p -> buildProdottoOrdineDTO(p)).toList())
				 .build();
	 }
	public static ProdottoOrdineDTO buildProdottoOrdineDTO(ProdottoOrdine po) {
		return new ProdottoOrdineDTO.Builder()
				.id(po.getId())
				.prodotto(buildProdottoDTO(po.getProdotto()))
				.quantita(po.getQuantita())
				.prezzoAcquisto(po.getPrezzoAcquisto())
				.build();
	}
	 
	public static CarrelloDTO buildCarrelloDTO(Carrello carrello) {
		if(carrello == null) {
			return null;
		}
		return new CarrelloDTO.Builder()
				.idCarrello(carrello.getIdCarrello())
				.prodotti(carrello.getProdotti().stream().map(p -> buildProdottoCarrelloDTO(p)).toList())
				.build();
	}
	public static ProdottoCarrelloDTO buildProdottoCarrelloDTO(ProdottoCarrello pc) {
		return new ProdottoCarrelloDTO.Builder()
				.id(pc.getId())
				.quantita(pc.getQuantita())
				.prodotto(buildProdottoDTO(pc.getProdotto()))				 
				.build();
	}
	 
	public static WishlistDTO buildWishlistDTO(Wishlist wishlist) {
		if(wishlist == null) {
			return null;
		}
		return new WishlistDTO.Builder()
				.idWishlist(wishlist.getIdWishlist())
				.prodotti(wishlist.getProdotti().stream().map(p -> buildProdottoDTO(p)).toList())
				.build();
	}
	private static WishlistDTO buildWishlistDTOnoProdotto(Wishlist wishlist) {
		if(wishlist == null) {
			return null;
		}
		return new WishlistDTO.Builder()
				.idWishlist(wishlist.getIdWishlist())
				.build();
	}
	private static ProdottoCarrelloDTO buildProdottoCarrelloNoProdottoDTO(ProdottoCarrello pc) {
		return new ProdottoCarrelloDTO.Builder()
				.id(pc.getId())
				.quantita(pc.getQuantita())		 
				.build();
	}
}
