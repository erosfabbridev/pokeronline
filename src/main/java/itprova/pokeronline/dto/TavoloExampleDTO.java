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
public class TavoloExampleDTO {

	private Long id;
	private Integer esperienzaMin;
	private Integer cifraMinima;
	private String denominazione;
	private LocalDate dataCreazione;
	@Builder.Default
	@JsonIgnoreProperties(value = "tavolo")
	private List<UtenteDTO> giocatori = new ArrayList<>(0);
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Utente utenteCreazione;

	public Tavolo buildTavoloModel() {

		return Tavolo.builder().cifraMinima(cifraMinima).dataCreazione(dataCreazione).denominazione(denominazione)
				.esperienzaMin(esperienzaMin).utenteCreazione(utenteCreazione).build();
	}

	public static TavoloExampleDTO createTavoloExampleDTOfromModel(Tavolo tavolo) {

		return TavoloExampleDTO.builder().id(tavolo.getId()).cifraMinima(tavolo.getCifraMinima())
				.esperienzaMin(tavolo.getEsperienzaMin()).dataCreazione(tavolo.getDataCreazione())
				.utenteCreazione(tavolo.getUtenteCreazione()).denominazione(tavolo.getDenominazione()).build();
	}

	public static List<TavoloExampleDTO> createTavoloExampleDTOListFromModelList(List<Tavolo> listAllTavoli) {

		return listAllTavoli.stream().map(tavolo -> {
			return TavoloExampleDTO.createTavoloExampleDTOfromModel(tavolo);
		}).collect(Collectors.toList());

	}
	
	

}
