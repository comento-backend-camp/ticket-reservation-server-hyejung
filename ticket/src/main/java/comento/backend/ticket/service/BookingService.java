package comento.backend.ticket.service;

import comento.backend.ticket.config.ErrorCode;
import comento.backend.ticket.config.customException.NotFoundDataException;
import comento.backend.ticket.domain.*;
import comento.backend.ticket.dto.*;
import comento.backend.ticket.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class BookingService {
	private static final int WAIT_TIME = 1;
	private static final int LEASE_TIME = 2;
	private static final String SEAT_LOCK = "seat_lock";

	private final BookingRepository bookingRepository;
	private final UserService userService;
	private final SeatService seatService;
	private final RedissonClient redissonClient;
	private final LockService lockService;

	public BookingService(BookingRepository bookingRepository, UserService userService,
		SeatService seatService, RedissonClient redissonClient, LockService lockService) {
		this.bookingRepository = bookingRepository;
		this.userService = userService;
		this.seatService = seatService;
		this.redissonClient = redissonClient;
		this.lockService = lockService;
	}

	//@brief : Redisson을 이용하여 동시성 제어할 경우 (선언적 트랜잭션 사용 불가, transcationManager 이용해야 함)
	//    public Booking saveBooking(final User user, final Performance performance, final Seat seat, final BookingDto reqBooking) {
	//        RLock lock = redissonClient.getLock(SEAT_LOCK);
	//        Booking booking = null;
	//        try {
	//            if (!(lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS))) {
	//                log.info("lock 획득 실패");
	//                throw new RuntimeException("Rock fail!");
	//            }
	//            log.info("lock 획득");
	//
	//            //seat 테이블의 is_booking 칼럼을 true로 update
	//            updateSeat(seat, performance, reqBooking);
	//
	//            //booking 여부 insert
	//            booking = reqBooking.toEntity(user, performance, seat);
	//        } catch (InterruptedException e) {
	//            throw new RuntimeException(e.getMessage());
	//        } finally {
	//            lock.unlock();
	//            log.info("lock 반납");
	//        }
	//        return bookingRepository.save(booking);
	//    }

	//@brief : MySQL User-level lock을 이용하여 동시성 제어할 경우(선언적 트랜잭션 사용 가능)
	@Transactional
	public Booking saveBooking(final User user, final Performance performance, final Seat seat,
		final BookingDto reqBooking) {
		Booking[] booking = {null};
		lockService.execute(SEAT_LOCK, 10, () -> {
			//seat 테이블의 is_booking 칼럼을 true로 update
			updateSeat(seat, performance, reqBooking);

			//booking 여부 insert
			booking[0] = reqBooking.toEntity(user, performance, seat);
		});

		return bookingRepository.save(booking[0]);
	}

<<<<<<< HEAD
	private void updateSeat(final Seat seat, final Performance performance, final BookingDto reqBooking) {
		SeatDto seatDto = SeatDto.builder()
			.seatId(seat.getId())
			.performance(performance)
			.seatType(reqBooking.getSeatType())
			.seatNumber(reqBooking.getSeatNumber())
			.isBooking(true)
			.build();
		seatService.updateSeat(seatDto);
	}
=======
    private void updateSeat(final Seat seat, final Performance performance, final BookingDto reqBooking) {
        SeatDto seatDto = SeatDto.builder()
                .seatId(seat.getId())
                .performance(performance)
                .seatType(reqBooking.getSeatType())
                .seatNumber(reqBooking.getSeatNumber())
                .isBooking(true)
                .build();
        seatService.updateSeat(seatDto);
    }
>>>>>>> main

	@Transactional(readOnly = true)
	public List<BookingResponse> getMyBooking(String email) {
		User user = userService.getUser(email);
		List<BookingResponse> result = bookingRepository.findMyBooking(true, user.getEmail());
		if (result.isEmpty()) {
			throw new NotFoundDataException(ErrorCode.NOT_FOUND);
		}
		return result;
	}
}
