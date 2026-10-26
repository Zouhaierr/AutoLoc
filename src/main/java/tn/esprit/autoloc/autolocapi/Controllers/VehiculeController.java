package tn.esprit.autoloc.autolocapi.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.Services.IVehiculeService;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@AllArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @PostMapping
    public Vehicule addVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.addVehicule(vehicule);
    }

    @GetMapping
    public List<Vehicule> getAllVehicules() {
        return vehiculeService.getAllVehicules();
    }

    @GetMapping("/{id}")
    public Vehicule getVehiculeById(@PathVariable Long id) {
        return vehiculeService.getVehiculeById(id);
    }

    @PutMapping
    public Vehicule updateVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.updateVehicule(vehicule);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicule(@PathVariable Long id) {
        vehiculeService.deleteVehicule(id);
    }

    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public void affecterVehiculeAAgence(@PathVariable Long idVehicule, @PathVariable Long idAgence) {
        vehiculeService.affecterVehiculeAAgence(idVehicule, idAgence);
    }

    @DeleteMapping("/{idVehicule}/agence")
    public void desaffecterVehiculeDAgence(@PathVariable Long idVehicule) {
        vehiculeService.desaffecterVehiculeDAgence(idVehicule);
    }

    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    public void affecterEquipementAVehicule(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        vehiculeService.affecterEquipementAVehicule(idEquipement, idVehicule);
    }

    @DeleteMapping("/{idVehicule}/equipements/{idEquipement}")
    public void desaffecterEquipementDeVehicule(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        vehiculeService.desaffecterEquipementDeVehicule(idEquipement, idVehicule);
    }
}