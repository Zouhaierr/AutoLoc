package tn.esprit.autoloc.autolocapi.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.autolocapi.Repositories.ClientRepository;
import tn.esprit.autoloc.autolocapi.Repositories.ReservationRepository;
import tn.esprit.autoloc.autolocapi.Repositories.VehiculeRepository;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private static final List<StatutReservation> STATUTS_ACTIFS =
            List.of(StatutReservation.EN_ATTENTE, StatutReservation.CONFIRMEE);

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public Reservation addReservation(Reservation reservation, Long idClient, Long idVehicule) {
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id " + idClient));
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found with id " + idVehicule));

        checkDates(reservation.getDateDebut(), reservation.getDateFin());

        if (vehicule.getStatut() == StatutVehicule.MAINTENANCE) {
            throw new IllegalStateException("Vehicule " + idVehicule + " is under maintenance");
        }
        // -1L: no existing reservation to exclude when creating a new one
        checkDisponibilite(idVehicule, reservation.getDateDebut(), reservation.getDateFin(), -1L);

        reservation.setStatut(StatutReservation.EN_ATTENTE);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Long idReservation) {
        return reservationRepository.findById(idReservation)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id " + idReservation));
    }

    @Override
    @Transactional
    public Reservation updateReservation(Long idReservation, Reservation reservation) {
        Reservation existing = getReservationById(idReservation);

        if (!STATUTS_ACTIFS.contains(existing.getStatut())) {
            throw new IllegalStateException("Only pending or confirmed reservations can be modified");
        }
        checkDates(reservation.getDateDebut(), reservation.getDateFin());
        checkDisponibilite(existing.getVehicule().getIdVehicule(),
                reservation.getDateDebut(), reservation.getDateFin(), idReservation);

        existing.setDateDebut(reservation.getDateDebut());
        existing.setDateFin(reservation.getDateFin());
        return reservationRepository.save(existing);
    }

    @Override
    public void deleteReservation(Long idReservation) {
        Reservation existing = getReservationById(idReservation);
        if (existing.getContrat() != null) {
            throw new IllegalStateException("Cannot delete a reservation that already has a contract");
        }
        reservationRepository.delete(existing);
    }

    @Override
    public Reservation confirmerReservation(Long idReservation) {
        Reservation reservation = getReservationById(idReservation);
        if (reservation.getStatut() != StatutReservation.EN_ATTENTE) {
            throw new IllegalStateException("Only a pending reservation can be confirmed");
        }
        reservation.setStatut(StatutReservation.CONFIRMEE);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation annulerReservation(Long idReservation) {
        Reservation reservation = getReservationById(idReservation);
        if (!STATUTS_ACTIFS.contains(reservation.getStatut())) {
            throw new IllegalStateException("Only a pending or confirmed reservation can be cancelled");
        }
        reservation.setStatut(StatutReservation.ANNULEE);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation terminerReservation(Long idReservation) {
        Reservation reservation = getReservationById(idReservation);
        if (reservation.getStatut() != StatutReservation.CONFIRMEE) {
            throw new IllegalStateException("Only a confirmed reservation can be completed");
        }
        reservation.setStatut(StatutReservation.TERMINEE);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByClient(Long idClient) {
        if (!clientRepository.existsById(idClient)) {
            throw new EntityNotFoundException("Client not found with id " + idClient);
        }
        return reservationRepository.findByClient_IdClient(idClient);
    }

    @Override
    public List<Reservation> getReservationsByVehicule(Long idVehicule) {
        if (!vehiculeRepository.existsById(idVehicule)) {
            throw new EntityNotFoundException("Vehicule not found with id " + idVehicule);
        }
        return reservationRepository.findByVehicule_IdVehicule(idVehicule);
    }

    private void checkDates(LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("dateDebut and dateFin are required");
        }
        if (dateDebut.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("dateDebut cannot be in the past");
        }
        if (dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("dateFin cannot be before dateDebut");
        }
    }

    private void checkDisponibilite(Long idVehicule, LocalDate dateDebut, LocalDate dateFin, Long excludeId) {
        boolean overlap = reservationRepository.existsOverlap(
                idVehicule, STATUTS_ACTIFS, dateDebut, dateFin, excludeId);
        if (overlap) {
            throw new IllegalStateException("Vehicule is already reserved for these dates");
        }
    }
}