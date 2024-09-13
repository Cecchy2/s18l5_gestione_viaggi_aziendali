package dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories;

import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ViaggiRepository extends JpaRepository<Viaggio, UUID> {

Optional<Viaggio> findByDestinazioneAndDataViaggio(String destinazione, LocalDate dataViaggio);
}

