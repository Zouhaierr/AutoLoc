package tn.esprit.autoloc.autolocapi.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.Repositories.AgenceRepository;
import tn.esprit.autoloc.autolocapi.Repositories.EquipementRepository;
import tn.esprit.autoloc.autolocapi.Repositories.MaintenanceRepository;
import tn.esprit.autoloc.autolocapi.Repositories.ReservationRepository;
import tn.esprit.autoloc.autolocapi.Repositories.VehiculeRepository;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class VehiculeService implements IVehiculeService {

    private static final List<StatutReservation> STATUTS_ACTIFS =
            List.of(StatutReservation.EN_ATTENTE, StatutReservation.CONFIRMEE);

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final EquipementRepository equipementRepository;
    private final ReservationRepository reservationRepository;
    private final MaintenanceRepository maintenanceRepository;

    @Override
    public Vehicule addVehicule(Vehicule vehicule, Long idAgence) {
        checkTarif(vehicule.getTarifJournalier());

        if (vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation())) {
            throw new IllegalStateException(
                    "A vehicle with registration " + vehicule.getImmatriculation() + " already exists");
        }
        if (idAgence != null) {
            vehicule.setAgence(findAgence(idAgence));
        }
        if (vehicule.getStatut() == null) {
            vehicule.setStatut(StatutVehicule.DISPONIBLE);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule getVehiculeById(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found with id " + idVehicule));
    }

    @Override
    public Vehicule updateVehicule(Long idVehicule, Vehicule vehicule) {
        Vehicule existing = getVehiculeById(idVehicule);
        checkTarif(vehicule.getTarifJournalier());

        if (vehiculeRepository.existsByImmatriculationAndIdVehiculeNot(
                vehicule.getImmatriculation(), idVehicule)) {
            throw new IllegalStateException(
                    "A vehicle with registration " + vehicule.getImmatriculation() + " already exists");
        }

        existing.setImmatriculation(vehicule.getImmatriculation());
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setCategorie(vehicule.getCategorie());
        existing.setTarifJournalier(vehicule.getTarifJournalier());
        return vehiculeRepository.save(existing);
    }

    @Override
    public void deleteVehicule(Long idVehicule) {
        Vehicule existing = getVehiculeById(idVehicule);

        if (!reservationRepository.findByVehicule_IdVehicule(idVehicule).isEmpty()) {
            throw new IllegalStateException("Cannot delete a vehicle that has reservations");
        }
        if (!maintenanceRepository.findByVehicule_IdVehicule(idVehicule).isEmpty()) {
            throw new IllegalStateException("Cannot delete a vehicle that has maintenance records");
        }
        vehiculeRepository.delete(existing);
    }

    @Override
    public Vehicule changerStatut(Long idVehicule, StatutVehicule statut) {
        Vehicule vehicule = getVehiculeById(idVehicule);
        vehicule.setStatut(statut);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule affecterVehiculeToAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = getVehiculeById(idVehicule);
        vehicule.setAgence(findAgence(idAgence));
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule ajouterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getVehiculeById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new EntityNotFoundException("Equipement not found with id " + idEquipement));

        if (!vehicule.getEquipements().contains(equipement)) {
            vehicule.getEquipements().add(equipement);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule retirerEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getVehiculeById(idVehicule);
        boolean removed = vehicule.getEquipements()
                .removeIf(e -> e.getIdEquipement().equals(idEquipement));

        if (!removed) {
            throw new EntityNotFoundException(
                    "Equipement " + idEquipement + " is not assigned to vehicule " + idVehicule);
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> getVehiculesByAgence(Long idAgence) {
        findAgence(idAgence);
        return vehiculeRepository.findByAgence_IdAgence(idAgence);
    }

    @Override
    public List<Vehicule> getVehiculesByCategorie(CategorieVehicule categorie) {
        return vehiculeRepository.findByCategorie(categorie);
    }

    @Override
    public List<Vehicule> getVehiculesDisponibles(LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut == null || dateFin == null || dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("Invalid date range");
        }
        return vehiculeRepository.findDisponibles(
                StatutVehicule.MAINTENANCE, STATUTS_ACTIFS, dateDebut, dateFin);
    }

    private Agence findAgence(Long idAgence) {
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence not found with id " + idAgence));
    }

    private void checkTarif(BigDecimal tarif) {
        if (tarif == null || tarif.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("tarifJournalier must be greater than 0");
        }
    }
}