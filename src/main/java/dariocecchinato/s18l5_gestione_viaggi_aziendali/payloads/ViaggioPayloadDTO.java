package dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads;

import java.time.LocalDate;

public record ViaggioPayloadDTO(
        String destinazione,
        LocalDate dataViaggio,
        String statoPrenotazione
) {}
