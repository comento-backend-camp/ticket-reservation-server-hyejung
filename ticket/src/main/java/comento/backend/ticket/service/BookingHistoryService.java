package comento.backend.ticket.service;

import comento.backend.ticket.domain.BookingHistory;
import comento.backend.ticket.dto.BookingHistoryDto;
import comento.backend.ticket.repository.BookingHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingHistoryService {
    private final BookingHistoryRepository bookingHistoryRepository;

    public BookingHistoryService(BookingHistoryRepository bookingHistoryRepository) {
        this.bookingHistoryRepository = bookingHistoryRepository;
    }

    public BookingHistory saveBookingHistory(BookingHistoryDto bookingHistoryDto){
        BookingHistory bookingHistory = bookingHistoryDto.toEntity();
        return bookingHistoryRepository.save(bookingHistory);
    }
}
