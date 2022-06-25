package itprova.pokeronline.web.api.exception;

import org.springframework.http.HttpStatus;

public class TavoloHasGiocatoriException extends  WebException {

	private static final long serialVersionUID = 1L;

	public TavoloHasGiocatoriException(String string) {
		super(string);
		
		
	}
	public HttpStatus getRisposta() {
		
		return HttpStatus.BAD_REQUEST;
	}
}
