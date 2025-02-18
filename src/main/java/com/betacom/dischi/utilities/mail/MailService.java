package com.betacom.dischi.utilities.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.request.MailRequest;

@Service
public class MailService {

	@Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    
     public void mailConfermaOrdine(MailRequest request) throws CustomException {
    	 String text = "Il tuo ordine presso BetaDisk è stato confermato";
    	 
    	 SimpleMailMessage message = new SimpleMailMessage();
         message.setTo(request.getTo());
         message.setSubject("Ordine BetaDisk confermato");
         message.setText(text);
         //message.setFrom("betadisk@betacom.com");
         mailSender.send(message);
     }
}
