package tn.esprit.autoloc.autolocapi.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Repositories.ContratRepository;
import tn.esprit.autoloc.autolocapi.Repositories.ReservationRepository;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ContratService implements IContratService {

    private final ContratRepository contratRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public Contrat addContrat(Contrat contrat, Long idReservation) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id " + idReservation));

        if (contratRepository.existsByReservation_IdReservation(idReservation)) {
            throw new IllegalStateException("A contract already exists for reservation " + idReservation);
        }

        if (contrat.getDateSignature() == null) {
            contrat.setDateSignature(LocalDate.now());
        }

        contrat.setReservation(reservation);
        return contratRepository.save(contrat);
    }

    @Override
    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat getContratById(Long idContrat) {
        return contratRepository.findById(idContrat)
                .orElseThrow(() -> new EntityNotFoundException("Contrat not found with id " + idContrat));
    }

    @Override
    public Contrat updateContrat(Long idContrat, Contrat contrat) {
        Contrat existing = getContratById(idContrat);
        existing.setDateSignature(contrat.getDateSignature());
        existing.setMontantTotal(contrat.getMontantTotal());
        existing.setValide(contrat.isValide());
        return contratRepository.save(existing);
    }

    @Override
    public void deleteContrat(Long idContrat) {
        Contrat existing = getContratById(idContrat);
        contratRepository.delete(existing);
    }
}