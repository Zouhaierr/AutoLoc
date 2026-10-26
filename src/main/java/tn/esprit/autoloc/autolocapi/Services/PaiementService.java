package tn.esprit.autoloc.autolocapi.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Repositories.ContratRepository;
import tn.esprit.autoloc.autolocapi.Repositories.PaiementRepository;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Paiement;

import java.util.List;

@Service
@AllArgsConstructor
public class PaiementService implements IPaiementService {

    private final PaiementRepository paiementRepository;
    private final ContratRepository contratRepository;

    @Override
    public Paiement addPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement getPaiementById(Long idPaiement) {
        return paiementRepository.findById(idPaiement).orElse(null);
    }

    @Override
    public Paiement updatePaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public void deletePaiement(Long idPaiement) {
        paiementRepository.deleteById(idPaiement);
    }

    @Override
    public void affecterPaiementAContrat(Long idPaiement, Long idContrat) {
        Paiement paiement = paiementRepository.findById(idPaiement).orElseThrow();
        Contrat contrat = contratRepository.findById(idContrat).orElseThrow();
        paiement.setContrat(contrat);
        paiementRepository.save(paiement);
    }
}