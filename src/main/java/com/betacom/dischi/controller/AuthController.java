package com.betacom.dischi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.dischi.DTO.SignInDTO;
import com.betacom.dischi.request.SignInRequest;
import com.betacom.dischi.response.ResponseObject;
import com.betacom.dischi.services.interfaces.AuthService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/rest/auth")
public class AuthController {
	
	@Autowired
	AuthService authServ;

	@PostMapping("/signin")
	public ResponseObject<SignInDTO> signIn (@RequestBody SignInRequest req){
		ResponseObject<SignInDTO> response = new ResponseObject<>();
		response.setRc(true);
		
		try {
			response = authServ.signIn(req);
		}catch(Exception e) {
			response.setRc(false);
			response.setMsg(e.getMessage());
		}
		return response;
		
	}
	


	
}
