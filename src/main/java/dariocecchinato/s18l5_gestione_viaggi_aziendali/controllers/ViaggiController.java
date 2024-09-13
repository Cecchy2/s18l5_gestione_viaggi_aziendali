package dariocecchinato.s18l5_gestione_viaggi_aziendali.controllers;

import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Viaggio;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.BadRequestException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads.ViaggioResponseDTO;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads.ViaggioPayloadDTO;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.services.ViaggiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/viaggi")
public class ViaggiController {
    @Autowired
    private ViaggiService viaggiService;

    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    public ViaggioResponseDTO saveViaggio(@RequestBody @Validated ViaggioPayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()){
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono errore con il payload " + message);
        }else{
            return new ViaggioResponseDTO(this.viaggiService.save(body).getId());
        }
    }

    @GetMapping
    public Page<Viaggio> findAllViaggi(@RequestParam(defaultValue = "0")int page,
                                       @RequestParam(defaultValue = "10")int size,
                                       @RequestParam(defaultValue = "destinazione")String sortby){
        return this.viaggiService.findAll(page, size, sortby);
    }
}
