package tn.esprit.autoloc.autolocapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Equipement;

public interface EquipementRepository extends JpaRepository<Equipement, Long> {
}
