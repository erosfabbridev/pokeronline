package itprova.pokeronline.web.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itprova.pokeronline.dto.UtenteDTO;
import itprova.pokeronline.model.Ruolo;
import itprova.pokeronline.model.Utente;
import itprova.pokeronline.service.RuoloService;
import itprova.pokeronline.service.UtenteService;
import itprova.pokeronline.web.api.exception.CreditoMinimoException;
import itprova.pokeronline.web.api.exception.IdNotNullForInsertException;
import itprova.pokeronline.web.api.exception.WebException;

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
//			  WebException ex = new CreditoMinimoException("Non è ammesso fornire un id per la creazione");
//			  throw ex;
		}
			
		Utente utente = utenteInput.buildUtenteModel(false);
		utente.getRuoli().add(ruoloService.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
		utenteService.inserisciNuovo(utente);
		
		return UtenteDTO.buildUtenteDTOFromModel(utente);
	}
//	@GetMapping
//	public UtenteDTO findById() {
//		
//	}
//	
}
