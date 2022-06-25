package itprova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.model.Utente;

public interface TavoloRepository  extends CrudRepository<Tavolo, Long> {

	@Query("from Tavolo t join fetch t.giocatori where t.id=t.id")
	List<Tavolo> findAllEager();
	
	@Query("from Tavolo t join fetch t.giocatori where t.id=:id")
	Tavolo findEager(Long id);
	
	@Query("from Tavolo t where t.utenteCreazione=:utente")
	List<Tavolo> findAllCreatedBy(Utente utente);

	@Query("from Tavolo t join fetch t.giocatori where t.id=:id and t.utenteCreazione=:utente")
	Tavolo findEagerConUser(Long id, Utente utente);
}
