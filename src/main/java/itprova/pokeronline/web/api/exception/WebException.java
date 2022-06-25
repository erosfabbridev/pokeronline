package itprova.pokeronline.web.api.exception;

import org.springframework.http.HttpStatus;

public abstract class WebException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public WebException(String string) {
		super(string);
	}

	public abstract HttpStatus getRisposta();
	
}