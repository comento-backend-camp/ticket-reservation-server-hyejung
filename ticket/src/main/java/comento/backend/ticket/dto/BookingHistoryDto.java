package comento.backend.ticket.dto;

import comento.backend.ticket.domain.BookingHistory;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.domain.User;
import lombok.Data;

@Data
public class BookingHistoryDto {
    private boolean isSuccess;
    private User user;
    private Performance performance;
    private Seat seat;

    public BookingHistory toEntity() {
        return BookingHistory.builder()
                .isSuccess(isSuccess)
                .user(user)
                .performance(performance)
                .seat(seat)
                .build();
    }
}
