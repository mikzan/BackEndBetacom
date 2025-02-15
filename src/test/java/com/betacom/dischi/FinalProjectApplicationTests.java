package com.betacom.dischi;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectClasses({
	ProdottoControllerTest.class,
	ClienteControllerTest.class,
	CarrelloControllerTest.class,
	OrdineControllerTest.class,
	RecensioneControllerTest.class,
	UtenteControllerTest.class,
	WishlistControllerTest.class
	
})

@SpringBootTest
class FinalProjectApplicationTests {

	@Test
	void contextLoads() {
	}

}
