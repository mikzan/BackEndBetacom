package com.betacom.dischi.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.dischi.DTO.ClienteDTO;
import com.betacom.dischi.request.ClienteRequest;
import com.betacom.dischi.response.ResponseBase;
import com.betacom.dischi.response.ResponseList;
import com.betacom.dischi.response.ResponseObject;
import com.betacom.dischi.services.interfaces.ClienteService;


    @CrossOrigin(origins="*")
	@RestController
	@RequestMapping("/rest/cliente")
	public class ClienteController {
		@Autowired
		Logger log;
		@Autowired
		ClienteService clienteService;
		
		

		@GetMapping("/listAll")
<<<<<<< Updated upstream
		public ResponseList<ClienteDTO>list(Integer idCliente,String nome,String cognome) {
			log.debug("Lista di tutti i clienti: ");
			ResponseList<ClienteDTO> response = new ResponseList<ClienteDTO>();
			response.setRc(true);
			try {
				response.setDati(clienteService.listAll(idCliente,nome,cognome)); 
				response.setRc(true);
		        response.setMsg("Visualizzazione lista clienti");
			}catch(Exception e) {
				log.error(e.getMessage());
				response.setMsg(e.getMessage());
				response.setRc(false);
			}
			return response;
			
=======
		public ResponseList<ClienteDTO> listAll(
		    Integer idCliente, 
		    String nome, 
		    String cognome, 
		    String cap, 
		    String comune, 
		    String provincia,
		    @RequestParam(defaultValue = "0") int page, 
		    @RequestParam(defaultValue = "10") int size 
		) {
		    log.debug("Lista paginata di clienti: ");
		    ResponseList<ClienteDTO> response = new ResponseList<ClienteDTO>();
		    response.setRc(true);
		    try {
		        Pageable pageable = PageRequest.of(page, size);
		        Page<ClienteDTO> pageClienti = clienteService.listAll(idCliente, nome, cognome, cap, comune, provincia, pageable);
		        response.setDati(pageClienti.getContent());
		        response.setRc(true);
		        response.setMsg("Visualizzazione lista clienti paginata");
		    } catch (Exception e) {
		        log.error(e.getMessage());
		        response.setMsg(e.getMessage());
		        response.setRc(false);
		    }
		    return response;
>>>>>>> Stashed changes
		}

		@GetMapping("/listById")
		public ResponseObject<ClienteDTO> listById(@RequestParam Integer id){
			log.debug("List " + id );
			ResponseObject<ClienteDTO> response = new ResponseObject<ClienteDTO>();
			try {
				response.setDati(clienteService.listById(id));
				response.setRc(true);
		        response.setMsg("Visualizzazione dati cliente con id: "+id);

			}catch(Exception e) {
				log.error(e.getMessage());
				response.setMsg(e.getMessage());
				response.setRc(false);
			}
			return response;
		}
		
		
		
		@PostMapping("/create")
		public ResponseBase create(@RequestBody(required = true) ClienteRequest req) {
			ResponseBase response = new ResponseBase();
			log.debug(req.toString());
			try {
				clienteService.create(req);
				response.setRc(true);
		        response.setMsg("Cliente creato con successo!");
			}
			catch(Exception e) {
				response.setMsg(e.getMessage());
				response.setRc(false);
			}
			return response;
		}
		
		@PostMapping("/update")
		public ResponseBase update(@RequestBody(required = true) ClienteRequest req) {
			ResponseBase response = new ResponseBase();
			log.debug(req.toString());
			try {
				clienteService.update(req);
				response.setRc(true);
		        response.setMsg("Cliente aggiornato con successo!");

			}
			catch(Exception e) {
				response.setMsg(e.getMessage());
				response.setRc(false);
			}		
			return response;
		}
		
		@PostMapping("/delete")
		public ResponseBase delete(@RequestBody(required = true) ClienteRequest req) {
		    ResponseBase response = new ResponseBase();
		    try {
		        clienteService.delete(req);
		        response.setRc(true); // 
		        response.setMsg("Cliente eliminato con successo!");
		    } catch (Exception e) {
		        response.setRc(false); 
		        response.setMsg(e.getMessage());
		    }
		    return response;
		}
		


}
