package itprova.pokeronline.game;

public class PokerGameSimulator {
	
	public static int simulaPartita() {
		
		Double segno = Math.random();
		Double somma = 0.0;
		if (segno >=0.6) {
			somma = Math.random() * 1000;
		}else {
			somma =  Math.random() * -1000;
		}
		
		return somma.intValue();
	}
}
