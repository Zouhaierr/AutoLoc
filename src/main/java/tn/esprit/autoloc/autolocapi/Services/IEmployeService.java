package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Employe;

import java.util.List;

public interface IEmployeService {

    Employe addEmploye(Employe employe, Long idAgence);

    List<Employe> getAllEmployes();

    Employe getEmployeById(Long idEmploye);

    Employe updateEmploye(Long idEmploye, Employe employe);

    void deleteEmploye(Long idEmploye);

    Employe affecterEmployeToAgence(Long idEmploye, Long idAgence);

    List<Employe> getEmployesByAgence(Long idAgence);
}