package comento.backend.ticket.repository;

import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.dto.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByPerformance(Performance performance);
    Optional<Seat> findByPerformanceAndSeatTypeAndSeatNumber(Performance performance, SeatType seatType, Integer seatNumber);
}
