package tn.esprit.autoloc.autolocapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByAgence_IdAgence(Long idAgence);

    boolean existsByImmatriculationAndIdVehiculeNot(String immatriculation, Long idVehicule);
}
