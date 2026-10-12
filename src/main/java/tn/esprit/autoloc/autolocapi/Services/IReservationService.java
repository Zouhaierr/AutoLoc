package tn.esprit.autoloc.autolocapi.Services;

import tn.esprit.autoloc.autolocapi.domain.Reservation;

import java.util.List;

public interface IReservationService {

    Reservation addReservation(Reservation reservation, Long idClient, Long idVehicule);

    List<Reservation> getAllReservations();

    Reservation getReservationById(Long idReservation);

    Reservation updateReservation(Long idReservation, Reservation reservation);

    void deleteReservation(Long idReservation);

    Reservation confirmerReservation(Long idReservation);

    Reservation annulerReservation(Long idReservation);

    Reservation terminerReservation(Long idReservation);

    List<Reservation> getReservationsByClient(Long idClient);

    List<Reservation> getReservationsByVehicule(Long idVehicule);
}