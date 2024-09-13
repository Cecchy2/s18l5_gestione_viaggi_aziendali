package dariocecchinato.s18l5_gestione_viaggi_aziendali.services;

import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Dipendente;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Prenotazione;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Viaggio;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.BadRequestException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.NotFoundException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads.PrenotazionePayloadDTO;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories.DipendentiReporitory;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories.PrenotazioniRepository;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class PrenotazioniService {
    @Autowired
    private PrenotazioniRepository prenotazioniRepository;
    @Autowired
    private ViaggiRepository viaggiRepository;
    @Autowired
    private DipendentiReporitory dipendentiReporitory;

    public Prenotazione creaPrenotazione(PrenotazionePayloadDTO body){
        UUID dipendenteId = body.dipendente_id();
        Viaggio viaggio = viaggiRepository.findById(body.viaggio_id()).orElseThrow(()-> new NotFoundException(body.viaggio_id()));
        LocalDate dataViaggio = viaggio.getDataViaggio();

        if (prenotazioniRepository.existsByDipendenteIdAndViaggioDataViaggio(dipendenteId,dataViaggio)){
            throw new BadRequestException("Il dipendente " + body.dipendente_id() + " ha già un viaggio prenotato per quella data");
        }else{

        Dipendente dipendente = dipendentiReporitory.findById(body.dipendente_id()).orElseThrow(()->new NotFoundException(body.dipendente_id()));
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDataRichiesta(LocalDate.now());
        prenotazione.setDettagli(body.dettagli());
        prenotazione.setViaggio(viaggio);
        prenotazione.setDipendente(dipendente);

        return prenotazioniRepository.save(prenotazione);
        }
    }

    public Page<Prenotazione> findAll (int page, int size, String sortby){
        if (page > 10) page =10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby));
        return this.prenotazioniRepository.findAll(pageable);
    }

    public Prenotazione findById(UUID prenotazioneId){
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(()->new NotFoundException(prenotazioneId));
    }

    public Prenotazione updatePrenotazione(Prenotazione prenotazione) {
        return prenotazioniRepository.save(prenotazione);
    }

    public void findByIdAndDelete(UUID prenotazioneId){
        Prenotazione found = this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(()-> new NotFoundException(prenotazioneId));
        this.prenotazioniRepository.delete(found);
    }


}
