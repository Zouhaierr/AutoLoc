package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Employe;

import java.util.List;

public interface IEmployeService {

    Employe addEmploye(Employe employe);

    List<Employe> getAllEmployes();

    Employe getEmployeById(Long idEmploye);

    Employe updateEmploye(Employe employe);

    void deleteEmploye(Long idEmploye);
}