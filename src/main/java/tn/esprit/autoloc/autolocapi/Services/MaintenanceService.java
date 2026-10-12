package tn.esprit.autoloc.autolocapi.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.Repositories.MaintenanceRepository;
import tn.esprit.autoloc.autolocapi.Repositories.VehiculeRepository;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class MaintenanceService implements IMaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public Maintenance addMaintenance(Maintenance maintenance, Long idVehicule) {
        Vehicule vehicule = findVehicule(idVehicule);
        checkDates(maintenance.getDateDebut(), maintenance.getDateFin());

        maintenance.setVehicule(vehicule);

        // an ongoing maintenance (no end date) puts the vehicle out of service
        if (maintenance.getDateFin() == null) {
            vehicule.setStatut(StatutVehicule.MAINTENANCE);
            vehiculeRepository.save(vehicule);
        }
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public List<Maintenance> getAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance getMaintenanceById(Long idMaintenance) {
        return maintenanceRepository.findById(idMaintenance)
                .orElseThrow(() -> new EntityNotFoundException("Maintenance not found with id " + idMaintenance));
    }

    @Override
    public Maintenance updateMaintenance(Long idMaintenance, Maintenance maintenance) {
        Maintenance existing = getMaintenanceById(idMaintenance);
        checkDates(maintenance.getDateDebut(), maintenance.getDateFin());

        existing.setDateDebut(maintenance.getDateDebut());
        existing.setDateFin(maintenance.getDateFin());
        existing.setDescription(maintenance.getDescription());
        return maintenanceRepository.save(existing);
    }

    @Override
    public void deleteMaintenance(Long idMaintenance) {
        Maintenance existing = getMaintenanceById(idMaintenance);
        maintenanceRepository.delete(existing);
    }

    @Override
    @Transactional
    public Maintenance terminerMaintenance(Long idMaintenance, LocalDate dateFin) {
        Maintenance maintenance = getMaintenanceById(idMaintenance);
        checkDates(maintenance.getDateDebut(), dateFin);

        maintenance.setDateFin(dateFin);

        Vehicule vehicule = maintenance.getVehicule();
        vehicule.setStatut(StatutVehicule.DISPONIBLE);
        vehiculeRepository.save(vehicule);

        return maintenanceRepository.save(maintenance);
    }

    @Override
    public List<Maintenance> getMaintenancesByVehicule(Long idVehicule) {
        findVehicule(idVehicule);
        return maintenanceRepository.findByVehicule_IdVehicule(idVehicule);
    }

    private Vehicule findVehicule(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found with id " + idVehicule));
    }

    private void checkDates(LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut == null) {
            throw new IllegalArgumentException("dateDebut is required");
        }
        if (dateFin != null && dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("dateFin cannot be before dateDebut");
        }
    }
}