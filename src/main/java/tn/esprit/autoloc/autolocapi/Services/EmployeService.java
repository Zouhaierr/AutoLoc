package tn.esprit.autoloc.autolocapi.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Repositories.EmployeRepository;
import tn.esprit.autoloc.autolocapi.domain.Employe;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeService implements IEmployeService {

    private final EmployeRepository employeRepository;

    @Override
    public Employe addEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public Employe getEmployeById(Long idEmploye) {
        return employeRepository.findById(idEmploye).orElse(null);
    }

    @Override
    public Employe updateEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public void deleteEmploye(Long idEmploye) {
        employeRepository.deleteById(idEmploye);
    }
}