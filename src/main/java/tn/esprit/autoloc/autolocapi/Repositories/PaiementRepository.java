package tn.esprit.autoloc.autolocapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Paiement;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Equipement, Long> {
    List<Paiement> findByContrat_IdContrat(Long idContrat);
}
