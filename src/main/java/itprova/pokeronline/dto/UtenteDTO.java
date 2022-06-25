package itprova.pokeronline.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import itprova.pokeronline.model.Ruolo;
import itprova.pokeronline.model.StatoUtente;
import itprova.pokeronline.model.Utente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UtenteDTO {

	private Long id;

	@NotBlank(message = "{nome.notblank}")
	private String nome;

	@NotBlank(message = "{cognome.notblank}")
	private String cognome;

	@NotBlank(message = "{username.notblank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String username;

	private String password;

	private Date dataRegistrazione;

	private StatoUtente stato;

	private Integer esperienzaAccumulata;

	private Integer creditoAccumulato;

	private Long[] ruoliIds;

	public Utente buildUtenteModel(boolean includeIdRoles) {
		Utente result = Utente.builder().id(this.id).username(this.username).password(this.password).nome(this.nome)
				.cognome(this.cognome).dateCreated(this.dataRegistrazione).stato(this.stato)
				.esperienzaAccumulata(this.esperienzaAccumulata).creditoAccumulato(this.creditoAccumulato).build();
		if (includeIdRoles && ruoliIds != null)
			result.setRuoli(Arrays.asList(ruoliIds).stream().map(id -> Ruolo.builder().id(id).build())
					.collect(Collectors.toSet()));

		return result;
	}

	// niente password...
	public static UtenteDTO buildUtenteDTOFromModel(Utente utenteModel) {
		UtenteDTO result = UtenteDTO.builder().id(utenteModel.getId()).username(utenteModel.getUsername())
				.password(utenteModel.getPassword()).nome(utenteModel.getNome()).cognome(utenteModel.getCognome())
				.stato(utenteModel.getStato()).dataRegistrazione(utenteModel.getDateCreated())
				.esperienzaAccumulata(utenteModel.getEsperienzaAccumulata())
				.creditoAccumulato(utenteModel.getCreditoAccumulato()).build();

		if (!utenteModel.getRuoli().isEmpty())
			result.ruoliIds = utenteModel.getRuoli().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});

		return result;
	}

	public static List<UtenteDTO> createUtenteDTOListFromModelList(List<Utente> modelListInput) {
		return modelListInput.stream().map(utenteEntity -> {
			return UtenteDTO.buildUtenteDTOFromModel(utenteEntity);
		}).collect(Collectors.toList());
	}

}