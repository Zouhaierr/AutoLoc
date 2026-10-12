package tn.esprit.autoloc.autolocapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import org.springframework.stereotype.Repository;
@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {

}
