package one.di.pps.model;


import java.util.List;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
	
	List<Client> findByNome(String nome);
	List<Client> findByAddress_Uf(String uf);
	
}