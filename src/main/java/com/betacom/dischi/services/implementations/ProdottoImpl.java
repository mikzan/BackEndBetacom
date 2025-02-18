package com.betacom.dischi.services.implementations;

import static com.betacom.dischi.utilities.Utility.buildProdottoDTO;
import static com.betacom.dischi.utilities.Utility.validazioneValoriProdotto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betacom.dischi.DTO.ProdottoDTO;
import com.betacom.dischi.DTO.RecensioneDTO;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Prodotto;
import com.betacom.dischi.models.Recensione;
import com.betacom.dischi.repository.IProdottoRepository;
import com.betacom.dischi.repository.IRecensioneRepository;
import com.betacom.dischi.request.ProdottoRequest;
import com.betacom.dischi.services.interfaces.ProdottoService;
import com.betacom.dischi.utilities.enums.Formato;

import jakarta.transaction.Transactional;

@Service
public class ProdottoImpl implements ProdottoService{
	
	@Autowired
	Logger log;
	
	@Autowired
	IProdottoRepository prodottoRepository;
	
	@Autowired
	IRecensioneRepository recensioneRepository;
	

	@Override
	public void create(ProdottoRequest req) throws Exception {
	
		Prodotto prodotto = new Prodotto();
	
		Optional<Prodotto> prodottoCercato = prodottoRepository.findByTitoloAndArtista(req.getTitolo(), req.getArtista());
		if(prodottoCercato.isPresent()) {
			throw new Exception("Prodotto già esistente");
		}
		
		validazioneValoriProdotto(req);
		
		prodotto.setFormato(Formato.valueOf(req.getFormato()));
		prodotto.setTitolo(req.getTitolo());
		prodotto.setArtista(req.getArtista());
		prodotto.setGenere(req.getGenere());
		prodotto.setDescrizione(req.getDescrizione());
		prodotto.setAnnoPubblicazione(req.getAnnoPubblicazione());
		prodotto.setPrezzo(req.getPrezzo());
		prodotto.setQuantita(req.getQuantita());
		prodotto.setImmagineProdotto(req.getImmagineProdotto());
		
		prodottoRepository.save(prodotto);
	}
	


	@Override
	public void update(ProdottoRequest req) throws CustomException {
		
		Optional<Prodotto> prodotto = prodottoRepository.findById(req.getIdProdotto());
		if(prodotto.isEmpty())
			throw new CustomException("Prodotto non trovato");
		
		Prodotto p = prodotto.get();
		
		validazioneValoriProdotto(req);
		
		p.setFormato(Formato.valueOf(req.getFormato()));
		p.setTitolo(req.getTitolo());
		p.setArtista(req.getArtista());
		p.setGenere(req.getGenere());
		p.setDescrizione(req.getDescrizione());
		p.setAnnoPubblicazione(req.getAnnoPubblicazione());
		p.setPrezzo(req.getPrezzo());
		p.setQuantita(req.getQuantita());
		p.setImmagineProdotto(req.getImmagineProdotto());
		
		prodottoRepository.save(p);
		
	}
	

	@Transactional(rollbackOn = CustomException.class)
	@Override
	public void delete(ProdottoRequest req) throws CustomException {
		Optional<Prodotto> prodotto = prodottoRepository.findById(req.getIdProdotto());
		if(prodotto.isEmpty())
			throw new CustomException("Prodotto non trovato");
		
		Prodotto p = prodotto.get();
		prodottoRepository.delete(p);
		
	}


	@Override
	public List<ProdottoDTO> listAll(Integer idProdotto, String titolo, String artista, String genere,
		Integer annoPubblicazione) throws Exception {
		
		List<Prodotto> listaFiltrata = prodottoRepository.prodottiFiltrati(idProdotto,titolo, artista, genere, annoPubblicazione);
	
		List<ProdottoDTO> risultato = listaFiltrata.stream()
				.map(p -> buildProdottoDTO(p))
	            .collect(Collectors.toList());  
	    
	    return risultato;
	}



	@Override
	public List<ProdottoDTO> listPerFormato(Formato formato) throws Exception {
		
		List<Prodotto> listaFiltrata = prodottoRepository.prodottiPerFormato(formato);
		
		
		List<ProdottoDTO> risultato = listaFiltrata.stream()
				.map(p -> buildProdottoDTO(p)) 
	            .collect(Collectors.toList());  
	    
	    return risultato;
	}



	@Override
	public List<ProdottoDTO> topTenProdotti() throws Exception {
	    List<Prodotto> listaProdotti = prodottoRepository.findAll();
	    
	    List<Prodotto> risultato = listaProdotti.stream()
	            .filter(prodotto -> prodotto.getRecensioni().size() != 0).collect(Collectors.toList());
	   
	    
	    risultato.forEach(p-> p.getRecensioni().sort(new Comparator<Recensione>() {
	    	@Override
	    	public int compare(Recensione p1, Recensione p2) {
	    		return p1.getStelle() - p2.getStelle();		
	    				}
		}));
	    		risultato.sort(new Comparator<Prodotto>() {
	    	@Override
	    	public int compare(Prodotto p1, Prodotto p2) {
	    		return p1.getRecensioni().get(0).getStelle() - p2.getRecensioni().get(0).getStelle();		
	    				}
		});
	    		


	    return risultato.stream().map(r-> buildProdottoDTO(r)).limit(10).toList();
	}

	
	
	
	
}
	