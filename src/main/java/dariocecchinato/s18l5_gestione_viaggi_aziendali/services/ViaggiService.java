package dariocecchinato.s18l5_gestione_viaggi_aziendali.services;

import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Viaggio;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.enums.Stato_Prenotazione;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.BadRequestException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.NotFoundException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.NotFoundExceptionViaggio;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads.ViaggioPayloadDTO;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads.ViaggioResponseDTO;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ViaggiService {
    @Autowired
    private ViaggiRepository viaggiRepository;

public Viaggio save (ViaggioPayloadDTO body){
    if (viaggiRepository.findByDestinazioneAndDataViaggio(body.destinazione(), body.dataViaggio()).isPresent()){
        throw new BadRequestException("Il viaggio per " + body.destinazione() + " è già stato programmato" );
    } else {
        Stato_Prenotazione stato_prenotazione= Stato_Prenotazione.valueOf(body.statoPrenotazione());
        Viaggio newViaggio = new Viaggio(body.destinazione(), body.dataViaggio(),stato_prenotazione);
        return  this.viaggiRepository.save(newViaggio);
    }
}

public Page<Viaggio> findAll(int page, int size , String sortby){
    if (page > 10) page = 10;
    Pageable pageable= PageRequest.of(page, size, Sort.by(sortby));
    return this.viaggiRepository.findAll(pageable);
}

public Viaggio findById (UUID viaggioId){
    if (viaggioId == null) throw new BadRequestException("Devi inserire un id ");
    return viaggiRepository.findById(viaggioId).orElseThrow(()-> new NotFoundException(viaggioId));
}

    public Viaggio findByDestinationAndDate(String destinazione, LocalDate dataViaggio) {
        return this.viaggiRepository.findByDestinazioneAndDataViaggio(destinazione, dataViaggio)
                .orElseThrow(() -> new NotFoundExceptionViaggio(destinazione, dataViaggio)); // Nome corretto e senza virgola
    }


    public Viaggio findByIdAndUpdate (UUID viaggioId, ViaggioPayloadDTO body){
    Viaggio found = this.viaggiRepository.findById(viaggioId)
            .orElseThrow(() -> new NotFoundException(viaggioId));
    found.setDestinazione(body.destinazione());
    found.setDataViaggio(body.dataViaggio());
    found.setStatoPrenotazione(Stato_Prenotazione.valueOf(body.statoPrenotazione()));
    return viaggiRepository.save(found);
    }

    public void findByIdAndDelete(UUID viaggioId){
    Viaggio found =this.viaggiRepository.findById(viaggioId)
            .orElseThrow(() -> new NotFoundException(viaggioId));
    this.viaggiRepository.delete(found);

    }


}
