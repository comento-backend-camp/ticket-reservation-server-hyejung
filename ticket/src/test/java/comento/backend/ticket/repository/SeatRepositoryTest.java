package comento.backend.ticket.repository;

import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.domain.Seat;
import comento.backend.ticket.emum.SeatType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SeatRepositoryTest {
    private final SeatRepository seatRepository;

    @Autowired
    public SeatRepositoryTest(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Test
    @DisplayName("조회를 성공한다면 ID값을 가져온다.")
    void seat_정보_조회() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("2021-06-20");
        Performance performance = new Performance(1L, "2021 정오의 음악회 5월", "국악", date, date,
                "전석 20,000원", "국립극장\n하늘극장\n8세 이상 관람가", "70분");

        Optional<Seat> result = seatRepository.findByPerformanceAndSeatTypeAndSeatNumber(performance, SeatType.VIP, 2);

        assertThat(result.get().getId()).isNotZero();
    }
}
