package comento.backend.ticket.service;

import comento.backend.ticket.domain.Booking;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.BookingDto;
import comento.backend.ticket.dto.SeatDto;
import comento.backend.ticket.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PerformanceService performanceService;
    private final UserService userService;
    private final SeatService seatService;

    public BookingService(BookingRepository bookingRepository, PerformanceService performanceService, UserService userService, SeatService seatService) {
        this.bookingRepository = bookingRepository;
        this.performanceService = performanceService;
        this.userService = userService;
        this.seatService = seatService;
    }

    // @TODO : VIP, 3이 여러 개 저장되는 이슈 해결 해야 함 (동시성 문제)
    //예약 정보 저장하고, Seat 정보에는 is_booking을 true로 변경
    public Booking saveBookging(BookingDto reqBooking){
        User user = userService.getUser(reqBooking.getEmail());
        Performance performance = performanceService.getPerformance(reqBooking.getId(), reqBooking.getTitle());
        Seat seat = seatService.getSeat(performance, reqBooking.getSeatType(), reqBooking.getSeatNumber());

        reqBooking.setUser(user);
        reqBooking.setPerformance(performance);
        reqBooking.setSeat(seat);

        //seat 테이블의 is_booking 칼럼을 true로 update
        SeatDto seatDto = new SeatDto(seat.getId(), performance, reqBooking.getSeatType(), reqBooking.getSeatNumber(), true);
        seatService.updateSeat(seatDto);

        Booking booking = reqBooking.toEntity();
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getMyBooking(String email){

        return null;
    }
}
