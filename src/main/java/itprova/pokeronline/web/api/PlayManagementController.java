package itprova.pokeronline.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itprova.pokeronline.dto.TavoloDTO;
import itprova.pokeronline.game.PokerGameSimulator;
import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.model.Utente;
import itprova.pokeronline.service.TavoloService;
import itprova.pokeronline.service.UtenteService;
import itprova.pokeronline.web.api.exception.CreditoMinimoException;
import itprova.pokeronline.web.api.exception.NotEnoughExpException;

@RestController
@RequestMapping("api/playManagement")
public class PlayManagementController {

	@Autowired
	UtenteService utenteService;
	@Autowired
	TavoloService tavoloService;

	@GetMapping("/compraCredito/{credito}")
	public Integer createNew(@PathVariable(value = "credito", required = true) Integer credito) {

		if (credito < 1) {
			throw new CreditoMinimoException("Il credito da aggiungere non deve essere minore di 1");
		}
		// volendo potrei fare una query nativa per incrementare il credito passando
		// utente e credito
		Utente giocatore = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		giocatore.setCreditoAccumulato(giocatore.getCreditoAccumulato() + credito);
		if (giocatore.getId() <1 || giocatore.getId() == null || giocatore == null) {
			throw new RuntimeException();
		}
		utenteService.aggiorna(giocatore);
		return giocatore.getCreditoAccumulato();
	}

	@GetMapping("/lastGame")
	public List<TavoloDTO> lastGame() {

		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.caricaTavoliInCuiSonoPresente(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
	}

	@GetMapping("/abbandonaPartita/{idTavolo}")
	public void abbandona(@PathVariable(value = "idTavolo", required = true) Long idTavolo) {

		Utente giocatore = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		tavoloService.abbandonaPartita(idTavolo, giocatore);
	}

	@GetMapping("/ricercaTavoliEsperienzaSufficiente")
	public List<TavoloDTO> ricercaTavoliToJoin() {

		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.trovaTavoliPerGiocare(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
	}

	@GetMapping("/giocaPartitaAlTavolo/{idTavolo}")
	public Integer gioca(@PathVariable(value = "idTavolo", required = true) Long idTavolo) {

		Utente giocatore = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Tavolo tavolo = tavoloService.caricaSingoloTavoloEagerGiocatori(idTavolo);

		if (giocatore.getEsperienzaAccumulata() < tavolo.getEsperienzaMin()) {
			throw new NotEnoughExpException("Non hai abbastanza esperienza per giocare a questo tavolo");
		}

		if (giocatore.getCreditoAccumulato() < tavolo.getCifraMinima()) {
			throw new CreditoMinimoException("non hai abbastanza soldi per questo tavolo");
		}
		if (!tavolo.getGiocatori().contains(giocatore))
			tavoloService.aggiungiGiocatoreATavolo(idTavolo, idTavolo);
		
		giocatore.setCreditoAccumulato(giocatore.getCreditoAccumulato()+PokerGameSimulator.simulaPartita());
		if (giocatore.getCreditoAccumulato() < 0) {
			giocatore.setCreditoAccumulato(0);
		}
		utenteService.aggiorna(giocatore);
		
		return giocatore.getCreditoAccumulato();
	}

}
