package com.betacom.dischi;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectClasses({
	ProdottoControllerTest.class,
	ClienteControllerTest.class,
	UtenteControllerTest.class,
	CarrelloControllerTest.class,
	OrdineControllerTest.class,
	RecensioneControllerTest.class,
	WishlistControllerTest.class,
	MailTest.class
	
})

@SpringBootTest
class FinalProjectApplicationTests {

	@Test
	void contextLoads() {
	}

}
