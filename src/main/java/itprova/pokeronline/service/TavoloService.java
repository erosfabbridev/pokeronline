package itprova.pokeronline.service;

import java.util.List;

import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.model.Utente;

public interface TavoloService {

	Tavolo inserisciNuovo(Tavolo tavolo);

	Tavolo aggiorna(Tavolo tavolo);

	void rimuovi(Tavolo tavolo);

	Tavolo caricaSingoloTavolo(Long id);

	Tavolo caricaSingoloTavoloEagerGiocatori(Long id);

	List<Tavolo> listAllTavoli();

	List<Tavolo> listaTavoliCreatiDa(Utente utente);

	Tavolo caricaSingoloTavoloConUtente(Long id, Utente utente);

	List<Tavolo> caricaTavoliInCuiSonoPresente(Utente utente);

	void abbandonaPartita(Long idTavolo, Utente giocatore);

	List<Tavolo> trovaTavoliPerGiocare(Utente findByUsername);

	Tavolo aggiungiGiocatoreATavolo(Long idTavolo, Long idGiocatore);
	
	List<Tavolo> findByExample (Tavolo tavolo, Utente utente);
	
	

}
