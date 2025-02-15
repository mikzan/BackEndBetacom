package com.betacom.dischi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.betacom.dischi.models.Prodotto;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.models.Wishlist;
import com.betacom.dischi.repository.IClienteRepository;
import com.betacom.dischi.repository.IProdottoRepository;
import com.betacom.dischi.repository.IWishlistRepository;
import com.betacom.dischi.request.WishlistRequest;
import com.betacom.dischi.services.interfaces.WishlistService;
import com.betacom.dischi.utilities.enums.Formato;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Transactional
public class WishlistControllerTest {

	@Autowired
	private EntityManager entityManager;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IProdottoRepository prodottoRepository;

    @Autowired
    private IWishlistRepository wishlistRepository;

    private Cliente testCliente;
    private Prodotto testProdotto;


    @BeforeEach
    public void setUp() {
        
        testCliente = new Cliente();
        testCliente.setNome("Mario");
        testCliente.setCognome("Rossi");
        testCliente.setTelefono("123456789");
        testCliente.setDataRegistrazione(LocalDate.now()); 
        clienteRepository.save(testCliente);

        // Crea un prodotto di test
        testProdotto = new Prodotto();
        testProdotto.setTitolo("Album Test");
        testProdotto.setArtista("Artista Test");
        testProdotto.setGenere("Rock");
        testProdotto.setDescrizione("Descrizione di test.");
        testProdotto.setAnnoPubblicazione(2025);
        testProdotto.setPrezzo(29.99);
        testProdotto.setFormato(Formato.CD);

        prodottoRepository.save(testProdotto); 
    }

    @Test
    @Order(1)
    public void testCreateWishlist() throws Exception {
     
        WishlistRequest req = new WishlistRequest();
        req.setIdCliente(testCliente.getIdCliente());

    
        wishlistService.create(req);

        
        Optional<Wishlist> wishlist = wishlistRepository.findByCliente(testCliente);
        Assertions.assertThat(wishlist).isPresent();
        Assertions.assertThat(wishlist.get().getProdotti()).isEmpty();
    }

    @Test
    @Order(2)
    @Rollback(false)
    public void testAddProductToWishlist() throws Exception {

        WishlistRequest req = new WishlistRequest();
        req.setIdCliente(testCliente.getIdCliente());
        

        wishlistService.create(req);

        
        wishlistService.addProductToWishlist(testCliente.getIdCliente(), testProdotto.getIdProdotto());
        
        
        entityManager.flush();  
        
        
        Optional<Wishlist> wishlist = wishlistRepository.findByCliente(testCliente);
        Assertions.assertThat(wishlist).isPresent();
        
     
        Assertions.assertThat(wishlist.get().getProdotti())
            .extracting(Prodotto::getIdProdotto)
            .contains(testProdotto.getIdProdotto());
    }


    @Test
    @Order(3)
    public void testRemoveProductFromWishlist() throws Exception {
  
        Optional<Wishlist> wishlistOptional = wishlistRepository.findByCliente(testCliente);
        if (!wishlistOptional.isPresent()) {
       
            WishlistRequest req = new WishlistRequest();
            req.setIdCliente(testCliente.getIdCliente());
            wishlistService.create(req);
        }


        wishlistService.addProductToWishlist(testCliente.getIdCliente(), testProdotto.getIdProdotto());

        wishlistOptional = wishlistRepository.findByCliente(testCliente);
        Assertions.assertThat(wishlistOptional).isPresent();
        Wishlist wishlist = wishlistOptional.get();
        Assertions.assertThat(wishlist.getProdotti()).contains(testProdotto);

        wishlistService.removeProductFromWishlist(testCliente.getIdCliente(), testProdotto.getIdProdotto());

        Optional<Wishlist> updatedWishlist = wishlistRepository.findByCliente(testCliente);
        Assertions.assertThat(updatedWishlist).isPresent();
        Assertions.assertThat(updatedWishlist.get().getProdotti()).doesNotContain(testProdotto);
    }



    @Test
    @Order(4)
    public void testClearWishlist() throws Exception {
  
        Optional<Wishlist> wishlistOptional = wishlistRepository.findByCliente(testCliente);
        if (!wishlistOptional.isPresent()) {
            WishlistRequest req = new WishlistRequest();
            req.setIdCliente(testCliente.getIdCliente());
            wishlistService.create(req);  
        }

        wishlistService.addProductToWishlist(testCliente.getIdCliente(), testProdotto.getIdProdotto());

 
        wishlistService.clearWishlist(testCliente.getIdCliente());


        Optional<Wishlist> wishlist = wishlistRepository.findByCliente(testCliente);
        Assertions.assertThat(wishlist).isPresent();
        Assertions.assertThat(wishlist.get().getProdotti()).isEmpty(); 
    }


    @Test
    @Order(5)
    public void testGetWishlistProducts() throws Exception {

        Optional<Wishlist> wishlistOptional = wishlistRepository.findByCliente(testCliente);
        if (!wishlistOptional.isPresent()) {
            WishlistRequest req = new WishlistRequest();
            req.setIdCliente(testCliente.getIdCliente());
            wishlistService.create(req);  
        }

 
        wishlistService.addProductToWishlist(testCliente.getIdCliente(), testProdotto.getIdProdotto());

        List<Prodotto> prodotti = wishlistService.getWishlistProducts(testCliente.getIdCliente());


        Assertions.assertThat(prodotti).contains(testProdotto);
    }


    @Test
    @Order(6)
    public void testSearchWishlistById() throws Exception {
        // Crea la Wishlist se non esiste già
        WishlistRequest req = new WishlistRequest();
        req.setIdCliente(testCliente.getIdCliente());

        Optional<Wishlist> existingWishlist = wishlistRepository.findByCliente(testCliente);
        if (!existingWishlist.isPresent()) {
            System.out.println("Wishlist non trovata, la sto creando per il cliente con ID: " + testCliente.getIdCliente());
            wishlistService.create(req);  
            entityManager.flush();  
        } else {
            System.out.println("Wishlist già esistente per il cliente con ID: " + testCliente.getIdCliente());
        }

        
        Optional<Wishlist> createdWishlist = wishlistRepository.findByCliente(testCliente);
        Assertions.assertThat(createdWishlist).isPresent();
        System.out.println("Wishlist creata con ID: " + createdWishlist.get().getIdWishlist());

       
        Optional<Wishlist> wishlist = wishlistService.searchWishlistById(testCliente.getIdCliente());
        System.out.println("Wishlist trovata per il cliente con ID: " + testCliente.getIdCliente());

        Assertions.assertThat(wishlist).isPresent();
    }

    
    @Test
    @Order(7)
    public void testCreateWishlistForNonExistentCliente() throws Exception {
        WishlistRequest req = new WishlistRequest();
        req.setIdCliente(99999);  // ID cliente inesistente

        
        Assertions.assertThatThrownBy(() -> wishlistService.create(req))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("Cliente con ID: 99999 non trovato.");
    }

    @Test
    @Order(8)
    public void testRemoveNonExistingProductFromWishlist() throws Exception {
        Optional<Wishlist> wishlistOptional = wishlistRepository.findByCliente(testCliente);
        if (!wishlistOptional.isPresent()) {
            WishlistRequest req = new WishlistRequest();
            req.setIdCliente(testCliente.getIdCliente());
            wishlistService.create(req);
        }

        wishlistService.addProductToWishlist(testCliente.getIdCliente(), testProdotto.getIdProdotto());

        Optional<Wishlist> wishlist = wishlistRepository.findByCliente(testCliente);
        Assertions.assertThat(wishlist).isPresent();
        Wishlist currentWishlist = wishlist.get();

        // Rimuoviamo un prodotto che non è nella wishlist
        Prodotto prodottoNonPresente = new Prodotto();
        prodottoNonPresente.setIdProdotto(99999);  
        Assertions.assertThatThrownBy(() -> wishlistService.removeProductFromWishlist(testCliente.getIdCliente(), prodottoNonPresente.getIdProdotto()))
        .isInstanceOf(CustomException.class)
        .hasMessageContaining("Prodotto con ID: 99999 non trovato.");

    }

    @Test
    @Order(9)
    public void testSearchNonExistentWishlist() throws Exception {
       
        Optional<Wishlist> wishlist = wishlistService.searchWishlistById(testCliente.getIdCliente());
        Assertions.assertThat(wishlist).isEmpty();  
    }

    @Test
    @Order(10)
    public void testAddAndRemoveMultipleProducts() throws Exception {
        Prodotto prodotto2 = new Prodotto();
        prodotto2.setTitolo("Album Test 2");
        prodotto2.setArtista("Artista Test 2");
        prodotto2.setGenere("Pop");
        prodotto2.setDescrizione("Descrizione di test 2.");
        prodotto2.setAnnoPubblicazione(2026);
        prodotto2.setPrezzo(19.99);
        prodotto2.setFormato(Formato.VINILE);
        prodottoRepository.save(prodotto2);

        // Crea la wishlist se non esiste
        Optional<Wishlist> wishlistOptional = wishlistRepository.findByCliente(testCliente);
        if (!wishlistOptional.isPresent()) {
            WishlistRequest req = new WishlistRequest();
            req.setIdCliente(testCliente.getIdCliente());
            wishlistService.create(req);
        }

        
        wishlistService.addProductToWishlist(testCliente.getIdCliente(), testProdotto.getIdProdotto());
        wishlistService.addProductToWishlist(testCliente.getIdCliente(), prodotto2.getIdProdotto());

        List<Prodotto> prodotti = wishlistService.getWishlistProducts(testCliente.getIdCliente());
        Assertions.assertThat(prodotti).contains(testProdotto, prodotto2);

      
        wishlistService.removeProductFromWishlist(testCliente.getIdCliente(), prodotto2.getIdProdotto());

    
        List<Prodotto> updatedProdotti = wishlistService.getWishlistProducts(testCliente.getIdCliente());
        Assertions.assertThat(updatedProdotti).doesNotContain(prodotto2);
    }

}
