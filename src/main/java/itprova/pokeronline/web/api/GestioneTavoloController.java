package itprova.pokeronline.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import itprova.pokeronline.dto.AggiungiGiocatoreDTO;
import itprova.pokeronline.dto.TavoloDTO;
import itprova.pokeronline.dto.TavoloExampleDTO;
import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.service.TavoloService;
import itprova.pokeronline.service.UtenteService;
import itprova.pokeronline.web.api.exception.IdNotNullForInsertException;
import itprova.pokeronline.web.api.exception.IdNullForUpdateException;
import itprova.pokeronline.web.api.exception.TavoloHasGiocatoriException;
import itprova.pokeronline.web.api.exception.TavoloNotFoundException;

@RestController
@RequestMapping("api/gestioneTavolo")
public class GestioneTavoloController {

	@Autowired
	TavoloService tavoloService;

	@Autowired
	UtenteService utenteService;

	@GetMapping
	public List<TavoloDTO> getAll() {

		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
			List<TavoloDTO> list = TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listAllTavoli());
			return list;
		}

		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listaTavoliCreatiDa(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
	}

	@PostMapping
	public TavoloDTO createNew(@Valid @RequestBody TavoloDTO tavoloInput) {

		if (tavoloInput.getId() != null)
			throw new IdNotNullForInsertException("Non ?? ammesso fornire un id per la creazione");

		Tavolo tavoloDaInserire = tavoloInput.buildTavoloModel();
		tavoloDaInserire.setUtenteCreazione(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		Tavolo tavoloInsert = tavoloService.inserisciNuovo(tavoloDaInserire);
		return TavoloDTO.createTavoloDTOfromModel(tavoloInsert);

	}

	@GetMapping("/{id}")
	public TavoloDTO getById(@PathVariable(value = "id", required = true) Long id) {

		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
			return TavoloDTO.createTavoloDTOfromModel(tavoloService.caricaSingoloTavolo(id));

		}

		return TavoloDTO.createTavoloDTOfromModel(tavoloService.caricaSingoloTavoloConUtente(id,
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
	}

	// un utente potrebbe eliminare un qualsiasi tavolo inserendo un id casuale
	@SuppressWarnings("unused")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Tavolo tavolo = tavoloService.caricaSingoloTavoloEagerGiocatori(id);
		if (!tavolo.getGiocatori().isEmpty()) {
			throw new TavoloHasGiocatoriException("Il tavolo ha dei giocatori collegati");
		}

		if (tavolo == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);

		tavoloService.rimuovi(tavolo);
	}

	@PutMapping("/update")
	public TavoloDTO updateTavolo(@Valid @RequestBody TavoloDTO tavoloInput) {

		if (tavoloInput.getId() == null)
			throw new IdNullForUpdateException("E' necessario un id per l'update del tavolo");

		Tavolo tavoloDaModificare = tavoloInput.buildTavoloModel();
		tavoloDaModificare.setUtenteCreazione(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		Tavolo tavoloModificato = tavoloService.aggiorna(tavoloDaModificare);
		return TavoloDTO.createTavoloDTOfromModel(tavoloModificato);

	}

	// endpoint non molto utile dato che l'utente pu?? scegliere a piacimento il
	// tavolo
	@PostMapping("/aggiungiGiocatore")
	public TavoloDTO aggiungiGiocatoreATavolo(@Valid @RequestBody AggiungiGiocatoreDTO aggiungiGiocatoreDTO) {

		return TavoloDTO.createTavoloDTOfromModel(tavoloService
				.aggiungiGiocatoreATavolo(aggiungiGiocatoreDTO.getIdTavolo(), aggiungiGiocatoreDTO.getIdGiocatore()));

	}

	@PostMapping("/search")
	public List<TavoloDTO> findByExample(@RequestBody TavoloExampleDTO exampleDTO) {

		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {

			return TavoloDTO
					.createTavoloDTOListFromModelList(tavoloService.findByExample(exampleDTO.buildTavoloModel(), null));

		}
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.findByExample(exampleDTO.buildTavoloModel(),
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));

	}

}
