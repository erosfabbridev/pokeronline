package itprova.pokeronline.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AggiungiGiocatoreDTO {
	@NotNull
	private Long idTavolo;
	@NotNull
	private Long idGiocatore;
	
}
