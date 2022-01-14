package comento.backend.ticket.repository;

import comento.backend.ticket.domain.Booking;
import comento.backend.ticket.dto.BookingResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	/*해당 유저(u, ~@~)가 예약(b)한 공연 정보(p)와 좌석 정보(s) 조회*/
	@Query(
		"select new comento.backend.ticket.dto.BookingResponse(p.title, p.startDate, u.email, s.seatType, s.seatNumber, p.price) "
			+
			"from Seat s join s.performance p on s.performance = p.id " +
			"join Booking b on b.seat = s.id " +
			"join User u on u.id = b.user " +
			"where s.isBooking = :isBooking and u.email = :email")
	List<BookingResponse> findMyBooking(boolean isBooking, String email);
}
