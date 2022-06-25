package itprova.pokeronline.repository.utente;

import java.util.List;

import itprova.pokeronline.model.Utente;

public interface CustomUtenteRepository {

	List<Utente> findByExample(Utente example);

}
