package tn.esprit.autoloc.autolocapi.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.Repositories.AgenceRepository;
import tn.esprit.autoloc.autolocapi.Repositories.EquipementRepository;
import tn.esprit.autoloc.autolocapi.Repositories.VehiculeRepository;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.util.List;

@Service
@AllArgsConstructor
public class VehiculeService implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final EquipementRepository equipementRepository;

    @Override
    public Vehicule addVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule getVehiculeById(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule).orElse(null);
    }

    @Override
    public Vehicule updateVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public void deleteVehicule(Long idVehicule) {
        vehiculeRepository.deleteById(idVehicule);
    }

    @Override
    public void affecterVehiculeAAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule).orElseThrow();
        Agence agence = agenceRepository.findById(idAgence).orElseThrow();
        vehicule.setAgence(agence);
        vehiculeRepository.save(vehicule);
    }

    @Override
    public void desaffecterVehiculeDAgence(Long idVehicule) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule).orElseThrow();
        vehicule.setAgence(null);
        vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public void affecterEquipementAVehicule(Long idEquipement, Long idVehicule) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule).orElseThrow();
        Equipement equipement = equipementRepository.findById(idEquipement).orElseThrow();
        if (!vehicule.getEquipements().contains(equipement)) {
            vehicule.getEquipements().add(equipement);
        }
        vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public void desaffecterEquipementDeVehicule(Long idEquipement, Long idVehicule) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule).orElseThrow();
        vehicule.getEquipements().removeIf(e -> e.getIdEquipement().equals(idEquipement));
        vehiculeRepository.save(vehicule);
    }
}