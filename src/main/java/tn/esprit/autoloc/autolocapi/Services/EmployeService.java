package tn.esprit.autoloc.autolocapi.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Repositories.AgenceRepository;
import tn.esprit.autoloc.autolocapi.Repositories.EmployeRepository;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Employe;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeService implements IEmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceRepository agenceRepository;

    @Override
    public Employe addEmploye(Employe employe, Long idAgence) {
        if (idAgence != null) {
            employe.setAgence(findAgence(idAgence));
        }
        return employeRepository.save(employe);
    }

    @Override
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public Employe getEmployeById(Long idEmploye) {
        return employeRepository.findById(idEmploye)
                .orElseThrow(() -> new EntityNotFoundException("Employe not found with id " + idEmploye));
    }

    @Override
    public Employe updateEmploye(Long idEmploye, Employe employe) {
        Employe existing = getEmployeById(idEmploye);
        existing.setNom(employe.getNom());
        existing.setPrenom(employe.getPrenom());
        existing.setRole(employe.getRole());
        return employeRepository.save(existing);
    }

    @Override
    public void deleteEmploye(Long idEmploye) {
        Employe existing = getEmployeById(idEmploye);
        employeRepository.delete(existing);
    }

    @Override
    public Employe affecterEmployeToAgence(Long idEmploye, Long idAgence) {
        Employe employe = getEmployeById(idEmploye);
        employe.setAgence(findAgence(idAgence));
        return employeRepository.save(employe);
    }

    @Override
    public List<Employe> getEmployesByAgence(Long idAgence) {
        findAgence(idAgence); // throws a clear error if the agency doesn't exist
        return employeRepository.findByAgence_IdAgence(idAgence);
    }

    private Agence findAgence(Long idAgence) {
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence not found with id " + idAgence));
    }
}