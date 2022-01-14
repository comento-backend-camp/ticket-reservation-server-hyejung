package comento.backend.ticket.dto;

import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.emum.SeatType;
import lombok.Data;

@Data
public class SeatResponse {
	private SeatType seatType;
	private Integer seatNumber;
	private boolean isBooking;

	public SeatResponse(SeatType seatType, Integer seatNumber, boolean isBooking) {
		this.seatType = seatType;
		this.seatNumber = seatNumber;
		this.isBooking = isBooking;
	}

	public static SeatResponse of(Seat seat) {
		return new SeatResponse(
			seat.getSeatType(),
			seat.getSeatNumber(),
			seat.isBooking()
		);
	}
}
