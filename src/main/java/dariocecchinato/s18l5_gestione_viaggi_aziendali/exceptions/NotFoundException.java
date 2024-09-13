package dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{
	public NotFoundException(UUID id){
		super("L'elemento con id " + id + " non è stato trovato!");
	}
}
