package itprova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import itprova.pokeronline.model.Tavolo;

public interface TavoloRepository  extends CrudRepository<Tavolo, Long> {

	@Query("from Tavolo t join fetch t.giocatori where t.id=t.id")
	List<Tavolo> findAllEager();
	
	@Query("from Tavolo t join fetch t.giocatori where t.id=:id")
	Tavolo findEager(Long id);
}
