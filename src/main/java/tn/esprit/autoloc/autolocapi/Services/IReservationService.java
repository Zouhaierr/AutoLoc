package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Reservation;

import java.util.List;

public interface IReservationService {

    Reservation addReservation(Reservation reservation);

    List<Reservation> getAllReservations();

    Reservation getReservationById(Long idReservation);

    Reservation updateReservation(Reservation reservation);

    void deleteReservation(Long idReservation);

    void affecterClientAReservation(Long idClient, Long idReservation);
}