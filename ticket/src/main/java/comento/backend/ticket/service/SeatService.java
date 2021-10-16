package comento.backend.ticket.service;

import comento.backend.ticket.config.customException.NoDataException;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.*;
import comento.backend.ticket.emum.SeatType;
import comento.backend.ticket.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Transactional(readOnly = true)
    public List<SeatResponse> getListPerformanceSeat(final PerformanceResponse performanceData) {
        Performance performance = new Performance();
        performance.setId(performanceData.getId());
        List<Seat> seatResult =  seatRepository.findByPerformance(performance);
        if(seatResult.isEmpty()){
            throw new NoDataException();
        }
        return seatResult.stream().map(SeatResponse::of).collect(Collectors.toList());
    }

    public Seat updateSeat(final SeatDto seatDto) {
        Seat seat = seatDto.toEntity();
        return seatRepository.save(seat);
    }

    @Transactional(readOnly = true)
    public Seat getIsBooking(final User user, final Performance performance, final SeatType seatType, final Integer seatNumber){
        Seat seatIsBooking = seatRepository.findByPerformanceAndSeatTypeAndSeatNumber(performance, seatType, seatNumber)
                .orElseGet(()-> null);
        return seatIsBooking;
    }
}
