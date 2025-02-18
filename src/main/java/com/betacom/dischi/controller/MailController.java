package com.betacom.dischi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.request.MailRequest;
import com.betacom.dischi.response.ResponseBase;
import com.betacom.dischi.utilities.mail.MailService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/rest/mail")
public class MailController {
	
	@Autowired
	MailService mailServ;

	@PostMapping("/conferma")
	public ResponseBase getConferma(@RequestBody(required = true) MailRequest request){
		ResponseBase response = new ResponseBase();
		response.setRc(true);
		try {
			mailServ.mailConfermaOrdine(request);
			response.setMsg("Mail inviata correttamente");
		} catch (CustomException e) {
			response.setRc(false);
			response.setMsg(e.getMessage());
		}
		return response;
	}
}
