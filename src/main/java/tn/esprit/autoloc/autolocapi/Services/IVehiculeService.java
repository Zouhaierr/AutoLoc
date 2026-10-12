package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.time.LocalDate;
import java.util.List;

public interface IVehiculeService {

    Vehicule addVehicule(Vehicule vehicule, Long idAgence);

    List<Vehicule> getAllVehicules();

    Vehicule getVehiculeById(Long idVehicule);

    Vehicule updateVehicule(Long idVehicule, Vehicule vehicule);

    void deleteVehicule(Long idVehicule);

    Vehicule changerStatut(Long idVehicule, StatutVehicule statut);

    Vehicule affecterVehiculeToAgence(Long idVehicule, Long idAgence);

    Vehicule ajouterEquipement(Long idVehicule, Long idEquipement);

    Vehicule retirerEquipement(Long idVehicule, Long idEquipement);

    List<Vehicule> getVehiculesByAgence(Long idAgence);

    List<Vehicule> getVehiculesByCategorie(CategorieVehicule categorie);

    List<Vehicule> getVehiculesDisponibles(LocalDate dateDebut, LocalDate dateFin);
}