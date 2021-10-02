package comento.backend.ticket.service;

import comento.backend.ticket.config.customException.DuplicatedException;
import comento.backend.ticket.config.customException.NotFoundDataException;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.*;
import comento.backend.ticket.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    // @TODO : insert 쿼리도 출력되고, 예외도 발생하지만 BookingHisotry 테이블에 새 데이터 insert가 안된다 ..
    public Seat getIsBooking(final User user, final Performance performance, final SeatType seatType, final Integer seatNumber, final boolean isBooking) {
        Optional<Seat> seatIsBooking = seatRepository.findByPerformanceAndSeatTypeAndSeatNumberAndIsBooking(performance, seatType, seatNumber, isBooking);
        final Seat seat = getSeat(performance, seatType, seatNumber); //seat 번호를 위해서 필요
        return seatIsBooking.orElseGet(() -> {
           //실패 여부 BookingHistory에 저장
            BookingHistoryDto bookingHistoryDto = BookingHistoryDto.builder()
                    .id(null)
                    .user(user)
                    .performance(performance)
                    .seat(seat)
                    .isSuccess(false)
                    .build();
            bookingHistoryService.saveBookingHistory(bookingHistoryDto);
           //예외 전달
            throw new DuplicatedException("SeatService");
        });
    }

    public Seat getSeat(Performance performance, SeatType seatType, Integer seatNumber) {
        Optional<Seat> seat = seatRepository.findByPerformanceAndSeatTypeAndSeatNumber(performance, seatType, seatNumber);
        return seat.orElse(seat.get());
    }
}
