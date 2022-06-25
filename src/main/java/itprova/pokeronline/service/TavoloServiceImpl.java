package itprova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.model.Utente;
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
	@Transactional
	public Tavolo aggiorna(Tavolo tavolo) {
		
		return tavoloRepository.save(tavolo);
	}

	@Override
	@Transactional
	public void rimuovi(Tavolo tavolo) {
	
		tavoloRepository.delete(tavolo);
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavolo(Long id) {

		return tavoloRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloEagerGiocatori(Long id) {
		
		return tavoloRepository.findEager(id);
	}

	@Override
	@Transactional(readOnly = true)

	public List<Tavolo> listAllTavoli() {

		return (List<Tavolo>) tavoloRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tavolo> listaTavoliCreatiDa(Utente utente) {
		
		return (List<Tavolo>) tavoloRepository.findAllCreatedBy(utente);
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloConUtente(Long id, Utente utente ) {
		
		return tavoloRepository.findEagerConUser(id, utente);
	}

}
