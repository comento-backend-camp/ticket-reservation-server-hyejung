package comento.backend.ticket.controller;

import comento.backend.ticket.config.ErrorCode;
import comento.backend.ticket.config.SuccessCode;
import comento.backend.ticket.config.SuccessResponse;
import comento.backend.ticket.config.customException.DuplicatedException;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.BookingDto;
import comento.backend.ticket.dto.BookingResponse;
import comento.backend.ticket.dto.BookingResponseCreated;
import comento.backend.ticket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/performance/booking")
public class BookingController {
    private final BookingService bookingService;
    private final BookingHistoryService bookingHistoryService;
    private final UserService userService;
    private final PerformanceService performanceService;
    private final SeatService seatService;

    private SuccessCode successCode;
    private BookingResponseCreated bookingResponseCreated;

    @Autowired
    public BookingController(BookingService bookingService, BookingHistoryService bookingHistoryService, UserService userService, PerformanceService performanceService, SeatService seatService) {
        this.bookingService = bookingService;
        this.bookingHistoryService = bookingHistoryService;
        this.userService = userService;
        this.performanceService = performanceService;
        this.seatService = seatService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addBooking(@Valid @RequestBody BookingDto reqBooking){
        final User user = userService.getUser(reqBooking.getEmail());
        final Performance performance = performanceService.getPerformance(reqBooking.getId(), reqBooking.getTitle());
        final Seat seat = seatService.getIsBooking(performance, reqBooking.getSeatType(), reqBooking.getSeatNumber()); //false라면 예약 가능

        if(seat.isBooking()){ //true면 이미 예약된 상태
            bookingHistoryService.saveBookingFailLog(user, performance, seat);
            throw new DuplicatedException(ErrorCode.INVALID_SEAT);
        }else{
            bookingService.saveBooking(user, performance, seat, reqBooking);
            bookingHistoryService.saveBookingSucessLog(user, performance, seat);
            bookingResponseCreated = new BookingResponseCreated(reqBooking.getSeatType(), reqBooking.getSeatNumber());
            successCode = SuccessCode.CREATED;
        }
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), bookingResponseCreated));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> showMyBooking(@Valid @PathVariable String email){
        List<BookingResponse> myBooking = bookingService.getMyBooking(email);
        successCode = SuccessCode.OK;
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), myBooking));
    }
}
