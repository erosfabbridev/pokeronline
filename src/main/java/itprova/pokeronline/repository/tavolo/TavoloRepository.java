package itprova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.model.Utente;

public interface TavoloRepository  extends CrudRepository<Tavolo, Long> {

	@Query("from Tavolo t join fetch t.giocatori where t.id=t.id")
	List<Tavolo> findAllEager();
	
	@Query("from Tavolo t left join fetch t.giocatori where t.id=?1")
	Tavolo findByIdEager(Long idTavolo);
	
	@Query("from Tavolo t where t.utenteCreazione=:utente")
	List<Tavolo> findAllCreatedBy(Utente utente);

	@Query("from Tavolo t join fetch t.giocatori where t.id=:id and t.utenteCreazione=:utente")
	Tavolo findEagerConUser(Long id, Utente utente);

	@Query("from Tavolo t join fetch t.giocatori g where g=:utente")
	List<Tavolo> findAllWhereUtenteIsPresent(Utente utente);

	@Query("from Tavolo t join fetch t.giocatori g where g=:utente and t.id=:id")
	Tavolo findByIdWhereUtenteIsPresent(Utente utente, Long id);

	@Query("from Tavolo t where g=:utente and t.esperienzaMin <= :esperienzaAccumulata")
	List<Tavolo> findAllEsperienzaMinoreOUgualeA(Integer esperienzaAccumulata);
	
}
