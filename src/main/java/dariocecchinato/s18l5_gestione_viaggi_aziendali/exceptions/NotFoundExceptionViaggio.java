package dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions;

import java.time.LocalDate;

public class NotFoundExceptionViaggio extends RuntimeException {

    public NotFoundExceptionViaggio(String destinazione, LocalDate dataViaggio) {
        super("Il viaggio per " + destinazione + " del " + dataViaggio + " non è stato trovato.");
    }
}