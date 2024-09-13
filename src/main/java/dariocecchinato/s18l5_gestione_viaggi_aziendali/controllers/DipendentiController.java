package dariocecchinato.s18l5_gestione_viaggi_aziendali.controllers;

import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Dipendente;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.BadRequestException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.NotFoundException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads.DipendentePayloadDTO;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads.DipendenteResponseDTO;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {
    @Autowired
    private DipendentiService dipendentiService;

            @GetMapping
    public Page<Dipendente> findAllDipendenti(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "nome")String sortby){
        return this.dipendentiService.findAll(page, size, sortby);
            }

            @PostMapping
            @ResponseStatus(HttpStatus.CREATED)
    public DipendenteResponseDTO saveDipendente(@RequestBody @Validated DipendentePayloadDTO body, BindingResult validationResult){
                if (validationResult.hasErrors()){
                    String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
                    throw new BadRequestException("Ci sono errore con il payload " + message);
                }else {
                    return new DipendenteResponseDTO(this.dipendentiService.save(body).getId());
                }
            }
            @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable UUID dipendenteId){
                Dipendente found = this.dipendentiService.findDipendenteById(dipendenteId);
                if (found == null )throw new NotFoundException(dipendenteId);
                return found;
            }
            @PutMapping("/{dipendenteId}")
    public Dipendente findByIdAndUpdate(@PathVariable UUID dipendenteId,@RequestBody @Validated DipendentePayloadDTO body, BindingResult validationResult){
                if (validationResult.hasErrors()){
                    String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
                    throw new BadRequestException("Ci sono errore con il payload " + message);
                }else {
                    return this.dipendentiService.findByIdAndUpdate(dipendenteId,body);
                }
            }

            @DeleteMapping("/{dipendenteId}")
            @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID dipendenteId){this.dipendentiService.findByIdAndDeleteDipendente(dipendenteId);}

    @PatchMapping("/{dipendenteId}/avatar")
    public void uploadAvatar(@PathVariable UUID dipendenteId, @RequestParam("avatar") MultipartFile image) throws IOException {
        String avatarUrl = this.dipendentiService.uploadImage(image);
        Dipendente dipendente = dipendentiService.findDipendenteById(dipendenteId);
        dipendente.setAvatar(avatarUrl);
        dipendentiService.saveDipendente(dipendente);
            }
    }




