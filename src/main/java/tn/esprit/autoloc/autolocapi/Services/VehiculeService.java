package tn.esprit.autoloc.autolocapi.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Repositories.VehiculeRepository;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.util.List;

@Service
@AllArgsConstructor
public class VehiculeService implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;

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
}