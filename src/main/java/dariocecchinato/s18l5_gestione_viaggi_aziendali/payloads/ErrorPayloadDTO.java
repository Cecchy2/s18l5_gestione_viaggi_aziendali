package dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads;

import java.time.LocalDateTime;

public record ErrorPayloadDTO(String message, LocalDateTime errorTime) {
}
