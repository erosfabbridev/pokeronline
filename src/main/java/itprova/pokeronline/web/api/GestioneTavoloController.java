package itprova.pokeronline.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itprova.pokeronline.dto.TavoloDTO;
import itprova.pokeronline.service.TavoloService;

@RestController
@RequestMapping("api/gestioneTavolo")
public class GestioneTavoloController {

	@Autowired
	TavoloService tavoloService;
	

	@GetMapping
	public List<TavoloDTO> getAll() {
		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listAllTavoli());
	}

}
