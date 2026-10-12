package tn.esprit.autoloc.autolocapi.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Repositories.ReservationRepository;
import tn.esprit.autoloc.autolocapi.domain.Reservation;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Long idReservation) {
        return reservationRepository.findById(idReservation).orElse(null);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long idReservation) {
        reservationRepository.deleteById(idReservation);
    }
}