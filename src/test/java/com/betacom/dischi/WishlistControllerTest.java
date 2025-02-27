package com.betacom.dischi;

import com.betacom.dischi.controller.WishlistController;
import com.betacom.dischi.DTO.ProdottoDTO;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.models.Prodotto;
import com.betacom.dischi.models.Wishlist;
import com.betacom.dischi.repository.IClienteRepository;
import com.betacom.dischi.repository.IProdottoRepository;
import com.betacom.dischi.repository.IWishlistRepository;
import com.betacom.dischi.request.WishlistRequest;
import com.betacom.dischi.response.ResponseBase;
import com.betacom.dischi.response.ResponseObject;
import com.betacom.dischi.utilities.enums.Formato;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WishlistControllerTest {

    @Autowired
    private WishlistController wishlistController;

    @Autowired
    private IClienteRepository clienteRepository;
    
    @Autowired
    private IWishlistRepository wishlistRepository;


    @Autowired
    private IProdottoRepository prodottoRepository;

    private Cliente testCliente;
    private Prodotto testProdotto;

    @BeforeEach
    public void setUp() {
        // Pulisco db prima di inserie nuovi dati
        wishlistRepository.deleteAll();
        prodottoRepository.deleteAll();
        clienteRepository.deleteAll();
        
        wishlistRepository.flush();
        prodottoRepository.flush();
        clienteRepository.flush();

        // Crea il prodotto per test
        testProdotto = new Prodotto();
        testProdotto.setFormato(Formato.CD);
        testProdotto.setTitolo("Test Album");
        testProdotto.setArtista("Test Artist");
        testProdotto.setGenere("Pop");
        testProdotto.setDescrizione("Test description");
        testProdotto.setAnnoPubblicazione(2025);
        testProdotto.setPrezzo(19.99);
        testProdotto.setQuantita(10);
        testProdotto.setImmagineProdotto("image_url");
        prodottoRepository.save(testProdotto);
        prodottoRepository.flush();  

        // Crea il cliente per test
        testCliente = new Cliente();
        testCliente.setNome("Test Client");
        testCliente.setCognome("Test Surname");
        testCliente.setTelefono("1234567890");
        testCliente.setDataRegistrazione(LocalDate.now());
        clienteRepository.save(testCliente);
        clienteRepository.flush();  // Assicura il salvataggio

        Assertions.assertThat(testCliente.getIdCliente()).isNotNull();
    }

    public WishlistRequest createWishlistRequest() {
        WishlistRequest req = new WishlistRequest();
        req.setIdCliente(testCliente.getIdCliente());  
        return req;
    }

    @Test
    @Order(1)
    public void testCreateWishlist() {
        WishlistRequest req = createWishlistRequest();
        req.setIdProdotti(List.of(testProdotto.getIdProdotto())); 

        System.out.println("Request: " + req);

        ResponseBase response = wishlistController.create(req);

        System.out.println("Response: " + response);

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getMsg()).contains("Wishlist create con successo");

        Assertions.assertThat(response.getRc()).isEqualTo(true);
    }



    @Test
    @Order(2)
    public void testAddProductToWishlist() {
        WishlistRequest req = createWishlistRequest();
        wishlistController.create(req);

        req.setIdProdotti(List.of(testProdotto.getIdProdotto())); 
        ResponseBase response = wishlistController.addProduct(req);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        Assertions.assertThat(response.getMsg()).contains("Prodotto aggiunto alla wishlist con successo.");
    }

    @Test
    @Order(3)
    public void testRemoveProductFromWishlist() {
        // Creazione della wishlist
        WishlistRequest req = createWishlistRequest();
        wishlistController.create(req);
        req.setIdProdotti(List.of(testProdotto.getIdProdotto()));

        // Aggiunta del prodotto
        ResponseBase addResponse = wishlistController.addProduct(req);
        System.out.println("Add Response RC: " + addResponse.getRc());
        System.out.println("Add Response Message: " + addResponse.getMsg());

        // controllo aggiunta prod
        ResponseObject<List<ProdottoDTO>> getResponse = wishlistController.getAllProducts(testCliente.getIdCliente());
        System.out.println("Prodotti nella wishlist dopo l'aggiunta: " + getResponse.getDati());

        ResponseBase removeResponse = wishlistController.removeProduct(req);
        System.out.println("Remove Response RC: " + removeResponse.getRc());
        System.out.println("Remove Response Message: " + removeResponse.getMsg());

        Assertions.assertThat(removeResponse.getRc()).isEqualTo(true);
        Assertions.assertThat(removeResponse.getMsg()).contains("Prodotto rimosso dalla wishlist con successo.");

        ResponseObject<List<ProdottoDTO>> finalCheck = wishlistController.getAllProducts(testCliente.getIdCliente());
        System.out.println("Prodotti nella wishlist dopo la rimozione: " + finalCheck.getDati());
    }



    @Test
    @Order(4)
    public void testGetWishlistProducts() {
        WishlistRequest req = createWishlistRequest();
        wishlistController.create(req);
        req.setIdProdotti(List.of(testProdotto.getIdProdotto()));
        wishlistController.addProduct(req);

        ResponseObject<List<ProdottoDTO>> response = wishlistController.getAllProducts(testCliente.getIdCliente());

        List<ProdottoDTO> prodotti = response.getDati();
        Assertions.assertThat(prodotti).isNotEmpty();
        Assertions.assertThat(prodotti).extracting(ProdottoDTO::getIdProdotto)
                .contains(testProdotto.getIdProdotto());
    }

    @Test
    @Order(5)
    public void testClearWishlist() {
        WishlistRequest req = createWishlistRequest();
        wishlistController.create(req);
        req.setIdProdotti(List.of(testProdotto.getIdProdotto()));
        wishlistController.addProduct(req);

        ResponseBase response = wishlistController.clearWishlist(req);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        Assertions.assertThat(response.getMsg()).contains("Tutti i prodotti sono stati rimossi dalla wishlist.");
    }


    @Test
    @Order(6)   
    public void testSearchWishlistById() {
        WishlistRequest req = createWishlistRequest();
        wishlistController.create(req);

        ResponseObject<Wishlist> response = wishlistController.searchWishlistById(testCliente.getIdCliente());

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        Assertions.assertThat(response.getMsg()).contains("Wishlist trovata con successo.");
    }

    @Test
    @Order(7)
    public void testCreateWishlistWithInvalidCliente() {
        WishlistRequest req = new WishlistRequest();
        req.setIdCliente(99999); // ID non esistente

        ResponseBase response = wishlistController.create(req);

        Assertions.assertThat(response.getRc()).isEqualTo(false);
        Assertions.assertThat(response.getMsg()).containsIgnoringCase("cliente con ID: 99999 non trovato");

    }  

    @Test
    @Order(8)
    public void testRemoveProductNotInWishlist() {
        WishlistRequest req = createWishlistRequest();
        wishlistController.create(req);

        req.setIdProdotti(List.of(99999)); // prodotto inesistente
  
        ResponseBase response = wishlistController.removeProduct(req);

        Assertions.assertThat(response.getRc()).isEqualTo(false);
        Assertions.assertThat(response.getMsg()).contains("Prodotto con ID: 99999 non trovato");
    }

    @Test
    @Order(9)
    public void testClearEmptyWishlist() {
        WishlistRequest req = createWishlistRequest();
        wishlistController.create(req);

        ResponseBase response = wishlistController.clearWishlist(req);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        Assertions.assertThat(response.getMsg()).contains("Tutti i prodotti sono stati rimossi dalla wishlist.");
    }

}
