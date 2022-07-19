package one.di.pps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import one.di.pps.model.Client;
import one.di.pps.service.ClientService;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 * 
 * @author falvojr
 */
@RestController
@RequestMapping("clients")
public class ClientRestController {

	@Autowired
	private ClientService clientService;

	@GetMapping
	public ResponseEntity<Iterable<Client>> buscarTodos() {
		return ResponseEntity.ok(clientService.buscarTodos());
	}

	@GetMapping("/{nome}/All")
	public ResponseEntity<Iterable<Client>> buscarPorNome(@PathVariable String nome) {
		return ResponseEntity.ok(clientService.buscarPorNome(nome));
	}

	@GetMapping("/Estado/{uf}")
	public ResponseEntity<Iterable<Client>> buscarPorUf(@PathVariable String uf) {
		return ResponseEntity.ok(clientService.buscarPorUf(uf));
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Client> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(clientService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<Client> inserir(@RequestBody Client client) {
		clientService.inserir(client);
		return ResponseEntity.ok(client);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Client> atualizar(@PathVariable Long id, @RequestBody Client client) {
		clientService.atualizar(id, client);
		return ResponseEntity.ok(client);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		clientService.deletar(id);
		return ResponseEntity.ok().build();
	}
}