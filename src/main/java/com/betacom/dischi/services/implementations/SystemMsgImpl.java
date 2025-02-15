package com.betacom.dischi.services.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betacom.dischi.models.KeyID;
import com.betacom.dischi.models.SystemMsg;
import com.betacom.dischi.repository.ISystemMsgRepository;
import com.betacom.dischi.services.interfaces.SystemMsgServices;

@Service
public class SystemMsgImpl implements SystemMsgServices{
	
	@Value("${lang_BE}")
	private String language;
	
	@Autowired
	ISystemMsgRepository msgRepo;

	@Override
	public String getSysMsg(String code) {
		Optional<SystemMsg> msg = msgRepo.findById(new KeyID(language, code));
		String response = null;
		if (msg.isEmpty()) {
			response = code;
		} else {
			response = msg.get().getMsg();
		}
		return response;
	}
	
	

}
