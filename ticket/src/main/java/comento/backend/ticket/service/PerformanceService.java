package comento.backend.ticket.service;

import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.dto.PerformanceDto;
import comento.backend.ticket.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

@Service
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    @Autowired
    public PerformanceService(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    public List<Performance> getListByDate(PerformanceDto performanceDto){
        Date startDate = performanceDto.getStartDate();
        return performanceRepository.findByStartDateGreaterThanEqualOrderByStartDateAsc(startDate);
    }

    public List<Performance> getListByDateAndTitle(PerformanceDto performanceDto){
        Date startDate = performanceDto.getStartDate();
        String title = performanceDto.getTitle();
        return performanceRepository.findByTitleAndStartDateGreaterThanEqualOrderByStartDate(title, startDate);
    }
}
