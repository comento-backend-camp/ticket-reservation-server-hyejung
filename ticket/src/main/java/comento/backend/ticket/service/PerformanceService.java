package comento.backend.ticket.service;

import comento.backend.ticket.config.customException.NotFoundDataException;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.dto.PerformanceDto;
import comento.backend.ticket.dto.PerformanceResponse;
import comento.backend.ticket.repository.PerformanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    public PerformanceService(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    public List<PerformanceResponse> getListPerformance(PerformanceDto performanceDto){
        Date startDate = performanceDto.getStartDate();
        String title = performanceDto.getTitle();
        List<Performance> result = title != null ?
                performanceRepository.findByTitleAndStartDateGreaterThanEqualOrderByStartDate(title, startDate) :
                performanceRepository.findByStartDateGreaterThanEqualOrderByStartDateAsc(startDate);
        if(result.isEmpty()){
            throw new NotFoundDataException();
        }
        return result.stream().map(PerformanceResponse::of).collect(Collectors.toList());
    }
}
