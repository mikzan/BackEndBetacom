package com.betacom.dischi.services.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betacom.dischi.DTO.ClienteDTO;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.repository.IClienteRepository;
import com.betacom.dischi.request.ClienteRequest;
import com.betacom.dischi.services.interfaces.ClienteService;
import com.betacom.dischi.services.interfaces.SystemMsgServices;
import com.betacom.dischi.utilities.Utility;

import jakarta.transaction.Transactional;

import static com.betacom.dischi.utilities.Utility.*;
@Service
public class ClienteImpl implements ClienteService {

	@Autowired
	Logger log;

	@Autowired
	IClienteRepository clienteRepo;
	@Autowired
    SystemMsgServices msgServ;
	
	
	@Override
	@Transactional
	public List<ClienteDTO> listAll(Integer idCliente,String nome,String cognome,String cap,String comune,String provincia) {
		List<Cliente> listaClienti = clienteRepo.filteredClients(idCliente, nome, cognome,cap,comune,provincia);
		return listaClienti.stream()
				.map(c -> buildClienteDTO(c)).toList();
	}

	@Override
	@Transactional
	public ClienteDTO create(ClienteRequest req) throws CustomException {
		log.debug("Create Cliente: " + req);
		Cliente cliente = new Cliente();
		checkAndSetFields(req, cliente);
		cliente.setDataRegistrazione(LocalDate.now());
		log.debug("Cliente creato con ID: " + cliente.getIdCliente() + " e dettagli: " + cliente);
	    cliente = clienteRepo.save(cliente); 
	    ClienteDTO clienteDTO = new ClienteDTO.Builder()
	            .idCliente(cliente.getIdCliente()) 
	            .dataRegistrazione(cliente.getDataRegistrazione())
	            .build();  
        log.info(msgServ.getSysMsg("customer_created"));

	    return clienteDTO;  
       
	}

	@Override
	@Transactional
	public void update(ClienteRequest req) throws CustomException {
		log.debug("Update Cliente: " + req);
		Optional<Cliente> optCliente = clienteRepo.findById(req.getIdCliente());
		Cliente cliente = optCliente
                .orElseThrow(() -> new CustomException(msgServ.getSysMsg("no_customer_for_update")));
		checkAndSetFields(req, cliente);
		clienteRepo.save(cliente);
        log.info(msgServ.getSysMsg("customer_updated"));

	}

	@Override
	@Transactional
	public void delete(ClienteRequest req) throws CustomException {
		log.debug("Delete Cliente: " + req);
		Optional<Cliente> optCliente = clienteRepo.findById(req.getIdCliente());
		Cliente cliente = optCliente.orElseThrow(
                () -> new CustomException(msgServ.getSysMsg("no_customer_for_delete")));
		clienteRepo.delete(cliente);
		log.debug("Cliente con ID: " + req.getIdCliente() + " eliminato con successo.");
        log.info(msgServ.getSysMsg("customer_deleted"));

	}

	@Override
	@Transactional
	public ClienteDTO listById(Integer id) throws CustomException {
		log.debug("Visualizzazione dati cliente con ID: " + id);
		Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new CustomException(msgServ.getSysMsg("no_customer")));
		return Utility.buildClienteDTO(cliente);
	}

	private void checkAndSetFields(ClienteRequest req, Cliente cliente) throws CustomException {
		if (req.getNome() == null || req.getNome().isBlank()) {
            throw new CustomException(msgServ.getSysMsg("name_invalid"));
		}
		if (req.getCognome() == null || req.getCognome().isBlank()) {
            throw new CustomException(msgServ.getSysMsg("surname_invalid"));
		}
		if (req.getTelefono() == null || req.getTelefono().isBlank()) {
            throw new CustomException(msgServ.getSysMsg("phone_invalid"));
		}
		if (req.getComune() == null || req.getComune().isBlank()) {
            throw new CustomException(msgServ.getSysMsg("municipality_invalid"));
		}
		if (req.getCap() == null || req.getCap().isBlank()) {
            throw new CustomException(msgServ.getSysMsg("cap_invalid"));
		}
		if (req.getProvincia() == null || req.getProvincia().isBlank()) {
            throw new CustomException(msgServ.getSysMsg("province_invalid"));
		}
		if (req.getVia() == null || req.getVia().isBlank()) {
            throw new CustomException(msgServ.getSysMsg("address_invalid"));
		}
		// ^ -> inizio stringa, \d -> cifra numerica, \\s- -> spazio o trattino, $ fine della stringa
		// ? -> opzionale
		String telefonoRegex = "^\\+?\\d{1,3}[\\s-]?\\(?\\d{1,4}\\)?[\\s-]?\\d{1,4}[\\s-]?\\d{1,4}$";
		if (!req.getTelefono().matches(telefonoRegex)) {
            throw new CustomException(msgServ.getSysMsg("phone_format_invalid"));
		}
		if (req.getImmagineCliente() == null || req.getImmagineCliente().isBlank()) {
			cliente.setImmagineCliente(
					"https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
		} else {
			cliente.setImmagineCliente(req.getImmagineCliente());
		}
		cliente.setNome(req.getNome());
		cliente.setCognome(req.getCognome());
		cliente.setTelefono(req.getTelefono());
		cliente.setCap(req.getCap());
		cliente.setProvincia(req.getProvincia());
		cliente.setVia(req.getVia());
		cliente.setComune(req.getComune());
	}
}