package comento.backend.ticket.dto;

import lombok.Data;

@Data
public class BookingResponseCreated {
    private SeatType seatType;
    private Integer seatNumber;

    public BookingResponseCreated(SeatType seatType, Integer seatNumber) {
        this.seatType = seatType;
        this.seatNumber = seatNumber;
    }
}
