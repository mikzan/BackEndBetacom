package com.betacom.dischi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.dischi.DTO.CarrelloDTO;
import com.betacom.dischi.controller.CarrelloController;
import com.betacom.dischi.request.CarrelloRequest;
import com.betacom.dischi.response.ResponseBase;
import com.betacom.dischi.response.ResponseObject;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarrelloControllerTest {

	@Autowired
	CarrelloController controller;
	
	@Test
	@Order(1)
	public void addProdotto(){
		CarrelloRequest request = new CarrelloRequest();
		request.setIdCliente(2);
		request.setIdProdotto(2);
		request.setQuantita(1);
		ResponseBase response = controller.addProdotto(request);
		Assertions.assertThat(response.getRc()).isEqualTo(true);
	}
	
	@Test
	@Order(2)
	public void addProdottoErrore(){
		CarrelloRequest request = new CarrelloRequest();
		request.setIdCliente(1);
		request.setIdProdotto(1);
		request.setQuantita(1);
		ResponseBase response = controller.addProdotto(request);
		Assertions.assertThat(response.getRc()).isEqualTo(false);
		Assertions.assertThat(response.getMsg()).isEqualTo("no_customer");
		request.setIdCliente(2);
		request.setIdProdotto(2);
		request.setQuantita(500);
		response = controller.addProdotto(request);
		Assertions.assertThat(response.getRc()).isEqualTo(false);
		Assertions.assertThat(response.getMsg()).isEqualTo("no_qnt");
	}
	
	@Test
	@Order(3)
	public void listaProdotto() {
		ResponseObject<CarrelloDTO> response = controller.listaProdotti(2);
		Assertions.assertThat(response.getRc()).isEqualTo(true);
		response = controller.listaProdotti(1);
		Assertions.assertThat(response.getRc()).isEqualTo(false);
	}
	
	@Test
	@Order(4)
	public void removeProdotto(){
		CarrelloRequest request = new CarrelloRequest();
		request.setIdCliente(2);
		request.setIdProdotto(2);
		request.setQuantita(1);
		ResponseBase response = controller.removeProdotto(request);
		Assertions.assertThat(response.getRc()).isEqualTo(true);
	}
	
	@Test
	@Order(5)
	public void svuotaCarrello(){
		CarrelloRequest request = new CarrelloRequest();
		request.setIdCliente(2);
		ResponseBase response = controller.deleteCarrello(request);
		Assertions.assertThat(response.getRc()).isEqualTo(true);
	}
	@Test
	@Order(6)
	public void addProdottoPerSuccessivo(){
		CarrelloRequest request = new CarrelloRequest();
		request.setIdCliente(2);
		request.setIdProdotto(2);
		request.setQuantita(1);
		ResponseBase response = controller.addProdotto(request);
		Assertions.assertThat(response.getRc()).isEqualTo(true);
	}
 }
