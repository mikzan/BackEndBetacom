package com.betacom.dischi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.models.Prodotto;
import com.betacom.dischi.models.Recensione;

public interface IRecensioneRepository extends JpaRepository<Recensione,Integer>  {

	Optional<Recensione> findByClienteAndProdotto(Cliente cliente,Prodotto prodotto) throws CustomException;

	Boolean existsByClienteAndProdotto(Cliente cliente,Prodotto prodotto) throws CustomException;

	List<Recensione> findByProdotto(Prodotto prodotto) throws CustomException;

	@Query(name="recensioni.filteredReviews")
	List<Recensione> filteredReviews(
			@Param("idRecensione") Integer idRecensione,
			@Param("stelle") Integer stelle
			// AND (:dataCreazione IS NULL OR FUNCTION('DATE_FORMAT', c.dataCreazione, '%Y-%m-%d') LIKE CONCAT(:dataCreazione, '%'))
			);
}
