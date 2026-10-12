package tn.esprit.autoloc.autolocapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.autolocapi.domain.Paiement;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}