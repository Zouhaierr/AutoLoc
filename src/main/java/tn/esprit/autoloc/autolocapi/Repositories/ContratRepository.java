package tn.esprit.autoloc.autolocapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Agence;

public interface ContratRepository extends JpaRepository<Agence, Long> {
    boolean existsByReservation_IdReservation(Long idReservation);
}
