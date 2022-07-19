package one.di.pps.service;

import java.util.List;

import one.di.pps.model.Client;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author falvojr
 */
public interface ClientService {

	Iterable<Client> buscarTodos();

	List<Client> buscarPorNome(String nome);

	List<Client> buscarPorUf(String uf);

	Client buscarPorId(Long id);

	void inserir(Client client);

	void atualizar(Long id, Client client);

	void deletar(Long id);

}