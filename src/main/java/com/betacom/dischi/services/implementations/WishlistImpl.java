package com.betacom.dischi.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betacom.dischi.DTO.ProdottoDTO;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.models.Prodotto;
import com.betacom.dischi.models.Wishlist;
import com.betacom.dischi.repository.IClienteRepository;
import com.betacom.dischi.repository.IProdottoRepository;
import com.betacom.dischi.repository.IWishlistRepository;
import com.betacom.dischi.request.WishlistRequest;
import com.betacom.dischi.services.interfaces.WishlistService;

import jakarta.transaction.Transactional;

@Service
public class WishlistImpl implements WishlistService {

    @Autowired
    private IWishlistRepository wishlistRepository;
    
    @Autowired
    private IClienteRepository clienteRepository;
    
    @Autowired
    private IProdottoRepository prodottoRepository;
    
    @Autowired
    Logger log;

    @Override
    public void create(WishlistRequest req) throws CustomException {
        log.debug("Creazione della Wishlist per il cliente con ID: " + req.getIdCliente());
        
        Optional<Cliente> optCliente = clienteRepository.findById(req.getIdCliente());
        Cliente cliente = optCliente.orElseThrow(() -> 
            new CustomException("Cliente con ID: " + req.getIdCliente() + " non trovato."));
        
        Wishlist wishlist = new Wishlist();
        wishlist.setCliente(cliente);
        wishlist.setProdotti(new ArrayList<>());
        
        wishlistRepository.save(wishlist);
        log.debug("Wishlist creata con successo per il cliente ID: " + req.getIdCliente());
    }

    @Override
    @Transactional
    public void addProductToWishlist(Integer idCliente, Integer idProdotto) throws CustomException {
        log.debug("Aggiunta del prodotto ID " + idProdotto + " alla Wishlist del cliente ID " + idCliente);
        
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new CustomException("Cliente con ID: " + idCliente + " non trovato."));
        
        Wishlist wishlist = wishlistRepository.findByCliente(cliente)
                .orElseThrow(() -> new CustomException("Wishlist non trovata per il cliente ID: " + idCliente));

        Prodotto prodotto = prodottoRepository.findById(idProdotto)
                .orElseThrow(() -> new CustomException("Prodotto con ID: " + idProdotto + " non trovato."));

        if (!wishlist.getProdotti().contains(prodotto)) {
            wishlist.getProdotti().add(prodotto);
            wishlistRepository.save(wishlist);
            log.debug("Prodotto ID " + idProdotto + " aggiunto alla Wishlist del cliente ID " + idCliente);
        } else {
            log.debug("Prodotto ID " + idProdotto + " già presente nella Wishlist del cliente ID " + idCliente);
        }
    }

    @Override
    public void removeProductFromWishlist(Integer idCliente, Integer idProdotto) throws CustomException {
        log.debug("Rimozione del prodotto ID " + idProdotto + " dalla Wishlist del cliente ID " + idCliente);

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new CustomException("Cliente con ID: " + idCliente + " non trovato."));

    
        Wishlist wishlist = wishlistRepository.findByCliente(cliente)
                .orElseThrow(() -> new CustomException("Wishlist non trovata per il cliente ID: " + idCliente));

        Prodotto prodotto = prodottoRepository.findById(idProdotto)
                .orElseThrow(() -> new CustomException("Prodotto con ID: " + idProdotto + " non trovato."));

       
        if (wishlist.getProdotti().contains(prodotto)) {
            wishlist.getProdotti().remove(prodotto);
            wishlistRepository.save(wishlist); 
            log.debug("Prodotto ID " + idProdotto + " rimosso dalla Wishlist del cliente ID " + idCliente);
        } else {
            log.debug("Il prodotto ID " + idProdotto + " non è presente nella Wishlist del cliente ID " + idCliente);
            throw new CustomException("Il prodotto non è presente nella wishlist del cliente.");
        }
    }
 
    @Override
    public void clearWishlist(Integer idCliente) throws CustomException {
        log.debug("Svuotamento della Wishlist per il cliente con ID: " + idCliente);
        
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new CustomException("Cliente con ID: " + idCliente + " non trovato."));
        
        Wishlist wishlist = wishlistRepository.findByCliente(cliente)
                .orElseThrow(() -> new CustomException("Wishlist non trovata per il cliente ID: " + idCliente));
        
        wishlist.getProdotti().clear();
        
        wishlistRepository.save(wishlist);
        log.debug("Wishlist svuotata con successo per il cliente ID: " + idCliente);
    }

    @Override
    public List<ProdottoDTO> getWishlistProducts(Integer idCliente) throws CustomException {
        log.debug("Recupero dei prodotti dalla Wishlist per il cliente con ID: " + idCliente);
        
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new CustomException("Cliente con ID: " + idCliente + " non trovato."));
        
        Wishlist wishlist = wishlistRepository.findByCliente(cliente)
                .orElseThrow(() -> new CustomException("Wishlist non trovata per il cliente ID: " + idCliente));
        
        return wishlist.getProdotti().stream()
                .map(prodotto -> new ProdottoDTO.Builder()
                        .idProdotto(prodotto.getIdProdotto())
                        .titolo(prodotto.getTitolo())
                        .artista(prodotto.getArtista())
                        .prezzo(prodotto.getPrezzo())
                        .formato(prodotto.getFormato().toString())
                        .genere(prodotto.getGenere())
                        .immagineProdotto(prodotto.getImmagineProdotto())
                        .build())
                .toList();
    }

    @Override
    public Optional<Wishlist> searchWishlistById(Integer idCliente) throws CustomException {
        log.debug("Ricerca della Wishlist per il cliente con ID: " + idCliente);
        // Ricerca della wishlist per il cliente
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new CustomException("Cliente con ID: " + idCliente + " non trovato."));
        
        // Restituisce la wishlist associata al cliente
        return wishlistRepository.findByCliente(cliente);
    }

}
