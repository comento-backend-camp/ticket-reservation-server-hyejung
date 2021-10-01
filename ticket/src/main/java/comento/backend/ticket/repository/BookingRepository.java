package comento.backend.ticket.repository;

import comento.backend.ticket.domain.Booking;
import comento.backend.ticket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByUser(User user);
}
