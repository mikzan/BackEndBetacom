package com.betacom.dischi.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.betacom.dischi.models.Cliente;


public interface IClienteRepository extends JpaRepository<Cliente,Integer> {

//	@Query(name="clienti.filteredClients")
//	List<Cliente> filteredClients(
//			@Param("idCliente") Integer idCliente,
//			@Param("nome") String nome,
//			@Param("cognome") String cognome,
//            @Param("dataRegistrazione") LocalDate dataRegistrazione
//            // indirizzo,cap,provincia
//			
//			);
	
	@Query(name="clienti.filteredClients")
	List<Cliente> filteredClients(
			@Param("idCliente") Integer idCliente,
			@Param("nome") String nome,
			@Param("cognome") String cognome
			// AND (:dataRegistrazione IS NULL OR FUNCTION('DATE_FORMAT', c.dataRegistrazione, '%Y-%m-%d') LIKE CONCAT(:dataRegistrazione, '%'))

            // indirizzo,cap,provincia
			
			
			);
	 // Nuovo metodo per paginazione
    @Query("SELECT c FROM Cliente c WHERE (:idCliente IS NULL OR c.idCliente = :idCliente) " +
            "AND (:nome IS NULL OR c.nome LIKE %:nome%) " +
            "AND (:cognome IS NULL OR c.cognome LIKE %:cognome%) " +
            "AND (:cap IS NULL OR c.cap LIKE %:cap%) " +
            "AND (:comune IS NULL OR c.comune LIKE %:comune%) " +
            "AND (:provincia IS NULL OR c.provincia LIKE %:provincia%)")
    Page<Cliente> filteredClientsPageable(
            @Param("idCliente") Integer idCliente,
            @Param("nome") String nome,
            @Param("cognome") String cognome,
            @Param("cap") String cap,
            @Param("comune") String comune,
            @Param("provincia") String provincia,
            Pageable pageable
    );

}
