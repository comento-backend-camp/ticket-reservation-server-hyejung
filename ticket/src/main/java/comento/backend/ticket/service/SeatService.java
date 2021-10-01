package comento.backend.ticket.service;

import comento.backend.ticket.config.customException.NotFoundDataException;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.dto.PerformanceResponse;
import comento.backend.ticket.dto.SeatDto;
import comento.backend.ticket.dto.SeatResponse;
import comento.backend.ticket.dto.SeatType;
import comento.backend.ticket.repository.SeatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<SeatResponse> getListPerformanceSeat(PerformanceResponse performanceData) {
        Performance performance = new Performance();
        performance.setId(performanceData.getId());
        List<Seat> seatResult =  seatRepository.findByPerformance(performance);
        if(seatResult.isEmpty()){
            throw new NotFoundDataException();
        }
        return seatResult.stream().map(SeatResponse::of).collect(Collectors.toList());
    }

    public Seat getSeat(Performance performance, SeatType seatType, Integer seatNumber) {
        Optional<Seat> seat = seatRepository.findByPerformanceAndSeatTypeAndSeatNumber(performance, seatType, seatNumber);
        return seat.orElseThrow(NotFoundDataException::new);
    }

    public Seat updateSeat(SeatDto seatDto) {
        Seat seat = seatDto.toEntity();
        return seatRepository.save(seat);
    }
}
