package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Paiement;

import java.math.BigDecimal;
import java.util.List;

public interface IPaiementService {

    Paiement addPaiement(Paiement paiement, Long idContrat);

    List<Paiement> getAllPaiements();

    Paiement getPaiementById(Long idPaiement);

    Paiement updatePaiement(Long idPaiement, Paiement paiement);

    void deletePaiement(Long idPaiement);

    List<Paiement> getPaiementsByContrat(Long idContrat);

    BigDecimal getTotalPayeByContrat(Long idContrat);
}