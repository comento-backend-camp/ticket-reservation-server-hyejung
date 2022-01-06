package comento.backend.ticket.dto;

import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.emum.SeatType;
import lombok.Builder;
import lombok.Data;

@Data
public class SeatDto {
    private Long seatId;
    private Performance performance;
    private SeatType seatType;
    private Integer seatNumber;
    private boolean isBooking;

    @Builder
    public SeatDto(Long seatId, Performance performance, SeatType seatType, Integer seatNumber, boolean isBooking) {
        this.seatId = seatId;
        this.performance = performance;
        this.seatType = seatType;
        this.seatNumber = seatNumber;
        this.isBooking = isBooking;
    }

    public Seat toEntity(){
        return Seat.builder()
                .id(seatId)
                .performance(performance)
                .seatType(seatType)
                .seatNumber(seatNumber)
                .isBooking(isBooking)
                .build();
    }
}
