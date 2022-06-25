package itprova.pokeronline.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itprova.pokeronline.dto.TavoloDTO;
import itprova.pokeronline.model.Utente;
import itprova.pokeronline.service.TavoloService;
import itprova.pokeronline.service.UtenteService;
import itprova.pokeronline.web.api.exception.CreditoMinimoException;

@RestController
@RequestMapping("api/playManagment")
public class PlayManagmentController {

	@Autowired
	UtenteService utenteService;
	@Autowired
	TavoloService tavoloService;

	@PostMapping("/compraCredito/{credito}")
	public Integer createNew(@PathVariable(value = "credito", required = true) Integer credito) {

		if (credito < 1) {
			throw new CreditoMinimoException("Il credito da aggiungere non deve essere minore di 1");
		}
		// volendo potrei fare una query nativa per incrementare il credito passando
		// utente e credito
		Utente giocatore = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		giocatore.setCreditoAccumulato(giocatore.getCreditoAccumulato() + credito);
		utenteService.aggiorna(giocatore);
		return giocatore.getCreditoAccumulato();
	}

	@GetMapping("/lastGame")
	public List<TavoloDTO> getById() {

		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.caricaTavoliInCuiSonoPresente(
				utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
	}

}
