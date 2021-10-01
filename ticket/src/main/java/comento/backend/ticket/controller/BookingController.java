package comento.backend.ticket.controller;

import comento.backend.ticket.config.SuccessCode;
import comento.backend.ticket.config.SuccessResponse;
import comento.backend.ticket.domain.Booking;
import comento.backend.ticket.dto.BookingDto;
import comento.backend.ticket.dto.BookingResponseCreated;
import comento.backend.ticket.dto.SeatDto;
import comento.backend.ticket.service.BookingHistoryService;
import comento.backend.ticket.service.BookingService;
import comento.backend.ticket.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/performance/booking")
public class BookingController {
    private final BookingService bookingService;
    private final SeatService seatService;
    private final BookingHistoryService bookingHistoryService;
    private SuccessCode successCode;

    @Autowired
    public BookingController(BookingService bookingService, SeatService seatService, BookingHistoryService bookingHistoryService) {
        this.bookingService = bookingService;
        this.seatService = seatService;
        this.bookingHistoryService = bookingHistoryService;
    }

    @PostMapping("")
    public ResponseEntity addBooking(@Valid @RequestBody BookingDto reqBooking){
        Booking booking = bookingService.saveBookging(reqBooking);
        if(booking == null){

        }

        BookingResponseCreated result = new BookingResponseCreated(reqBooking.getSeatType(), reqBooking.getSeatNumber());
        successCode = SuccessCode.CREATED;
        return new ResponseEntity(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), result),
                HttpStatus.CREATED);
    }

    //@TODO : 예약 정보 조회하기
//    @GetMapping("/email/{email}")
//    public ResponseEntity showMyBooking(@Valid @PathVariable String email){
//
//        successCode = SuccessCode.OK;
//        return new ResponseEntity(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), result)
//                HttpStatus.OK);
//    }
}
