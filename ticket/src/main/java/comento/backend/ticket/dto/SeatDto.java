package comento.backend.ticket.dto;

import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import lombok.Builder;
import lombok.Data;

@Data
public class SeatDto {
    private Long seat_id;
    private Performance performance;
    private SeatType seatType;
    private Integer seatNumber;
    private boolean isBooking;

    public SeatDto(){};

    @Builder
    public SeatDto(Long seat_id, Performance performance, SeatType seatType, Integer seatNumber, boolean isBooking) {
        this.seat_id = seat_id;
        this.performance = performance;
        this.seatType = seatType;
        this.seatNumber = seatNumber;
        this.isBooking = isBooking;
    }

    public Seat toEntity(){
        return Seat.builder()
                .id(seat_id)
                .performance(performance)
                .seatType(seatType)
                .seatNumber(seatNumber)
                .isBooking(isBooking)
                .build();
    }
}
