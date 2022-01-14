package comento.backend.ticket.repository;

import comento.backend.ticket.domain.Performance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
	List<Performance> findByStartDateGreaterThanEqualOrderByStartDateAsc(Date startDate);

	List<Performance> findByTitleAndStartDateGreaterThanEqualOrderByStartDate(String title, Date startDate);

	Optional<Performance> findByIdAndTitle(Long id, String title);
}
