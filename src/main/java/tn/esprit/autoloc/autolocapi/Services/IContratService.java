package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Contrat;

import java.util.List;

public interface IContratService {

    Contrat addContrat(Contrat contrat);

    List<Contrat> getAllContrats();

    Contrat getContratById(Long idContrat);

    Contrat updateContrat(Contrat contrat);

    void deleteContrat(Long idContrat);

    void affecterContratAReservation(Long idContrat, Long idReservation);
    void desaffecterContratDeReservation(Long idReservation);
}