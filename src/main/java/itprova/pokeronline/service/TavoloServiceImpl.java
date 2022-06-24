package itprova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.repository.tavolo.TavoloRepository;

@Service
public class TavoloServiceImpl implements TavoloService {

	@Autowired
	TavoloRepository tavoloRepository;

	@Override
	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavolo) {

		return tavoloRepository.save(tavolo);
	}

	@Override
	public Tavolo aggiorna(Tavolo tavolo) {
		
		return tavoloRepository.save(tavolo);
	}

	@Override
	public void cancella(Tavolo tavolo) {

		tavoloRepository.delete(tavolo);
	}

	@Override
	public Tavolo caricaSingoloTavolo(Long id) {

		return tavoloRepository.findById(id).orElse(null);
	}

	@Override
	public Tavolo caricaSingoloTavoloEagerGiocatori(Long id) {
		
		return tavoloRepository.findEager(id);
	}

	@Override
	public List<Tavolo> listAllTavoli() {

		return (List<Tavolo>) tavoloRepository.findAll();
	}

}
