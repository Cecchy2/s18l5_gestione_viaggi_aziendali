package dariocecchinato.s18l5_gestione_viaggi_aziendali.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private LocalDate dataRichiesta;
    private String dettagli;
    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

}
