package com.betacom.dischi.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.request.OrdineRequest;
import com.betacom.dischi.response.ResponseBase;
import com.betacom.dischi.services.interfaces.OrdineService;

@RestController
@RequestMapping("/rest/ordine")
public class OrdineController {

	@Autowired
	Logger log;
	
	@Autowired
	OrdineService ordineServ;
	
	@PostMapping("/create")
	public ResponseBase create(@RequestBody(required = true) OrdineRequest request) {
		log.debug("Aggiungi prodotto: " + request);
		ResponseBase response = new ResponseBase();
		response.setRc(true);
		try {
			ordineServ.create(request);
			response.setMsg("Ordine inviato con successo");
		} catch (CustomException e) {
			log.error(e.getMessage());
			response.setRc(false);
			response.setMsg(e.getMessage());
		
		}
		return response;
	}
	
	@PostMapping("/delete")
	public ResponseBase delete(@RequestBody(required = true) OrdineRequest request) {
		log.debug("Elimina ordine: " + request);
		ResponseBase response = new ResponseBase();
		response.setRc(true);
		try {
			ordineServ.delete(request);
			response.setMsg("Ordine eliminato con successo");
		} catch (CustomException e) {
			log.error(e.getMessage());
			response.setRc(false);
			response.setMsg(e.getMessage());
		
		}
		return response;
	}
	
	@PostMapping("/update")
	public ResponseBase update(@RequestBody(required = true) OrdineRequest request) {
		log.debug("Elimina ordine: " + request);
		ResponseBase response = new ResponseBase();
		response.setRc(true);
		try {
			ordineServ.update(request);
			response.setMsg("Ordine aggiornato con successo");
		} catch (CustomException e) {
			log.error(e.getMessage());
			response.setRc(false);
			response.setMsg(e.getMessage());
		
		}
		return response;
	}
}
