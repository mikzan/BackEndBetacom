package com.betacom.dischi.services.implementations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.dischi.DTO.OrdineDTO;
import com.betacom.dischi.DTO.ProdottoDTO;
import com.betacom.dischi.DTO.ProdottoOrdineDTO;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Carrello;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.models.Ordine;
import com.betacom.dischi.models.Prodotto;
import com.betacom.dischi.models.ProdottoCarrello;
import com.betacom.dischi.models.ProdottoOrdine;
import com.betacom.dischi.repository.ICarrelloRepository;
import com.betacom.dischi.repository.IClienteRepository;
import com.betacom.dischi.repository.IOrdineRepository;
import com.betacom.dischi.repository.IProdottoCarrelloRepository;
import com.betacom.dischi.repository.IProdottoOrdineRepository;
import com.betacom.dischi.repository.IProdottoRepository;
import com.betacom.dischi.request.OrdineRequest;
import com.betacom.dischi.services.interfaces.OrdineService;

import jakarta.transaction.Transactional;

@Service
public class OrdineImpl implements OrdineService{
	
	private IClienteRepository clienteRepo;
	private ICarrelloRepository carrelloRepo;
	private IProdottoRepository prodottoRepo;
	private IProdottoCarrelloRepository joinCarrelloRepo;
	private IOrdineRepository ordineRepo;
	private IProdottoOrdineRepository joinOrdineRepo;
	
	public OrdineImpl(IClienteRepository clienteRepo,
			ICarrelloRepository carrelloRepo,
			IProdottoRepository prodottoRepo,
			IProdottoCarrelloRepository joinCarrelloRepo,
			IOrdineRepository ordineRepo,
			IProdottoOrdineRepository joinOrdineRepo
			) {
		this.clienteRepo = clienteRepo;
		this.carrelloRepo = carrelloRepo;
		this.prodottoRepo = prodottoRepo;
		this.joinCarrelloRepo = joinCarrelloRepo;
		this.ordineRepo = ordineRepo;
		this.joinOrdineRepo = joinOrdineRepo;
	}
	
	@Override
	@Transactional
	public void create(OrdineRequest request) throws CustomException {
		Optional<Cliente> cliente = clienteRepo.findById(request.getIdCliente());
		if (cliente.isEmpty()) {
			throw new CustomException("Cliente inesistente");
		}
		Carrello carrello = cliente.get().getCarrello();
		if (carrello == null || carrello.getProdotti().isEmpty()) {
			throw new CustomException("il carrello è vuoto");
		}
		Ordine ordine = new Ordine();
		ordine.setCliente(cliente.get());
		ordine.setSpedito(false);
		ordine.setDataOrdine(LocalDate.now());
		double totale = 0.0;
		List<ProdottoOrdine> listaProdotti = new ArrayList<ProdottoOrdine>();
		for (ProdottoCarrello prodCar : carrello.getProdotti()) {
			ProdottoOrdine prodOrd = new ProdottoOrdine(
					ordine,
					prodCar.getProdotto(),
					prodCar.getQuantita(),
					prodCar.getProdotto().getPrezzo()
					);
			listaProdotti.add(prodOrd);
			totale += prodCar.getProdotto().getPrezzo()*prodCar.getQuantita();
		}
		ordine.setTotaleImporto(totale);
		ordine.setProdotti(listaProdotti);
		ordineRepo.save(ordine);
		cliente.get().setCarrello(null);
		clienteRepo.save(cliente.get());
		joinCarrelloRepo.deleteAll(carrello.getProdotti());
		carrello.getProdotti().clear();
		carrelloRepo.delete(carrello);
	}

	@Override
	public void delete(OrdineRequest request) throws CustomException {
		Optional<Ordine> ordine = ordineRepo.findById(request.getIdOrdine());
		if (ordine.isEmpty()) {
			throw new CustomException("L'ordine non esiste");
		}
		if (ordine.get().getSpedito()) {
			throw new CustomException("L'ordine è già stato spedito, non si può eliminare");
		}
		for (ProdottoOrdine prodOrd : ordine.get().getProdotti()) {
			Prodotto prodotto = prodOrd.getProdotto();
			prodotto.setQuantita(prodotto.getQuantita() + prodOrd.getQuantita());
			prodottoRepo.save(prodotto);
		}
		joinOrdineRepo.deleteAll(ordine.get().getProdotti());
		ordineRepo.delete(ordine.get());
	}

	@Override
	public void update(OrdineRequest request) throws CustomException {
		Optional<Ordine> ordine = ordineRepo.findById(request.getIdOrdine());
		if (ordine.isEmpty()) {
			throw new CustomException("L'ordine non esiste");
		}
		if (ordine.get().getSpedito()) {
			throw new CustomException("L'ordine è già stato spedito, non si può eliminare");
		}
		if (request.getSpedito() == null) {
			ordine.get().setSpedito(false);
		} else {
			ordine.get().setSpedito(request.getSpedito());
		}
		ordineRepo.save(ordine.get());
	}

	@Override
	public List<OrdineDTO> listaByCliente(Integer id) throws CustomException {
		Optional<Cliente> cliente = clienteRepo.findById(id);
		if (cliente.isEmpty()) {
			throw new CustomException("Cliente inesistente");
		}
		List<Ordine> ordini = cliente.get().getOrdini();
		return ordini.stream()
				.map(ordine -> new OrdineDTO.Builder()
						.idOrdine(ordine.getIdOrdine())
						.dataOrdine(ordine.getDataOrdine())
						.spedito(ordine.getSpedito())
						.totaleImporto(ordine.getTotaleImporto())
						.prodotti(ordine.getProdotti().stream()
								.map(prodotto -> new ProdottoOrdineDTO.Builder()
										.prezzoAcquisto(prodotto.getPrezzoAcquisto())
										.quantita(prodotto.getQuantita())
										.prodotto(new ProdottoDTO.Builder()
														.idProdotto(prodotto.getProdotto().getIdProdotto())
														.titolo(prodotto.getProdotto().getTitolo())
														.artista(prodotto.getProdotto().getArtista())
														.immagineProdotto(prodotto.getProdotto().getImmagineProdotto())
												.build())
										.build())
								.toList())
						.build())
				.toList();
	}

}
