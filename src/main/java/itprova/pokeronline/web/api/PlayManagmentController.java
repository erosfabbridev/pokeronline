package itprova.pokeronline.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itprova.pokeronline.model.Utente;
import itprova.pokeronline.service.UtenteService;
import itprova.pokeronline.web.api.exception.CreditoMinimoException;

@RestController
@RequestMapping("api/playManagment")
public class PlayManagmentController {
	
	@Autowired
	UtenteService utenteService;
	
	@PostMapping("/compraCredito/{credito}")
	public Integer createNew(@PathVariable(value = "credito", required = true) Integer credito) {
		//volendo potrei fare una query nativa per incrementare il credito passando utente e credito
		if (credito < 1) {
			throw new CreditoMinimoException("Il credito da aggiungere non deve essere minore di 1");
		}
		Utente giocatore = utenteService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		giocatore.setCreditoAccumulato(giocatore.getCreditoAccumulato()+credito);
		utenteService.aggiorna(giocatore);
		return giocatore.getCreditoAccumulato();

	}
}
