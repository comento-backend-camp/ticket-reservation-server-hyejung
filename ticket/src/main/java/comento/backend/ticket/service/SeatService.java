package comento.backend.ticket.service;

import comento.backend.ticket.config.customException.DuplicatedException;
import comento.backend.ticket.config.customException.NotFoundDataException;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.*;
import comento.backend.ticket.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Transactional(readOnly = true)
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
    @Transactional
    public Seat getIsBooking(final User user, final Performance performance, final SeatType seatType, final Integer seatNumber, final boolean isBooking) {
        Seat seatIsBooking = seatRepository.findByPerformanceAndSeatTypeAndSeatNumberAndIsBooking(performance, seatType, seatNumber, isBooking)
                .orElseGet(()-> null);
        if(Objects.isNull(seatIsBooking)){
            saveBookingFailLog(user, performance, null);
            throw new DuplicatedException("SeatService");
        }
        return seatIsBooking;
    }

    private void saveBookingFailLog(final User user, final Performance performance, final Seat seat) {
        //실패 여부 BookingHistory에 저장
        BookingHistoryDto bookingHistoryDto = BookingHistoryDto.builder()
                .id(null)
                .user(user)
                .performance(performance)
                .seat(seat)
                .isSuccess(false)
                .build();
        bookingHistoryService.saveBookingHistory(bookingHistoryDto);
    }
}
