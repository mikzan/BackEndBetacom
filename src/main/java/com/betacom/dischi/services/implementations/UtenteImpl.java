package com.betacom.dischi.services.implementations;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.betacom.dischi.DTO.SignInDTO;
import com.betacom.dischi.DTO.UtenteDTO;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.models.Utente;
import com.betacom.dischi.repository.IClienteRepository;
import com.betacom.dischi.repository.IUtenteRepository;
import com.betacom.dischi.request.SignInRequest;
import com.betacom.dischi.request.UtenteRequest;
import com.betacom.dischi.services.interfaces.UtenteService;
import com.betacom.dischi.utilities.enums.Roles;
import jakarta.transaction.Transactional;
import static com.betacom.dischi.utilities.Utility.*;
@Service
public class UtenteImpl implements UtenteService{

	private  IUtenteRepository utenteRepo;
    private  IClienteRepository clienteRepo;
    private  Logger log;
    private  PasswordEncoder passwordEncoder;

    public UtenteImpl(IUtenteRepository utenteRepo, 
                       IClienteRepository clienteRepo, 
                       Logger log, 
                       PasswordEncoder passwordEncoder) {
        this.utenteRepo = utenteRepo;
        this.clienteRepo = clienteRepo;
        this.log = log;
        this.passwordEncoder = passwordEncoder;
    }


	@Override
	public SignInDTO signIn(SignInRequest req)  {
	    log.debug("Signin utente: " + req.getUsername());
	    SignInDTO resp = new SignInDTO();
	    
	    Optional<Utente> utente = utenteRepo.findByUsername(req.getUsername());
	    
	    if (utente.isEmpty()) {
	        resp.setLogged(false); 
	    } else {
	        if (passwordEncoder.matches(req.getPassword(), utente.get().getPassword())) {
	            resp.setLogged(true); 
	            resp.setRole(utente.get().getRoles().toString());
	        } else {
	            resp.setLogged(false); 
	        }
	    }
	    
	    return resp;
	}


	@Transactional
	@Override
	public void createUser(UtenteRequest req) throws CustomException {
	 log.debug("Crea utente: "+req);
     Optional<Utente> optUtente = utenteRepo.findByUsername(req.getUsername());	
     if(optUtente.isPresent()) {
    	 throw new CustomException("Utente con questo username già esistente");
    	 
     }
     if(req.getRoles() == null) {
    	 req.setRoles("UTENTE");
     }
     Optional<Cliente> optCliente = clienteRepo.findById(req.getIdCliente());
     if(optCliente.isEmpty()) {
         log.debug("Cliente con ID: "+req.getIdCliente()+ "non trovato");
    	 throw new CustomException("Cliente non trovato");
     }
     Cliente cliente = optCliente.get();
     if(cliente.getUtente() != null) {
    	 throw new CustomException("Cliente già associato a un altro utente");
     }
     Utente utente = new Utente();

     utente.setPassword(passwordEncoder.encode(req.getPassword()));

     utente.setRoles(Roles.valueOf(req.getRoles()));
     utente.setEmail(req.getEmail());
     utente.setUsername(req.getUsername());
     utente.setCliente(cliente);
     //se cliente e gia legato a dati di un altro utente allora lancio
     // eccezione
     utenteRepo.save(utente);

     cliente.setUtente(utente);
     clienteRepo.save(cliente);
	}

	@Override
	public List<UtenteDTO> listAll(Integer idUtente,String username,String email) {
	    List<Utente> listaUtenti = utenteRepo.filteredUsers(idUtente, username, email);
	    return listaUtenti.stream()
	            .map(u -> buildUtenteDTO(u)) 
	            .toList();
	}
	
	@Transactional
	@Override
	public void deleteUtente(Integer id) throws CustomException{
		log.debug("Cancellazione utente con ID: "+id);
		Utente utente = utenteRepo.findById(id)
				.orElseThrow(() -> new CustomException("Utente non trovato"));
		Cliente cliente = utente.getCliente();
		if(cliente != null) {
			cliente.setUtente(null);
			clienteRepo.save(cliente);
		}
		utenteRepo.delete(utente);
		
	}
	
	@Override
	public void updateUtente(UtenteRequest req) throws CustomException{
		log.debug("Aggiornamento utente con ID: "+req.getIdUtente());
		Utente utente =  utenteRepo.findById(req.getIdUtente())
				.orElseThrow(() -> new CustomException("Utente non trovato"));
        Optional<Utente> optUtente = utenteRepo.findByUsername(req.getUsername());
		if(!utente.getUsername().equals(req.getUsername())){
			if(optUtente.isPresent()) {
				throw new CustomException("Utente con questo username già esistente");
			}
			utente.setUsername(req.getUsername());
		}
		if(req.getPassword() != null && !req.getPassword().isEmpty()) {
			utente.setPassword(passwordEncoder.encode(req.getPassword()));
		}
		// se utente ha ruolo admin allora puoi cambiare il ruolo e non viceversa
		if(req.getIsAdmin()){//valueof
			utente.setRoles(Roles.valueOf(req.getRoles()));
		}
		// TODO: EMAIL
		req.setEmail(req.getEmail());
		utenteRepo.save(utente);
		
	}
	  
	@Override
	public UtenteDTO listById(Integer id) throws CustomException {
		log.debug("Visualizzazione dati utente con ID: " + id);
	    Utente utente = utenteRepo.findById(id)
	    		.orElseThrow(() -> new CustomException("Utente non trovato"));
		return buildUtenteDTO(utente);
	}

}
