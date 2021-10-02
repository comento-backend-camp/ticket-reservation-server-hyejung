package comento.backend.ticket.dto;

import comento.backend.ticket.domain.BookingHistory;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class BookingHistoryDto {
    private Long id;
    private boolean isSuccess;
    private User user;
    private Performance performance;
    private Seat seat;

    public BookingHistoryDto(){}

    @Builder
    public BookingHistoryDto(Long id, boolean isSuccess, User user, Performance performance, Seat seat) {
        this.id = id;
        this.isSuccess = isSuccess;
        this.user = user;
        this.performance = performance;
        this.seat = seat;
    }

    public BookingHistory toEntity() {
        return BookingHistory.builder()
                .isSuccess(isSuccess)
                .user(user)
                .performance(performance)
                .seat(seat)
                .build();
    }
}
