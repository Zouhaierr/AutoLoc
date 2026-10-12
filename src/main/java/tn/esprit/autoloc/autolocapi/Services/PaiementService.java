package tn.esprit.autoloc.autolocapi.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.Repositories.ContratRepository;
import tn.esprit.autoloc.autolocapi.Repositories.PaiementRepository;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Paiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PaiementService implements IPaiementService {

    private final PaiementRepository paiementRepository;
    private final ContratRepository contratRepository;

    @Override
    @Transactional
    public Paiement addPaiement(Paiement paiement, Long idContrat) {
        Contrat contrat = findContrat(idContrat);
        checkMontant(paiement.getMontant());

        BigDecimal dejaPaye = getTotalPayeByContrat(idContrat);
        checkNotExceeding(contrat, dejaPaye.add(paiement.getMontant()));

        if (paiement.getDatePaiement() == null) {
            paiement.setDatePaiement(LocalDate.now());
        }

        paiement.setContrat(contrat);
        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement getPaiementById(Long idPaiement) {
        return paiementRepository.findById(idPaiement)
                .orElseThrow(() -> new EntityNotFoundException("Paiement not found with id " + idPaiement));
    }

    @Override
    @Transactional
    public Paiement updatePaiement(Long idPaiement, Paiement paiement) {
        Paiement existing = getPaiementById(idPaiement);
        checkMontant(paiement.getMontant());

        // total of the other payments of the same contract, plus the new amount
        Contrat contrat = existing.getContrat();
        BigDecimal autresPaiements = getTotalPayeByContrat(contrat.getIdContrat())
                .subtract(existing.getMontant());
        checkNotExceeding(contrat, autresPaiements.add(paiement.getMontant()));

        existing.setMontant(paiement.getMontant());
        existing.setDatePaiement(paiement.getDatePaiement());
        existing.setModePaiement(paiement.getModePaiement());
        return paiementRepository.save(existing);
    }

    @Override
    public void deletePaiement(Long idPaiement) {
        Paiement existing = getPaiementById(idPaiement);
        paiementRepository.delete(existing);
    }

    @Override
    public List<Paiement> getPaiementsByContrat(Long idContrat) {
        findContrat(idContrat);
        return paiementRepository.findByContrat_IdContrat(idContrat);
    }

    @Override
    public BigDecimal getTotalPayeByContrat(Long idContrat) {
        return paiementRepository.findByContrat_IdContrat(idContrat).stream()
                .map(Paiement::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Contrat findContrat(Long idContrat) {
        return contratRepository.findById(idContrat)
                .orElseThrow(() -> new EntityNotFoundException("Contrat not found with id " + idContrat));
    }

    private void checkMontant(BigDecimal montant) {
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("montant must be greater than 0");
        }
    }

    private void checkNotExceeding(Contrat contrat, BigDecimal totalApresPaiement) {
        if (totalApresPaiement.compareTo(contrat.getMontantTotal()) > 0) {
            throw new IllegalStateException(
                    "Total payments would exceed the contract amount (" + contrat.getMontantTotal() + ")");
        }
    }
}