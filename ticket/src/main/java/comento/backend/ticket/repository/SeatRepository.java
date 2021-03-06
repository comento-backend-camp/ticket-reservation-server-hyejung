package comento.backend.ticket.repository;

import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.emum.SeatType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findByPerformance(Performance performance);

	Optional<Seat> findByPerformanceAndSeatTypeAndSeatNumber(Performance performance, SeatType seatType,
		Integer seatNumber);
}
