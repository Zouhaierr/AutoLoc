package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Contrat;

import java.util.List;

public interface IContratService {

    Contrat addContrat(Contrat contrat, Long idReservation);

    List<Contrat> getAllContrats();

    Contrat getContratById(Long idContrat);

    Contrat updateContrat(Long idContrat, Contrat contrat);

    void deleteContrat(Long idContrat);
}