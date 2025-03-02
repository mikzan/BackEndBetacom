package com.betacom.dischi.services.implementations;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.betacom.dischi.DTO.SignInDTO;
import com.betacom.dischi.models.Utente;
import com.betacom.dischi.repository.IUtenteRepository;
import com.betacom.dischi.request.SignInRequest;
import com.betacom.dischi.response.ResponseObject;
import com.betacom.dischi.security.JwtToken;
import com.betacom.dischi.services.interfaces.AuthService;
import com.betacom.dischi.services.interfaces.UtenteService;

@Service
public class AuthImpl implements AuthService {

	@Autowired
	JwtToken jwtToken;
	@Autowired
	Logger log;
	@Autowired
	IUtenteRepository utenteRepo;
	@Autowired
	UtenteService utenteServ;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public ResponseObject<SignInDTO> signIn(SignInRequest req) throws Exception {
	    ResponseObject<SignInDTO> response = new ResponseObject<>();

	    Optional<Utente> utenteOpt = utenteRepo.findByUsername(req.getUsername());
	    if (utenteOpt.isEmpty()) {
	        response.setRc(false);
	        response.setMsg("Username non corretto");
	        return response;
	    }
	    Utente utente = utenteOpt.get();
	    log.debug("Username trovato: ", req.getUsername());

	    if (passwordEncoder.matches(req.getPassword(), utente.getPassword())) {

	        String token = jwtToken.generateToken(req.getUsername());
	        log.debug("Generato token per l'utente: ", req.getUsername());

	        SignInDTO signInDTO = new SignInDTO();
	        signInDTO.setToken(token);
	        signInDTO.setRole(utente.getRoles().toString());
	        signInDTO.setIdUtente(utente.getIdUtente());
	        signInDTO.setUsername(utente.getUsername());
	        signInDTO.setEmail(utente.getEmail());
            signInDTO.setLogged(true);
	        if (utente.getCliente() != null) {
	            signInDTO.setIdCliente(utente.getCliente().getIdCliente());
	            signInDTO.setDataRegistrazione(utente.getCliente().getDataRegistrazione());
	        }

	        response.setRc(true);
	        response.setMsg("Login avvenuto correttamente");
	        response.setDati(signInDTO);
	    } else {
	        response.setRc(false);
	        response.setMsg("Password errata per l'utente: " + req.getUsername());
	    }

	    return response;
	}



	
	
}
