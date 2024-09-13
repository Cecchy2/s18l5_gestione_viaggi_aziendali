package dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories;

import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DipendentiReporitory extends JpaRepository<Dipendente, UUID> {

    boolean existsByEmail(String email);
    Optional<Dipendente> findByEmail(String email);
}
