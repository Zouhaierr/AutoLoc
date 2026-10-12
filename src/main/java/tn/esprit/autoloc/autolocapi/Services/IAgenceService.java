package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Agence;

import java.util.List;

public interface IAgenceService {

    Agence addAgence(Agence agence);

    List<Agence> getAllAgences();

    Agence getAgenceById(Long idAgence);

    Agence updateAgence(Long idAgence, Agence agence);

    void deleteAgence(Long idAgence);
}