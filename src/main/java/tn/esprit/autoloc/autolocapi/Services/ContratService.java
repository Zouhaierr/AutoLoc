package tn.esprit.autoloc.autolocapi.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Repositories.ContratRepository;
import tn.esprit.autoloc.autolocapi.Repositories.ReservationRepository;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Reservation;

import java.util.List;

@Service
@AllArgsConstructor
public class ContratService implements IContratService {

    private final ContratRepository contratRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public Contrat addContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat getContratById(Long idContrat) {
        return contratRepository.findById(idContrat).orElse(null);
    }

    @Override
    public Contrat updateContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public void deleteContrat(Long idContrat) {
        contratRepository.deleteById(idContrat);
    }

    @Override
    public void affecterContratAReservation(Long idContrat, Long idReservation) {
        Contrat contrat = contratRepository.findById(idContrat).orElseThrow();
        Reservation reservation = reservationRepository.findById(idReservation).orElseThrow();
        contrat.setReservation(reservation);
        contratRepository.save(contrat);
    }

    @Override
    public void desaffecterContratDeReservation(Long idReservation) {
        Reservation reservation = reservationRepository.findById(idReservation).orElseThrow();
        Contrat contrat = reservation.getContrat();
        if (contrat != null) {
            contrat.setReservation(null);
            contratRepository.save(contrat);
        }
    }
}