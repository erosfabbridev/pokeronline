package itprova.pokeronline.repository.tavolo;

import java.util.List;

import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.model.Utente;

public interface CustomTavoloRepository {

	List<Tavolo> findByExample(Tavolo example, Utente utente);



}
