package comento.backend.ticket.service;

import comento.backend.ticket.config.customException.NotFoundDataException;
import comento.backend.ticket.domain.*;
import comento.backend.ticket.dto.*;
import comento.backend.ticket.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BookingService {
    private static final int WAIT_TIME = 1;
    private static final int LEASE_TIME = 2;
    private static final String SEAT_LOCK = "seat_lock";

    private final BookingRepository bookingRepository;
    private final PerformanceService performanceService;
    private final BookingHistoryService bookingHistoryService;
    private final UserService userService;
    private final SeatService seatService;
    private final RedissonClient redissonClient;

    public BookingService(BookingRepository bookingRepository, PerformanceService performanceService,
                          BookingHistoryService bookingHistoryService, UserService userService,
                          SeatService seatService, RedissonClient redissonClient) {
        this.bookingRepository = bookingRepository;
        this.performanceService = performanceService;
        this.bookingHistoryService = bookingHistoryService;
        this.userService = userService;
        this.seatService = seatService;
        this.redissonClient = redissonClient;
    }

    public Booking saveBookging(final BookingDto reqBooking) {
        RLock lock = redissonClient.getLock(SEAT_LOCK);
        lock.lock(LEASE_TIME, TimeUnit.SECONDS);

        Booking booking = null;
        try {
            if (!(lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS))) {
                log.info("lock 획득 실패");
                throw new RuntimeException("Rock fail!");
            }
            log.info("lock 획득");
            final User user = userService.getUser(reqBooking.getEmail());
            final Performance performance = performanceService.getPerformance(reqBooking.getId(), reqBooking.getTitle());
            final Seat seat = seatService.getIsBooking(user, performance, reqBooking.getSeatType(), reqBooking.getSeatNumber()); //false라면 예약 가능

            //seat 테이블의 is_booking 칼럼을 true로 update
            updateSeat(seat, performance, reqBooking);

            //seat의 값이 있다면, booking 가능
            bookingHistoryService.saveBookingSucessLog(user, performance, seat);

            //booking 여부 insert
            booking = reqBooking.toEntity(user, performance, seat);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            lock.unlock();
            log.info("lock 반납");
        }

        return bookingRepository.save(booking);
    }

    private void updateSeat(final Seat seat, final Performance performance, final BookingDto reqBooking) {
        SeatDto seatDto = SeatDto.builder()
                .seat_id(seat.getId())
                .performance(performance)
                .seatType(reqBooking.getSeatType())
                .seatNumber(reqBooking.getSeatNumber())
                .isBooking(true)
                .build();
        seatService.updateSeat(seatDto);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getMyBooking(String email) {
        User user = userService.getUser(email);
        List<BookingResponse> result = bookingRepository.findMyBooking(true, user.getEmail());
        if (result.isEmpty()) {
            throw new NotFoundDataException();
        }
        return result;
    }
}
