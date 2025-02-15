package com.betacom.dischi.services.implementations;

import static com.betacom.dischi.utilities.Utility.buildProdottoCarrelloDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.dischi.DTO.CarrelloDTO;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Carrello;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.models.Prodotto;
import com.betacom.dischi.models.ProdottoCarrello;
import com.betacom.dischi.repository.ICarrelloRepository;
import com.betacom.dischi.repository.IClienteRepository;
import com.betacom.dischi.repository.IProdottoCarrelloRepository;
import com.betacom.dischi.repository.IProdottoRepository;
import com.betacom.dischi.request.CarrelloRequest;
import com.betacom.dischi.services.interfaces.CarrelloService;
import com.betacom.dischi.services.interfaces.SystemMsgServices;

import jakarta.transaction.Transactional;

@Service
public class CarrelloImpl implements CarrelloService {
	
	private IClienteRepository clienteRepo;
	private IProdottoRepository prodottoRepo;
	private ICarrelloRepository carrelloRepo;
	private	IProdottoCarrelloRepository joinRepo;
	private SystemMsgServices msgServ;
	public CarrelloImpl(IClienteRepository clienteRepo,
						IProdottoRepository prodottoRepo,
						ICarrelloRepository carrelloRepo,
						IProdottoCarrelloRepository joinRepo,
						SystemMsgServices msgServ) {
		this.clienteRepo = clienteRepo;
		this.prodottoRepo = prodottoRepo;
		this.carrelloRepo = carrelloRepo;
		this.joinRepo = joinRepo;
		this.msgServ = msgServ;
	}
	
	
	@Override
	public Carrello create(CarrelloRequest request) throws CustomException {
		Optional<Cliente> cliente = clienteRepo.findById(request.getIdCliente());
		if (cliente.isEmpty()) {
			throw new CustomException(msgServ.getSysMsg("no_customer"));
		}
		if (cliente.get().getCarrello() != null) {
			throw new CustomException(msgServ.getSysMsg("yes_cart"));
		}
		Carrello carrello = new Carrello();
		carrello.setCliente(cliente.get());
		carrello.setIdCarrello(carrelloRepo.save(carrello).getIdCarrello());
		return carrello;
	}

	@Override
	@Transactional
	public void addProdotto(CarrelloRequest request) throws CustomException {
		Optional<Cliente> cliente = clienteRepo.findById(request.getIdCliente());
		if (cliente.isEmpty()) {
			throw new CustomException(msgServ.getSysMsg("no_customer"));
		}
		Carrello carrello = new Carrello();
		if (cliente.get().getCarrello() == null) {
			carrello = create(request);
		} else {
			carrello = cliente.get().getCarrello();
		}
		Prodotto prodotto = prodottoRepo.findById(request.getIdProdotto()).get();
		if (prodotto.getQuantita() < request.getQuantita()) {
			throw new CustomException(msgServ.getSysMsg("no_qnt"));
		}
		Optional<ProdottoCarrello> findRecord = joinRepo.findByCarrelloAndProdotto(carrello, prodotto);
		if (findRecord.isEmpty()) {
			ProdottoCarrello row = new ProdottoCarrello(
					carrello, prodotto, request.getQuantita());
			if (carrello.getProdotti()== null) {
				carrello.setProdotti(new ArrayList<ProdottoCarrello>());
			}
			carrello.getProdotti().add(row);
		} else {
			ProdottoCarrello row = findRecord.get();
			row.setQuantita(row.getQuantita() + request.getQuantita());
		}
		prodotto.setQuantita(prodotto.getQuantita() - request.getQuantita());
		prodottoRepo.save(prodotto);
		carrelloRepo.save(carrello);
	}

	@Override
	@Transactional
	public void removeProdotto(CarrelloRequest request) throws CustomException {
		Optional<Cliente> cliente = clienteRepo.findById(request.getIdCliente());
		if (cliente.isEmpty()) {
			throw new CustomException(msgServ.getSysMsg("no_customer"));
		}
		Carrello carrello = cliente.get().getCarrello();

		Prodotto prodotto = prodottoRepo.findById(request.getIdProdotto()).get();
		Optional<ProdottoCarrello> findRecord = joinRepo.findByCarrelloAndProdotto(carrello, prodotto);
		if (findRecord.isPresent()) {
			ProdottoCarrello row = findRecord.get();
			prodotto.setQuantita(prodotto.getQuantita() + request.getQuantita());
			if (row.getQuantita() > request.getQuantita()) {
				row.setQuantita(row.getQuantita() - request.getQuantita());
				joinRepo.save(row);
			} else {
				carrello.getProdotti().remove(row);
				joinRepo.delete(row);
			}
			prodottoRepo.save(prodotto);
			carrelloRepo.save(carrello);
		} else {
			throw new CustomException(msgServ.getSysMsg("no_product"));
		}
	}

	@Override
	@Transactional
	public void delete(CarrelloRequest request) throws CustomException {
		Optional<Cliente> cliente = clienteRepo.findById(request.getIdCliente());
		if (cliente.isEmpty()) {
			throw new CustomException(msgServ.getSysMsg("no_customer"));
		}
		Carrello carrello = new Carrello();
		if (cliente.get().getCarrello() == null) {
			throw new CustomException(msgServ.getSysMsg("empty_cart"));
		} else {
			carrello = cliente.get().getCarrello();
		}
		cliente.get().setCarrello(null);
		clienteRepo.save(cliente.get());
		for (ProdottoCarrello row : carrello.getProdotti()) {
			Prodotto prodotto = row.getProdotto();
			prodotto.setQuantita(prodotto.getQuantita() + row.getQuantita());
			prodottoRepo.save(prodotto);
		}
		carrelloRepo.delete(carrello);
	}

	@Override
	@Transactional
	public CarrelloDTO listaProdotti(Integer id) throws CustomException {
		Optional<Cliente> cliente = clienteRepo.findById(id);
		if (cliente.isEmpty()) {
			throw new CustomException(msgServ.getSysMsg("no_customer"));
		}
		if (cliente.get().getCarrello() == null) {
			throw new CustomException(msgServ.getSysMsg("empty_cart"));
		}
		double totale = 0.0;
		Carrello carrello = cliente.get().getCarrello();
		List<ProdottoCarrello> listaProdotti = carrello.getProdotti();
		for (ProdottoCarrello prodotto : listaProdotti) {
			double prezzo = prodotto.getProdotto().getPrezzo() * prodotto.getQuantita();
			totale += prezzo;
		}
		return new CarrelloDTO.Builder()
				.totale(totale)
				.prodotti(listaProdotti.stream()
							.map(prodotto -> buildProdottoCarrelloDTO(prodotto))
						.toList())
				.build();
	}

//	@Override
//	public List<CarrelloDTO> listaProdotti(Integer idCliente) throws CustomException {
//		List<Cliente> listaCompleta = clienteRepo.findAll();
//		return listaCompleta.stream().map(cliente -> new CarrelloDTO.Builder().build()).toList();
//	}
}
