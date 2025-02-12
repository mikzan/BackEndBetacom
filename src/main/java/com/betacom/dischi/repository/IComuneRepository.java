package com.betacom.dischi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.betacom.dischi.models.Comune;

public interface IComuneRepository extends JpaRepository<Comune, Integer> {
   
}
