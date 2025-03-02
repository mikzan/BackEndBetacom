package com.betacom.dischi.services.interfaces;

import com.betacom.dischi.DTO.SignInDTO;
import com.betacom.dischi.request.SignInRequest;
import com.betacom.dischi.response.ResponseObject;

public interface AuthService {

	ResponseObject<SignInDTO> signIn(SignInRequest req) throws Exception;

}
