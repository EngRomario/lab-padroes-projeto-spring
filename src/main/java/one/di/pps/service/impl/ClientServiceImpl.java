package one.di.pps.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.di.pps.model.Client;
import one.di.pps.model.ClientRepository;
import one.di.pps.model.Address;
import one.di.pps.model.AddressRepository;
import one.di.pps.service.ClientService;
import one.di.pps.service.ViaCepService;

/**
 * Implementação da <b>Strategy</b> {@link ClientService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr
 */
@Service
public class ClientServiceImpl implements ClientService {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ViaCepService viaCepService;

	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	public Iterable<Client> buscarTodos() {
		// Buscar todos os Clients.
		return clientRepository.findAll();
	}

	@Override
	public Client buscarPorId(Long id) {
		// Buscar Client por ID.
		Optional<Client> client = clientRepository.findById(id);
		if(client.isPresent())
			return client.get();
		return null;
	}

	@Override
	public List<Client> buscarPorNome(String nome) { 
		return clientRepository.findByNome(nome);
	}
	
	@Override
	public List<Client> buscarPorUf(String uf) { 
		return clientRepository.findByAddress_Uf(uf);
	}
	
	@Override
	public void inserir(Client client) {
		salvarClientComCep(client);
	}

	@Override
	public void atualizar(Long id, Client client) {
		// Buscar Client por ID, caso exista:
		Optional<Client> clientBd = clientRepository.findById(id);
		if (clientBd.isPresent()) {
			salvarClientComCep(client);
		}
	}

	@Override
	public void deletar(Long id) {
		// Deletar Client por ID.
		clientRepository.deleteById(id);
	}

	private void salvarClientComCep(Client client) {
		// Verificar se o Address do Client já existe (pelo CEP).
		String cep = client.getAddress().getCep();
		Address address = addressRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Address novoAddress = viaCepService.consultarCep(cep);
			addressRepository.save(novoAddress);
			return novoAddress;
		});
		client.setAddress(address);
		// Inserir Client, vinculando o Address (novo ou existente).
		clientRepository.save(client);
	}

}