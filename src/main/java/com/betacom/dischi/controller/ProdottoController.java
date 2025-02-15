package com.betacom.dischi.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.dischi.DTO.ProdottoDTO;
import com.betacom.dischi.request.ProdottoRequest;
import com.betacom.dischi.response.ResponseBase;
import com.betacom.dischi.response.ResponseList;
import com.betacom.dischi.services.interfaces.ProdottoService;
import com.betacom.dischi.utilities.enums.Formato;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/rest/prodotto")
public class ProdottoController {

	@Autowired
	Logger log;
	
	@Autowired
	ProdottoService prodottoService;
	
	
	@PostMapping("/create")
	public ResponseBase create(@RequestBody(required = true)ProdottoRequest req) {
		log.debug("Create prodotto: " + req);
		ResponseBase response = new ResponseBase();
		response.setRc(true);
		response.setMsg("Prodotto creato con successo!");
		try {
			prodottoService.create(req);
		}catch(Exception e) {
			response.setMsg(e.getMessage());
			response.setRc(false);
		}
		return response;
		
	}
	
	@PostMapping("/update")
	public ResponseBase update(@RequestBody(required = true) ProdottoRequest req) {
		ResponseBase response = new ResponseBase();
		response.setRc(true);
		response.setMsg("Prodotto aggiornato con successo!");
		try {
			prodottoService.update(req);
		}catch(Exception e) {
			response.setMsg(e.getMessage());
			response.setRc(false);
		}
		return response;
	}
	
	
	@PostMapping("/delete")
	public ResponseBase delete(@RequestBody(required = true) ProdottoRequest req) {
		ResponseBase response = new ResponseBase();
		response.setRc(true);
		response.setMsg("Prodotto eliminato con successo!");
		try {
			prodottoService.delete(req);
		}catch(Exception e) {
			response.setMsg(e.getMessage());
			response.setRc(false);
		}
		return response;
	}
	
	
	@GetMapping("/list")
	public ResponseList<ProdottoDTO> listAll(Integer idProdotto, String titolo, String artista, String genere, 
			Integer annoPubblicazione){
		ResponseList<ProdottoDTO> response = new ResponseList<ProdottoDTO>();
		response.setRc(true);
		try {
			response.setDati(prodottoService.listAll(idProdotto, titolo, artista, genere, annoPubblicazione));
	
		}catch(Exception e) {
			response.setRc(false);
			response.setMsg(e.getMessage());
		}
		return response;
	}
	
	@GetMapping("/listFormato")
	public ResponseList<ProdottoDTO> listFormato(Formato formato){
		ResponseList<ProdottoDTO> response = new ResponseList<ProdottoDTO>();
		response.setRc(true);
		try {
			response.setDati(prodottoService.listPerFormato(formato));
	
		}catch(Exception e) {
			response.setRc(false);
			response.setMsg(e.getMessage());
		}
		return response;
	}
	
	
}
	
	

