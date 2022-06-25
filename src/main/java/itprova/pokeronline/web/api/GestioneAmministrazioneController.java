package itprova.pokeronline.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import itprova.pokeronline.dto.UtenteDTO;
import itprova.pokeronline.model.Ruolo;
import itprova.pokeronline.model.Utente;
import itprova.pokeronline.service.RuoloService;
import itprova.pokeronline.service.UtenteService;
import itprova.pokeronline.web.api.exception.IdNotNullForInsertException;
import itprova.pokeronline.web.api.exception.UtenteNotFoundException;

@RestController
@RequestMapping("api/gestioneAmministrazione")
public class GestioneAmministrazioneController {
	
	@Autowired
	UtenteService utenteService;
	@Autowired
	RuoloService ruoloService;
	
	@PostMapping("/nuovoPlayer")
	public UtenteDTO createNewPlayer(@Valid @RequestBody UtenteDTO utenteInput) {

		if (utenteInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
		Utente utente = utenteInput.buildUtenteModel(false);
		utente.getRuoli().add(ruoloService.cercaPerDescrizioneECodice("Player", Ruolo.ROLE_PLAYER));
		utenteService.inserisciNuovo(utente);
		
		return UtenteDTO.buildUtenteDTOFromModel(utente);

	}
	@PostMapping("/nuovoSpecialPlayer")
	public UtenteDTO createNewSpecial(@Valid @RequestBody UtenteDTO utenteInput) {

		if (utenteInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
		Utente utente = utenteInput.buildUtenteModel(false);
		utente.getRuoli().add(ruoloService.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_SPECIAL_PLAYER));
		utenteService.inserisciNuovo(utente);
		
		return UtenteDTO.buildUtenteDTOFromModel(utente);

	}
	@PostMapping("/nuovoAdmin")
	public UtenteDTO createNewAdmin(@Valid @RequestBody UtenteDTO utenteInput) {

		if (utenteInput.getId() != null) {
			  throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
		}
			
		Utente utente = utenteInput.buildUtenteModel(false);
		utente.getRuoli().add(ruoloService.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
		utenteService.inserisciNuovo(utente);
		
		return UtenteDTO.buildUtenteDTOFromModel(utente);
	}
	
	@GetMapping("/utente/{idUtente}")
	public UtenteDTO findById(@PathVariable(value = "idUtente", required = true) Long idUtente) {
		
		return UtenteDTO.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(idUtente));
		
	}
	
	@PostMapping("/search")
	public List<UtenteDTO> search(@RequestBody UtenteDTO example) {
		return UtenteDTO.createUtenteDTOListFromModelList(utenteService.findByExample(example.buildUtenteModel(true)));
	}
	
	@PutMapping("/utente/{id}")
	public UtenteDTO update(@Valid @RequestBody UtenteDTO utenteInput, @PathVariable(required = true) Long id) {
		Utente utente = utenteService.caricaSingoloUtente(id);

		if (utente == null)
			throw new UtenteNotFoundException("Utente not found con id: " + id);

		utenteInput.setId(id);
		Utente utenteAggiornato = utenteService.aggiorna(utenteInput.buildUtenteModel(true));
		return UtenteDTO.buildUtenteDTOFromModel(utenteAggiornato);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Utente utente = utenteService.caricaSingoloUtente(id);

		if (utente == null)
			throw new UtenteNotFoundException("Utente not found con id: " + id);
		utenteService.rimuovi(utente);
	}

	
}
