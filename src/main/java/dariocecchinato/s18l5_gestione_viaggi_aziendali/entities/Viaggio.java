package dariocecchinato.s18l5_gestione_viaggi_aziendali.entities;

import dariocecchinato.s18l5_gestione_viaggi_aziendali.enums.Stato_Prenotazione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private  String destinazione;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Stato_Prenotazione stato_prenotazione;

    public Viaggio(String destinazione, LocalDate data, Stato_Prenotazione stato_prenotazione) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato_prenotazione = stato_prenotazione;
    }
}
