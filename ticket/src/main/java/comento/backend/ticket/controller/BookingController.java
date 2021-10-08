package comento.backend.ticket.controller;

import comento.backend.ticket.config.SuccessCode;
import comento.backend.ticket.config.SuccessResponse;
import comento.backend.ticket.dto.BookingDto;
import comento.backend.ticket.dto.BookingResponse;
import comento.backend.ticket.dto.BookingResponseCreated;
import comento.backend.ticket.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/performance/booking")
public class BookingController {
    private final BookingService bookingService;
    private SuccessCode successCode;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("")
    public ResponseEntity addBooking(@Valid @RequestBody BookingDto reqBooking){
        bookingService.saveBookging(reqBooking);
        BookingResponseCreated result = new BookingResponseCreated(reqBooking.getSeatType(), reqBooking.getSeatNumber());
        successCode = SuccessCode.CREATED;
        return new ResponseEntity(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), result),
                HttpStatus.CREATED);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity showMyBooking(@Valid @PathVariable String email){
        List<BookingResponse> result = bookingService.getMyBooking(email);
        successCode = SuccessCode.OK;
        return new ResponseEntity(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), result),
                HttpStatus.OK);
    }
}
