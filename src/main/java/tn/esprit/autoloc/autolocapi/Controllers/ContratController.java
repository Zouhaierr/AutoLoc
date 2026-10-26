package tn.esprit.autoloc.autolocapi.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.Services.IContratService;
import tn.esprit.autoloc.autolocapi.domain.Contrat;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@AllArgsConstructor
public class ContratController {

    private final IContratService contratService;

    @PostMapping
    public Contrat addContrat(@RequestBody Contrat contrat) {
        return contratService.addContrat(contrat);
    }

    @GetMapping
    public List<Contrat> getAllContrats() {
        return contratService.getAllContrats();
    }

    @GetMapping("/{id}")
    public Contrat getContratById(@PathVariable Long id) {
        return contratService.getContratById(id);
    }

    @PutMapping
    public Contrat updateContrat(@RequestBody Contrat contrat) {
        return contratService.updateContrat(contrat);
    }

    @DeleteMapping("/{id}")
    public void deleteContrat(@PathVariable Long id) {
        contratService.deleteContrat(id);
    }

    @PutMapping("/{idContrat}/reservation/{idReservation}")
    public void affecterContratAReservation(@PathVariable Long idContrat, @PathVariable Long idReservation) {
        contratService.affecterContratAReservation(idContrat, idReservation);
    }

    @DeleteMapping("/reservation/{idReservation}")
    public void desaffecterContratDeReservation(@PathVariable Long idReservation) {
        contratService.desaffecterContratDeReservation(idReservation);
    }
}