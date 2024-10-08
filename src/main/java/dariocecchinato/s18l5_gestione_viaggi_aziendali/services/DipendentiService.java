package dariocecchinato.s18l5_gestione_viaggi_aziendali.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.entities.Dipendente;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.BadRequestException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.exceptions.NotFoundException;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.payloads.DipendentePayloadDTO;
import dariocecchinato.s18l5_gestione_viaggi_aziendali.repositories.DipendentiReporitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class DipendentiService {
    @Autowired
    private DipendentiReporitory dipendentiReporitory;
    @Autowired
    private Cloudinary cloudinaryUploader;



    public Page<Dipendente> findAll(int page, int size, String sortby){
        if (page > 10) page = 10;
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortby));
        return dipendentiReporitory.findAll(pageable);
    }

    public Dipendente save (DipendentePayloadDTO body){
        if (dipendentiReporitory.existsByEmail(body.email()))throw new BadRequestException("L' email " + body.email() + " è già in uso");
        String avatar = "https://ui-avatars.com/api/?name="+body.nome()+"+"+body.cognome();
        Dipendente newDipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email(), avatar);
        return dipendentiReporitory.save(newDipendente);
    }
    public Dipendente findDipendenteById(UUID dipendenteId){
        Dipendente found = this.dipendentiReporitory.findById(dipendenteId).orElseThrow(()->new NotFoundException(dipendenteId));
        if (found == null)throw new NotFoundException(dipendenteId);
        return found;
    }

    public Dipendente findByIdAndUpdate(UUID dipendenteId, DipendentePayloadDTO body){
        String avatar = "https://ui-avatars.com/api/?name="+body.nome()+"+"+body.cognome();
        Dipendente found = this.dipendentiReporitory.findById(dipendenteId).orElseThrow(()->new NotFoundException(dipendenteId));
        if (found == null)throw new NotFoundException(dipendenteId);
        found.setAvatar(avatar);
        found.setUsername(body.username());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());
        return dipendentiReporitory.save(found);
    }
    public void findByIdAndDeleteDipendente (UUID dipendenteId){
        Dipendente found = this.dipendentiReporitory.findById(dipendenteId).orElseThrow(()->new NotFoundException(dipendenteId));
        if (found == null)throw new NotFoundException(dipendenteId);
        this.dipendentiReporitory.delete(found);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("URL " + url);
        return url;
    }

    public Dipendente saveDipendente(Dipendente dipendente) {
        return this.dipendentiReporitory.save(dipendente);
    }
}
