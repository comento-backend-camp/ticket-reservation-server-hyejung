package comento.backend.ticket.dto;

import comento.backend.ticket.emum.SeatType;
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
