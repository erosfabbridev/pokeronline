package itprova.pokeronline.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import itprova.pokeronline.model.Tavolo;
import itprova.pokeronline.model.Utente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TavoloDTO {

	private Long id;
	@NotNull
	private Integer esperienzaMin;
	@NotNull
	private Integer cifraMinima;
	@NotBlank
	private String denominazione;
	private LocalDate dataCreazione;
	@Builder.Default
	@JsonIgnoreProperties(value = "tavolo")
	private List<GiocatoreDTO> giocatori = new ArrayList<>(0);
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Utente utenteCreazione;

	public Tavolo buildFilmModel() {

		return Tavolo.builder().cifraMinima(cifraMinima).dataCreazione(dataCreazione).denominazione(denominazione)
				.esperienzaMin(esperienzaMin).utenteCreazione(utenteCreazione).build();
	}

	public static TavoloDTO createTavoloDTOfromModel(Tavolo tavolo) {

		return TavoloDTO.builder().id(tavolo.getId()).cifraMinima(tavolo.getCifraMinima())
				.esperienzaMin(tavolo.getEsperienzaMin()).dataCreazione(tavolo.getDataCreazione())
				.utenteCreazione(tavolo.getUtenteCreazione()).denominazione(tavolo.getDenominazione()).build();
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> listAllTavoli) {

		return listAllTavoli.stream().map(tavolo -> {
			return TavoloDTO.createTavoloDTOfromModel(tavolo);
		}).collect(Collectors.toList());

	}

}
