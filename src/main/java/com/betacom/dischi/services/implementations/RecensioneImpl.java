package com.betacom.dischi.services.implementations;

import static com.betacom.dischi.utilities.Utility.buildRecensioneDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.betacom.dischi.DTO.OrdineDTO;
import com.betacom.dischi.DTO.ProdottoOrdineDTO;
import com.betacom.dischi.DTO.RecensioneDTO;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.models.Prodotto;
import com.betacom.dischi.models.ProdottoOrdine;
import com.betacom.dischi.models.Recensione;
import com.betacom.dischi.repository.IClienteRepository;
import com.betacom.dischi.repository.IProdottoRepository;
import com.betacom.dischi.repository.IRecensioneRepository;
import com.betacom.dischi.request.RecensioneRequest;
import com.betacom.dischi.services.interfaces.OrdineService;
import com.betacom.dischi.services.interfaces.RecensioneService;
import com.betacom.dischi.services.interfaces.SystemMsgServices;
import com.betacom.dischi.utilities.Utility;

import jakarta.transaction.Transactional;

@Service
public class RecensioneImpl implements RecensioneService {
	
	private Logger log;
	private IRecensioneRepository recensioneRepo;
	private IClienteRepository clienteRepo;
	private IProdottoRepository prodottoRepo;
	private OrdineService ordineServ;
    private SystemMsgServices msgServ;

	
	public RecensioneImpl(Logger log, IRecensioneRepository recensioneRepo, IClienteRepository clienteRepo,
			IProdottoRepository prodottoRepo, OrdineService ordineServ,SystemMsgServices msgServ) {
		this.log = log;
		this.recensioneRepo = recensioneRepo;
		this.clienteRepo = clienteRepo;
		this.prodottoRepo = prodottoRepo;
		this.ordineServ = ordineServ;
		this.msgServ = msgServ;
	}


	@Override
	@Transactional
	public List<RecensioneDTO> listAll(Integer idRecensione,Integer stelle) {
	    List<Recensione> listaRecensioni = recensioneRepo.filteredReviews(idRecensione, stelle); 
	    return listaRecensioni.stream()
	            .map(rece -> buildRecensioneDTO(rece))
	            .toList();
	}
	
	
	
	@Override
	@Transactional
	public void create(RecensioneRequest req) throws CustomException {
		log.debug("Create recensione: "+req);
		Recensione recensione = new Recensione();
		checkAndSetFields(recensione,req);
		recensioneRepo.save(recensione);
        log.debug(msgServ.getSysMsg("review_created") + " ID: " + recensione.getIdRecensione());

	}

	@Override
	@Transactional
	public void update(RecensioneRequest req) throws CustomException {
		Recensione recensione = recensioneRepo.findById(req.getIdRecensione())
                .orElseThrow(() -> new CustomException(msgServ.getSysMsg("no_review")));
		boolean modifica = false;
		if (req.getDescrizione() != null) {
			recensione.setDescrizione(req.getDescrizione());
			modifica = true;
		}
		if (req.getStelle() != null) {
			recensione.setStelle(req.getStelle());
			modifica = true;
		}
		if (modifica) {
			recensione.setDataCreazione(LocalDate.now());
		}
		recensioneRepo.save(recensione);
        log.debug(msgServ.getSysMsg("review_updated"));

	}
	@Override
	@Transactional
	public void delete(RecensioneRequest req) throws CustomException {
		log.debug("Delete recensione: "+req);
		  Recensione recensione = recensioneRepo.findById(req.getIdRecensione())
	                .orElseThrow(() -> new CustomException(msgServ.getSysMsg("no_review")));
		recensioneRepo.delete(recensione);
        log.debug(msgServ.getSysMsg("review_deleted") + " ID: " + req.getIdRecensione());
	}

	private Recensione checkAndSetFields(Recensione recensione,RecensioneRequest req) throws  CustomException {
		Cliente cliente = clienteRepo.findById(req.getIdCliente())
                .orElseThrow(() -> new CustomException(msgServ.getSysMsg("no_customer")));
		Prodotto prodotto = prodottoRepo.findById(req.getIdProdotto())
                .orElseThrow(() -> new CustomException(msgServ.getSysMsg("no_product")));
		checkIfRecensioneAlreadyExists(cliente,prodotto);
		checkIfClienteAlreadyBoughtProduct(cliente,prodotto.getProdottiOrdine());
	    checkIfRecensioneRequestHasValidParameters(req);
		recensione.setStelle(req.getStelle());
		recensione.setDescrizione(req.getDescrizione());
		recensione.setCliente(cliente);
	    recensione.setProdotto(prodotto);  
		recensione.setDataCreazione(LocalDate.now());

		return recensione;
	}
	
	private void checkIfRecensioneAlreadyExists(Cliente cliente, Prodotto prodotto) throws CustomException {
		Boolean recensioneEsistente = recensioneRepo.existsByClienteAndProdotto(cliente, prodotto);
		if(recensioneEsistente) 
            throw new CustomException(msgServ.getSysMsg("review_already_exists"));
	}
	
	private void checkIfClienteAlreadyBoughtProduct(Cliente cliente, List<ProdottoOrdine> prodottiOrdine) throws CustomException {
		boolean haAcquistato = false;
	    for (ProdottoOrdine prodottoOrdine : prodottiOrdine) {
	        Integer prodottoId = prodottoOrdine.getProdotto().getIdProdotto();  
	        Integer clienteIdOrdine = prodottoOrdine.getOrdine().getCliente().getIdCliente();
	        if (clienteIdOrdine.equals(cliente.getIdCliente()) && prodottoId.equals(prodottoOrdine.getProdotto().getIdProdotto())) {
	                haAcquistato = true;
	                break;  
	        } 
	    }
	    if (!haAcquistato) 
            throw new CustomException(msgServ.getSysMsg("product_not_bought"));
	}
	
	public void checkIfRecensioneRequestHasValidParameters(RecensioneRequest req) throws CustomException {
		if(req.getDescrizione() == null || req.getDescrizione().isBlank()) {
            throw new CustomException(msgServ.getSysMsg("description_invalid"));
		}
		if(req.getStelle() == null) {
            throw new CustomException(msgServ.getSysMsg("stars_invalid"));
		}
		if(req.getStelle() < 1 || req.getStelle() > 5) {
            throw new CustomException(msgServ.getSysMsg("stars_range_invalid"));
		}
	}
	
	@Override
	@Transactional
	public RecensioneDTO listById(Integer id) throws CustomException {
	    Recensione recensione = recensioneRepo.findById(id)
                .orElseThrow(() -> new CustomException(msgServ.getSysMsg("no_review")));
	    RecensioneDTO recensioneDTO = Utility.buildRecensioneDTO(recensione);  
	    return recensioneDTO;
	}

	@Override
	@Transactional
	public List<RecensioneDTO> listaProdottiDaRecensire(Integer idCliente) throws CustomException {
		List<OrdineDTO> listaOrdini = ordineServ.listaByCliente(idCliente);
		List<RecensioneDTO> listaRecensioni = new ArrayList<RecensioneDTO>();
		
		listaOrdini.forEach(ordine -> {
			ordine.getProdotti().forEach(p -> {
				if (p.getProdotto().getRecensioni().isEmpty()) {
					RecensioneDTO rec = new RecensioneDTO.Builder()
							.cliente(ordine.getCliente())
							.prodotto(p.getProdotto())
							.build();
					listaRecensioni.add(recensioneDaScrivere(ordine, p));
				} else {
					p.getProdotto().getRecensioni().forEach(r -> {
						if (r.getCliente().getIdCliente() == idCliente) {
							boolean trovato = false;
							if (listaRecensioni.size() != 0 ) {
								for (RecensioneDTO item : listaRecensioni) {
									if (item.getProdotto().getTitolo().equalsIgnoreCase(p.getProdotto().getTitolo()) && item.getProdotto().getArtista().equalsIgnoreCase(p.getProdotto().getArtista())) {
										trovato = true;
										break;
									}
								};
							}
							if (!trovato) {
								listaRecensioni.add(recensioneScritta(r, p));
							}
						} else {
							listaRecensioni.add(recensioneDaScrivere(ordine, p));
						}
					});
				}
			});
		});
		return listaRecensioni;
	}

	private RecensioneDTO recensioneDaScrivere(OrdineDTO ordine, ProdottoOrdineDTO p) {
		 return new RecensioneDTO.Builder()
		.cliente(ordine.getCliente())
		.prodotto(p.getProdotto())
		.build();
	}
	private RecensioneDTO recensioneScritta(RecensioneDTO r,  ProdottoOrdineDTO p) {
		return new RecensioneDTO.Builder()
		.cliente(r.getCliente())
		.prodotto(p.getProdotto())
		.idRecensione(r.getIdRecensione())
		.dataCreazione(r.getDataCreazione())
		.stelle(r.getStelle())
		.descrizione(r.getDescrizione())
		.build();
	}
	
}
