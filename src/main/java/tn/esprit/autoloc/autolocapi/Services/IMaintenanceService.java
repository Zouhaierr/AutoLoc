package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Maintenance;

import java.time.LocalDate;
import java.util.List;

public interface IMaintenanceService {

    Maintenance addMaintenance(Maintenance maintenance, Long idVehicule);

    List<Maintenance> getAllMaintenances();

    Maintenance getMaintenanceById(Long idMaintenance);

    Maintenance updateMaintenance(Long idMaintenance, Maintenance maintenance);

    void deleteMaintenance(Long idMaintenance);

    Maintenance terminerMaintenance(Long idMaintenance, LocalDate dateFin);

    List<Maintenance> getMaintenancesByVehicule(Long idVehicule);
}