package itprova.pokeronline.service;

import java.util.List;

import itprova.pokeronline.model.Tavolo;

public interface TavoloService {
	
	Tavolo inserisciNuovo(Tavolo tavolo);
	
	Tavolo aggiorna(Tavolo tavolo);
	
	void cancella(Tavolo tavolo);
	
	Tavolo caricaSingoloTavolo(Long id);
	
	Tavolo caricaSingoloTavoloEagerGiocatori(Long id);
	
	List<Tavolo> listAllTavoli();
	
}
