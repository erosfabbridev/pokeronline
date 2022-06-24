package itprova.pokeronline.web.api.exception;

public class TavoloNotFoundException  extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public TavoloNotFoundException(String string) {
		super(string);
	}
	
}
