package comento.backend.ticket.service;

import comento.backend.ticket.domain.BookingHistory;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.BookingHistoryDto;
import comento.backend.ticket.repository.BookingHistoryRepository;

import org.springframework.stereotype.Service;

@Service
public class BookingHistoryService {
	private final BookingHistoryRepository bookingHistoryRepository;

	public BookingHistoryService(BookingHistoryRepository bookingHistoryRepository) {
		this.bookingHistoryRepository = bookingHistoryRepository;
	}

	public BookingHistory saveBookingHistory(final BookingHistoryDto bookingHistoryDto) {
		BookingHistory bookingHistory = bookingHistoryDto.toEntity();
		return bookingHistoryRepository.save(bookingHistory);
	}

	public void saveBookingSucessLog(final User user, final Performance performance, final Seat seat) {
		//booking의 성공 여부 history 저장
		BookingHistoryDto bookingHistoryDto = BookingHistoryDto.builder()
			.user(user)
			.performance(performance)
			.seat(seat)
			.isSuccess(true)
			.build();
		saveBookingHistory(bookingHistoryDto);
	}

	public void saveBookingFailLog(final User user, final Performance performance, final Seat seat) {
		//실패 여부 BookingHistory에 저장
		BookingHistoryDto bookingHistoryDto = BookingHistoryDto.builder()
			.user(user)
			.performance(performance)
			.seat(seat)
			.isSuccess(false)
			.build();
		saveBookingHistory(bookingHistoryDto);
	}
}
