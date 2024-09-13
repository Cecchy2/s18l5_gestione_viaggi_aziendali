package dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions;

public class BadRequestException extends RuntimeException {
	public BadRequestException(String msg){
		super(msg);
	}
}
