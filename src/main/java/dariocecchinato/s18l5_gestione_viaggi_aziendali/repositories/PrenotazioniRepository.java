package dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories;

import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {

    boolean existsByDipendenteIdAndViaggioDataViaggio(UUID dipendenteId, LocalDate dataViaggio);
}
