package com.betacom.dischi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.dischi.DTO.OrdineDTO;
import com.betacom.dischi.controller.OrdineController;
import com.betacom.dischi.request.OrdineRequest;
import com.betacom.dischi.response.ResponseBase;
import com.betacom.dischi.response.ResponseList;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdineControllerTest {

	@Autowired
	OrdineController controller;
	
	@Test
	@Order(1)
	public void creaOrdine(){
		OrdineRequest request = new OrdineRequest();
		request.setIdCliente(2);
		ResponseBase response = controller.create(request);
		Assertions.assertThat(response.getRc()).isEqualTo(true);
	}
	
	@Test
	@Order(2)
	public void updateOrdine(){
		OrdineRequest request = new OrdineRequest();
		request.setIdOrdine(2);
		request.setSpedito(true);
		ResponseBase response = controller.update(request);
		Assertions.assertThat(response.getRc()).isEqualTo(true);
	}
	
	@Test
	@Order(4)
	public void listaOrdini() {
		ResponseList<OrdineDTO> response = controller.listaOrdini(2);
		System.out.println(response.getMsg()+ "!!");
		Assertions.assertThat(response.getRc()).isEqualTo(true);
		response = controller.listaOrdini(1);
		Assertions.assertThat(response.getRc()).isEqualTo(false);
	}
	
	@Test
	@Order(5)
	public void deleteOrdine(){
		OrdineRequest request = new OrdineRequest();
		request.setIdOrdine(1);
		ResponseBase response = controller.delete(request);
		Assertions.assertThat(response.getRc()).isEqualTo(true);
	}
	
}
