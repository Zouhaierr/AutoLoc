package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Equipement;

import java.util.List;

public interface IEquipementService {

    Equipement addEquipement(Equipement equipement);

    List<Equipement> getAllEquipements();

    Equipement getEquipementById(Long idEquipement);

    Equipement updateEquipement(Equipement equipement);

    void deleteEquipement(Long idEquipement);
}