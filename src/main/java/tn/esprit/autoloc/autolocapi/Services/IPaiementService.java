package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Paiement;

import java.util.List;

public interface IPaiementService {

    Paiement addPaiement(Paiement paiement);

    List<Paiement> getAllPaiements();

    Paiement getPaiementById(Long idPaiement);

    Paiement updatePaiement(Paiement paiement);

    void deletePaiement(Long idPaiement);
}