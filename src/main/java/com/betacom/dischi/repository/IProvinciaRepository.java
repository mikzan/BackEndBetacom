package com.betacom.dischi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.dischi.models.Provincia;

public interface IProvinciaRepository extends JpaRepository<Provincia, Integer> {
    Provincia findByCodice(String codice);
    Provincia findByNome(String nome);
}