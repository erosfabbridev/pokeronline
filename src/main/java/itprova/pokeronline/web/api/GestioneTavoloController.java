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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import itprova.pokeronline.dto.TavoloDTO;
import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.service.TavoloService;
import itprova.pokeronline.service.UtenteService;
import itprova.pokeronline.web.api.exception.IdNotNullForInsertException;
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
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Tavolo tavoloDaInserire = tavoloInput.buildFilmModel();
		tavoloDaInserire.setUtenteCreazione(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		Tavolo tavoloInsert = tavoloService.inserisciNuovo(tavoloDaInserire);
		return TavoloDTO.createTavoloDTOfromModel(tavoloInsert);

	}

	@GetMapping("/{id}")
	public TavoloDTO getById(@PathVariable(value = "id", required = true) long id) {

		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
			TavoloDTO tavolo = TavoloDTO.createTavoloDTOfromModel(tavoloService.caricaSingoloTavolo(id));
			return tavolo;
		}

		return TavoloDTO.createTavoloDTOfromModel(tavoloService.caricaSingoloTavoloConUtente(id,
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Tavolo tavolo = tavoloService.caricaSingoloTavolo(id);

		if (tavolo == null)
			throw new TavoloNotFoundException("Regista not found con id: " + id);

		tavoloService.rimuovi(tavolo);
	}


}
