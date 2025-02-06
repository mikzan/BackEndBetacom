package com.betacom.dischi;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.hibernate.grammars.hql.HqlParser.IsTruePredicateContext;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.dischi.DTO.ProdottoDTO;
import com.betacom.dischi.controller.ProdottoController;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.request.ProdottoRequest;
import com.betacom.dischi.response.ResponseBase;
import com.betacom.dischi.response.ResponseList;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdottoControllerTest {

	@Autowired
	ProdottoController prodottoController;
		
	@Autowired
	Logger log;
	
	
	
	public ProdottoRequest createProdottoGeneralRequest() throws CustomException{
		
		ProdottoRequest req = new ProdottoRequest();
		
		req.setIdProdotto(1);
		req.setFormato("VINILE");
		req.setTitolo("Among the Living");
		req.setArtista("Anthrax");
		req.setGenere("Thrash Metal");
		req.setDescrizione("Album iconico del thrash metal, pubblicato nel 1987.");
		req.setAnnoPubblicazione(1987);
		req.setPrezzo(24.99);
		req.setQuantita(8);
		req.setImmagineProdotto("https://example.com/among-the-living.jpg");
		
		return req;
	}
	

	
	public ProdottoRequest createProdottoGeneralDTO() throws CustomException{
		
		ProdottoRequest dto = new ProdottoRequest();
		
		dto.setIdProdotto(1);
		dto.setFormato("VINILE");
		dto.setTitolo("Among the Living");
		dto.setArtista("Anthrax");
		dto.setGenere("Thrash Metal");
		dto.setDescrizione("Album iconico del thrash metal, pubblicato nel 1987.");
		dto.setAnnoPubblicazione(1987);
		dto.setPrezzo(24.99);
		dto.setQuantita(8);
		dto.setImmagineProdotto("https://example.com/among-the-living.jpg");
		
		return dto;
	}
	
	@Test
	@Order(1)
	public void createProdottoTest()throws Exception {
		ProdottoRequest req = createProdottoGeneralRequest();
		
		ResponseBase response = prodottoController.create(req);
		Assertions.assertThat(response.getRc()).isEqualTo(true);	
	}
	
	
	@Test
	@Order(2)
	public void listAllProdotti() throws CustomException{
		
		ProdottoRequest dto = createProdottoGeneralDTO();
		ResponseList<ProdottoDTO> lista = prodottoController.list();
			
		
		Assertions.assertThat(lista).isNotNull();
		Assertions.assertThat(lista
								.getDati()
								.stream()
								.anyMatch(t -> t.getTitolo()
										.equals("Among the Living"))).isTrue();
	
	} 
	
	
	@Test
	@Order(3)
	public void updateProdotto() throws CustomException {
		
		ProdottoRequest req = createProdottoGeneralRequest();
		
		req.setTitolo("Ops ho cambiato il titolo");
		
		ResponseBase response = prodottoController.update(req);
		
		Assertions.assertThat(response.getRc()).isEqualTo(true);
		Assertions.assertThat(req.getTitolo()).isEqualTo("Ops ho cambiato il titolo");
									
	}
	
	
	
	@Test
	@Order(4)
	public void deleteProdotto() throws CustomException{
	
		ProdottoRequest req = createProdottoGeneralRequest();
		ResponseBase response = prodottoController.delete(req);
		
		Assertions.assertThat(response.getRc()).isEqualTo(true);
		
	}
	
}
