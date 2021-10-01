package comento.backend.ticket.dto;

import comento.backend.ticket.domain.Booking;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.domain.User;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/*
   "date" : 2021-09-04,
   "title" : "뮤지컬",
   "email" :"kimhyejung12@naver.com"
   "seat" : "VIP석"
   "seat_number" : 2
 */

@Getter
@Data
public class BookingDto {
    private Long id;
    private String title;
    private Date startDate;
    private String email;
    private SeatType seatType;
    private Integer seatNumber;
    private String price;
    private User user;
    private Performance performance;
    private Seat seat;

    public BookingDto() {
    }

    public BookingDto(Long id, String title, Date startDate, String email, SeatType seatType, Integer seatNumber, String price) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.email = email;
        this.seatType = seatType;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public BookingDto(Long id, String title, Date startDate, String email, SeatType seatType, Integer seatNumber, String price, User user, Performance performance, Seat seat) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.email = email;
        this.seatType = seatType;
        this.seatNumber = seatNumber;
        this.price = price;
        this.user = user;
        this.performance = performance;
        this.seat = seat;
    }

    public Booking toEntity() {
        return Booking.builder()
                .user(user)
                .performance(performance)
                .seat(seat)
                .build();
    }

}