package dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViaggioPayloadDTO(
        @NotEmpty(message = "Devi inserire una destinazione")
        String destinazione,
        @NotNull(message = "Devi inserire una data")
        LocalDate dataViaggio,
        String statoPrenotazione
) {}
