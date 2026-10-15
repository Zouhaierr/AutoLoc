package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Maintenance;

import java.util.List;

public interface IMaintenanceService {

    Maintenance addMaintenance(Maintenance maintenance);

    List<Maintenance> getAllMaintenances();

    Maintenance getMaintenanceById(Long idMaintenance);

    Maintenance updateMaintenance(Maintenance maintenance);

    void deleteMaintenance(Long idMaintenance);
}