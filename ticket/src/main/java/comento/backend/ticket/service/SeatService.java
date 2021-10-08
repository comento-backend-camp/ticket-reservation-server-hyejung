package comento.backend.ticket.service;

import comento.backend.ticket.config.customException.DuplicatedException;
import comento.backend.ticket.config.customException.NotFoundDataException;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.*;
import comento.backend.ticket.emum.SeatType;
import comento.backend.ticket.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final BookingHistoryService bookingHistoryService;

    public SeatService(SeatRepository seatRepository, BookingHistoryService bookingHistoryService) {
        this.seatRepository = seatRepository;
        this.bookingHistoryService = bookingHistoryService;
    }

    public List<SeatResponse> getListPerformanceSeat(final PerformanceResponse performanceData) {
        Performance performance = new Performance();
        performance.setId(performanceData.getId());
        List<Seat> seatResult =  seatRepository.findByPerformance(performance);
        if(seatResult.isEmpty()){
            throw new NotFoundDataException();
        }
        return seatResult.stream().map(SeatResponse::of).collect(Collectors.toList());
    }

    public Seat updateSeat(final SeatDto seatDto) {
        Seat seat = seatDto.toEntity();
        return seatRepository.save(seat);
    }

    public Seat getIsBooking(final User user, final Performance performance, final SeatType seatType, final Integer seatNumber){
        Seat seatIsBooking = seatRepository.findByPerformanceAndSeatTypeAndSeatNumber(performance, seatType, seatNumber)
                .orElseGet(()-> null);
        if(seatIsBooking.isBooking()){ //true라면 예약 불가
            bookingHistoryService.saveBookingFailLog(user, performance, seatIsBooking);
            throw new DuplicatedException("SeatService");
        }

        if(Objects.isNull(seatIsBooking)){ //좌석 정보 없음
            throw new NotFoundDataException();
        }
        return seatIsBooking;
    }
}
