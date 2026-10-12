package tn.esprit.autoloc.autolocapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.autolocapi.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
