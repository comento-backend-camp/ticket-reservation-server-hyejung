package comento.backend.ticket.service;

import comento.backend.ticket.config.customException.NotFoundDataException;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.dto.PerformanceResponse;
import comento.backend.ticket.dto.SeatResponse;
import comento.backend.ticket.emum.SeatType;
import comento.backend.ticket.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(MockitoExtension.class)
public class MockSeatServiceTest {
    @Mock //mock객체로 생성
    private SeatRepository seatRepository;

    private SeatService seatService;
    private BookingHistoryService bookingHistoryService;

    @BeforeEach
    void init() {
        seatService = new SeatService(seatRepository); //mock 주입
    }
    @Test
    @DisplayName("[실패] 404 NOT FOUND ERROR")
    void 좌석_정보가_없으면_예외를_던진다() {
        //given
        Performance performance = new Performance(1L, null, null, null, null, null, null, null);
        PerformanceResponse performanceResponse = new PerformanceResponse(performance.getId(), "2022 정오의 음악회 5월", null, null,
                "전석 20,000원", null, null, null);

        given(seatRepository.findByPerformance(performance))
                .willReturn(new ArrayList<>());
        //when
        NotFoundDataException nfde = assertThrows(NotFoundDataException.class, () -> {
            seatService.getListPerformanceSeat(performanceResponse);
        });
        //then
        String msg = nfde.getMessage();
        assertThat(msg).isEqualTo(null);
    }
    @Test
    @DisplayName("[성공]")
    void 좌석_정보를_보여준다() throws ParseException {
        //given
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("2021-06-20");
        PerformanceResponse performanceResponse = new PerformanceResponse(1L, "2021 정오의 음악회 5월", date, date,
                "전석 20,000원", "국악", "국립극장\n하늘극장\n8세 이상 관람가", "70분");

        Performance performance = new Performance();
        performance.setId(performanceResponse.getId());
        given(seatRepository.findByPerformance(performance))
                .willReturn(SeatFixture.seats());
        //when
        List<SeatResponse> list =  seatService.getListPerformanceSeat(performanceResponse);

        //then
        assertThat(list.size()).isNotZero();
    }

    //반복적으로 테스트하여 사용할 객체를 위한 클래스 생성
    private static class SeatFixture {
        public static List<Seat> seats() {
            List<Seat> seats = new ArrayList<>();
            seats.add(seat());
            return seats;
        }
        public static Seat seat() {
            return Seat.builder()
                    .id(1L)
                    .seatType(SeatType.S)
                    .seatNumber(1)
                    .performance(new Performance())
                    .build();
        }
    }
}
