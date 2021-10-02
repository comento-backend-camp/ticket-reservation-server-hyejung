package comento.backend.ticket.service;

import comento.backend.ticket.config.customException.NotFoundDataException;
import comento.backend.ticket.domain.*;
import comento.backend.ticket.dto.*;
import comento.backend.ticket.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PerformanceService performanceService;
    private final BookingHistoryService bookingHistoryService;
    private final UserService userService;
    private final SeatService seatService;

    public BookingService(BookingRepository bookingRepository, PerformanceService performanceService,
                          BookingHistoryService bookingHistoryService, UserService userService,
                          SeatService seatService) {
        this.bookingRepository = bookingRepository;
        this.performanceService = performanceService;
        this.bookingHistoryService = bookingHistoryService;
        this.userService = userService;
        this.seatService = seatService;
    }

    // @TODO : 동시성 문제 해결해야 함
    //예약 정보를 booking에 저장하고, Seat 정보에는 is_booking을 true로 변경
    @Transactional
    public Booking saveBookging(final BookingDto reqBooking){
        final User user = userService.getUser(reqBooking.getEmail());
        final Performance performance = performanceService.getPerformance(reqBooking.getId(), reqBooking.getTitle());
        final Seat seat = seatService.getIsBooking(user, performance, reqBooking.getSeatType(), reqBooking.getSeatNumber(), false); //false라면 예약 가능

        //seat 테이블의 is_booking 칼럼을 true로 update
        updateSeat(seat, performance, reqBooking);

        //seat의 값이 있다면, booking 가능
        addBookingHistory(user, performance, seat);

        //booking 여부 insert
        Booking booking = reqBooking.toEntity(user, performance, seat);
        return bookingRepository.save(booking);
    }

    private void updateSeat(final Seat seat, final Performance performance, final BookingDto reqBooking) {
        SeatDto seatDto = SeatDto.builder()
                .seat_id(seat.getId())
                .performance(performance)
                .seatType(reqBooking.getSeatType())
                .seatNumber(reqBooking.getSeatNumber())
                .isBooking(true)
                .build();
        seatService.updateSeat(seatDto);
    }

    private void addBookingHistory(final User user, final Performance performance, final Seat seat) {
        //booking의 성공 여부 history 저장
        BookingHistoryDto bookingHistoryDto = BookingHistoryDto.builder()
                        .user(user)
                        .performance(performance)
                        .seat(seat)
                        .isSuccess(true)
                        .build();
        bookingHistoryService.saveBookingHistory(bookingHistoryDto);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getMyBooking(String email){
        User user = userService.getUser(email);
        List<BookingResponse> result = bookingRepository.findMyBooking(true, user.getEmail());
        if(result.isEmpty()){
            throw new NotFoundDataException();
        }
        return result;
    }
}
