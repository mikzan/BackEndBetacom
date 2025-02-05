package com.betacom.dischi.services.implementations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betacom.dischi.DTO.ClienteDTO;
import com.betacom.dischi.exception.CustomException;
import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.repository.IClienteRepository;
import com.betacom.dischi.request.ClienteRequest;
import com.betacom.dischi.services.interfaces.ClienteService;
import com.betacom.dischi.utilities.mapper.MapperClienteToDTO;

@Service
public class ClienteImpl implements ClienteService {

	@Autowired
	Logger log;

	@Autowired
	IClienteRepository clienteRepo;

	@Override
	public List<ClienteDTO> listAll() {
		List<Cliente> listaClienti = clienteRepo.findAll();
		log.debug("Trovati " + listaClienti.size() + " clienti.");
		return listaClienti.stream().map(MapperClienteToDTO::mapClienteToDTO).collect(Collectors.toList());
	}

	@Override
	public void create(ClienteRequest req) throws CustomException {
		log.debug("Create Cliente: " + req);
		Cliente cliente = new Cliente();
		checkAndSetFields(req, cliente);
		log.debug("Cliente creato con ID: " + cliente.getIdCliente() + " e dettagli: " + cliente);
		clienteRepo.save(cliente);
	}

	@Override
	public void update(ClienteRequest req) throws CustomException {
		log.debug("Update Cliente: " + req);
		Optional<Cliente> optCliente = clienteRepo.findById(req.getIdCliente());
		Cliente cliente = optCliente
				.orElseThrow(() -> new CustomException("Id: " + req.getIdCliente() + " del cliente non trovato."));
		checkAndSetFields(req, cliente);
		clienteRepo.save(cliente);
		log.debug("Cliente con ID: " + req.getIdCliente() + " aggiornato."+ " e dettagli: " + cliente);
	}

	@Override
	public void delete(ClienteRequest req) throws CustomException {
		log.debug("Delete Cliente: " + req);
		Optional<Cliente> optCliente = clienteRepo.findById(req.getIdCliente());
		Cliente cliente = optCliente.orElseThrow(
				() -> new CustomException("Id: " + req.getIdCliente() + " del cliente da eliminare non trovato"));
		clienteRepo.delete(cliente);
		log.debug("Cliente con I " + req.getIdCliente() + " eliminato con successo.");
	}

	@Override
	public ClienteDTO listById(Integer id) throws CustomException {
		log.debug("Visualizzazione dati cliente con ID: " + id);
		Cliente cliente = clienteRepo.findById(id)
				.orElseThrow(() -> new CustomException("Cliente con id " + id + " non trovato"));
		return MapperClienteToDTO.mapClienteToDTO(cliente);
	}

	private void checkAndSetFields(ClienteRequest req, Cliente cliente) throws CustomException {
		if (req.getNome() == null || req.getNome().isBlank()) {
			throw new CustomException("Il nome non può essere nullo o vuoto");
		}
		if (req.getCognome() == null || req.getCognome().isBlank()) {
			throw new CustomException("Il cognome non può essere nullo o vuoto");
		}
		if (req.getTelefono() == null || req.getTelefono().isBlank()) {
			throw new CustomException("Il numero di telefono non può essere nullo o vuoto");
		}
		// ^ -> inizio stringa, \d -> cifra numerica, \\s- -> spazio o trattino, $ fine della stringa
		// ? -> opzionale
		String telefonoRegex = "^\\+?\\d{1,3}[\\s-]?\\(?\\d{1,4}\\)?[\\s-]?\\d{1,4}[\\s-]?\\d{1,4}$";
		if (!req.getTelefono().matches(telefonoRegex)) {
			throw new CustomException("Numero di telefono non valido, non rispetta formato internazionale");
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
		cliente.setDataRegistrazione(LocalDate.now());
	}
}