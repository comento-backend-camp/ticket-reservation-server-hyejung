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

    public Booking toEntity(final User user, final Performance performance, final Seat seat) {
        return Booking.builder()
                .user(user)
                .performance(performance)
                .seat(seat)
                .build();
    }

}