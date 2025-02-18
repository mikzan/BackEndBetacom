package com.betacom.dischi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.dischi.models.Prodotto;
import com.betacom.dischi.utilities.enums.Formato;

public interface IProdottoRepository extends JpaRepository<Prodotto, Integer>{
	
	Optional<Prodotto> findByTitoloAndArtista(String titolo, String artista);

	@Query(name="prodotti.listaFiltrata")
	List<Prodotto> prodottiFiltrati(
			@Param("idProdotto")Integer idProdotto,
			@Param("titolo")String titolo,
			@Param("artista")String artista,
			@Param("genere")String genere,
			@Param("annoPubblicazione")Integer annoPubblicazione
			);
	
	
	@Query(name="prodotti.listaPerFormato")
	List<Prodotto> prodottiPerFormato(
			@Param("formato") Formato formato
			);

}