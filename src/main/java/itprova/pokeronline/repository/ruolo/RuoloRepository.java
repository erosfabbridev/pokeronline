package itprova.pokeronline.repository.ruolo;

import org.springframework.data.repository.CrudRepository;

import itprova.pokeronline.model.Ruolo;

public interface RuoloRepository extends CrudRepository<Ruolo, Long> {
	Ruolo findByDescrizioneAndCodice(String descrizione, String codice);
}
