package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {

    Vehicule addVehicule(Vehicule vehicule);

    List<Vehicule> getAllVehicules();

    Vehicule getVehiculeById(Long idVehicule);

    Vehicule updateVehicule(Vehicule vehicule);

    void deleteVehicule(Long idVehicule);

    void affecterVehiculeAAgence(Long idVehicule, Long idAgence);
    void desaffecterVehiculeDAgence(Long idVehicule);
    void affecterEquipementAVehicule(Long idEquipement, Long idVehicule);
    void desaffecterEquipementDeVehicule(Long idEquipement, Long idVehicule);
}