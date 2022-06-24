package itprova.pokeronline;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import itprova.pokeronline.model.Ruolo;
import itprova.pokeronline.model.Utente;
import itprova.pokeronline.service.RuoloService;
import itprova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance
					.inserisciNuovo(Ruolo.builder().descrizione("Administrator").codice(Ruolo.ROLE_ADMIN).build());
		}
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Player", Ruolo.ROLE_PLAYER) == null) {
			ruoloServiceInstance
					.inserisciNuovo(Ruolo.builder().descrizione("Player").codice(Ruolo.ROLE_PLAYER).build());
		}
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_SPECIAL_PLAYER) == null) {
			ruoloServiceInstance
					.inserisciNuovo(Ruolo.builder().descrizione("Player").codice(Ruolo.ROLE_SPECIAL_PLAYER).build());
		}
		
		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = Utente.builder().nome("mario")
										   .cognome("rossi")
										   .username("admin")
										   .password("admin")
										   .dateCreated(new Date()).build();
			
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
			utenteServiceInstance.inserisciNuovo(admin);
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}
		
		if (utenteServiceInstance.findByUsername("player") == null) {
			Utente player = Utente.builder().nome("paolo")
										    .cognome("verdi")
										    .username("player")
										    .password("player")
										    .dateCreated(new Date()).build();
			
			player.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Player", Ruolo.ROLE_PLAYER));
			utenteServiceInstance.inserisciNuovo(player);
		}
		
		if (utenteServiceInstance.findByUsername("specialplayer") == null) {
			Utente specialPlayer = Utente.builder().nome("paolo")
										    .cognome("verdi")
										    .username("specialplayer")
										    .password("specialplayer")
										    .dateCreated(new Date()).build();
			
			specialPlayer.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player", Ruolo.ROLE_PLAYER));
			utenteServiceInstance.inserisciNuovo(specialPlayer);
		}
		
		
	
	
	}

}
