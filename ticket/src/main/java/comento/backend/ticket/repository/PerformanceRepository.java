package comento.backend.ticket.repository;

import comento.backend.ticket.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    //@TODO : 쿼리 다시 짜기!
    List<Performance> findByStartDateGreaterThanEqualOrderByStartDateAsc(Date startDate);
    List<Performance> findByTitleAndStartDateGreaterThanEqualOrderByStartDate(String title, Date startDate);
}
