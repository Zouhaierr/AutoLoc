package tn.esprit.autoloc.autolocapi.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Repositories.AgenceRepository;
import tn.esprit.autoloc.autolocapi.domain.Agence;

import java.util.List;

@Service
@AllArgsConstructor
public class AgenceService implements IAgenceService {

    private final AgenceRepository agenceRepository;

    @Override
    public Agence addAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence getAgenceById(Long idAgence) {
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence not found with id " + idAgence));
    }

    @Override
    public Agence updateAgence(Long idAgence, Agence agence) {
        Agence existing = getAgenceById(idAgence);
        existing.setNom(agence.getNom());
        existing.setVille(agence.getVille());
        existing.setAdresse(agence.getAdresse());
        existing.setTelephone(agence.getTelephone());
        return agenceRepository.save(existing);
    }

    @Override
    public void deleteAgence(Long idAgence) {
        Agence existing = getAgenceById(idAgence);
        agenceRepository.delete(existing);
    }
}