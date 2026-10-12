package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {

    Vehicule addVehicule(Vehicule vehicule);

    List<Vehicule> getAllVehicules();

    Vehicule getVehiculeById(Long idVehicule);

    Vehicule updateVehicule(Vehicule vehicule);

    void deleteVehicule(Long idVehicule);
}