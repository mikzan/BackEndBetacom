package com.betacom.dischi.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.betacom.dischi.DTO.ClienteDTO;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.request.ClienteRequest;

public interface ClienteService {

	
	ClienteDTO listById(Integer id) throws CustomException;

	void create(ClienteRequest req) throws CustomException;

	void update(ClienteRequest req) throws CustomException;

	void delete(ClienteRequest req) throws CustomException;

<<<<<<< Updated upstream
	List<ClienteDTO> listAll(Integer idCliente, String nome, String cognome);
=======

	Page<ClienteDTO> listAll(Integer idCliente, String nome, String cognome, String cap, String comune,
			String provincia, Pageable pageable);
>>>>>>> Stashed changes

}
