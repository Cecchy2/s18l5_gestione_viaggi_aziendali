package dariocecchinato.s18l5_gestione_viaggi_aziendali.controllers;

import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Dipendente;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Prenotazione;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Viaggio;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.BadRequestException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.NotFoundException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads.PrenotazionePayloadDTO;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories.DipendentiReporitory;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories.ViaggiRepository;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;
    @Autowired
    private ViaggiRepository viaggiRepository;
    @Autowired
    private DipendentiReporitory dipendentiReporitory;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazionePayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()){
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(" ."));
            throw new BadRequestException("Errore nel payload " + message);
        }else{
            return this.prenotazioniService.creaPrenotazione(body);
        }
    }

    @GetMapping
    public Page<Prenotazione> getAllPrenotazioni (@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "id") String sortBy){
        return  this.prenotazioniService.findAll(page, size, sortBy);
    }

    @GetMapping("/{prenotazioneId}")
    public Prenotazione getById(@PathVariable UUID prenotazioneId){
        return this.prenotazioniService.findById(prenotazioneId);
    }

    @PutMapping("/{prenotazioneId}")
    public Prenotazione getByIdAndUpdate(@PathVariable UUID prenotazioneId, @RequestBody @Validated PrenotazionePayloadDTO body){
        Viaggio viaggio = viaggiRepository.findById(body.viaggio_id()).orElseThrow(()-> new NotFoundException(body.viaggio_id()));
        Dipendente dipendente = dipendentiReporitory.findById(body.dipendente_id()).orElseThrow(()-> new NotFoundException(body.dipendente_id()));
        Prenotazione found = this.prenotazioniService.findById(prenotazioneId);
        found.setDettagli(body.dettagli());
        found.setViaggio(viaggio);
        found.setDipendente(dipendente);
        return prenotazioniService.updatePrenotazione(found);

    }
    @DeleteMapping("/{prenotazioneId}")
    public void getByIdAndDelete(@PathVariable UUID prenotazioneId){
        this.prenotazioniService.findByIdAndDelete(prenotazioneId);
    }

}
