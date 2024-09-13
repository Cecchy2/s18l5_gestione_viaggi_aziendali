package dariocecchinato.s18l5_gestione_viaggi_aziendali.services;

import dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViaggiService {
    @Autowired
    private ViaggiRepository viaggiRepository;


}
