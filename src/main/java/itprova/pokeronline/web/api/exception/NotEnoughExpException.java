package itprova.pokeronline.web.api.exception;

public class NotEnoughExpException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotEnoughExpException(String string) {
		super(string);
	}
}
