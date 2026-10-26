package tn.esprit.autoloc.autolocapi.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.Services.IPaiementService;
import tn.esprit.autoloc.autolocapi.domain.Paiement;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@AllArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    @PostMapping
    public Paiement addPaiement(@RequestBody Paiement paiement) {
        return paiementService.addPaiement(paiement);
    }

    @GetMapping
    public List<Paiement> getAllPaiements() {
        return paiementService.getAllPaiements();
    }

    @GetMapping("/{id}")
    public Paiement getPaiementById(@PathVariable Long id) {
        return paiementService.getPaiementById(id);
    }

    @PutMapping
    public Paiement updatePaiement(@RequestBody Paiement paiement) {
        return paiementService.updatePaiement(paiement);
    }

    @DeleteMapping("/{id}")
    public void deletePaiement(@PathVariable Long id) {
        paiementService.deletePaiement(id);
    }

    @PutMapping("/{idPaiement}/contrat/{idContrat}")
    public void affecterPaiementAContrat(@PathVariable Long idPaiement, @PathVariable Long idContrat) {
        paiementService.affecterPaiementAContrat(idPaiement, idContrat);
    }
}