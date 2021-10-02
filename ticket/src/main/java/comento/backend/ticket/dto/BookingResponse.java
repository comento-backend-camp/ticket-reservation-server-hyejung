package comento.backend.ticket.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookingResponse {
    private String title;
    private Date startDate;
    private String email;
    private SeatType seatType;
    private Integer seatNumber;
    private String price;

    public BookingResponse(){}

    public BookingResponse(String title, Date startDate, String email, SeatType seatType, Integer seatNumber, String price) {
        this.title = title;
        this.startDate = startDate;
        this.email = email;
        this.seatType = seatType;
        this.seatNumber = seatNumber;
        this.price = price;
    }
}
