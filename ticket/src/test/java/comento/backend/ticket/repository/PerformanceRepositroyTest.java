package comento.backend.ticket.repository;

import comento.backend.ticket.domain.Performance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PerformanceRepositroyTest {
    private final PerformanceRepository performanceRepository;

    @Autowired
    public PerformanceRepositroyTest(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    @Test
    @DisplayName("[성공] 날짜를 정확하게 입력한 경우")
    public void 공연_날짜_정보_조회() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("2020-06-20");

        List<Performance> result = performanceRepository.findByStartDateGreaterThanEqualOrderByStartDateAsc(startDate);

        assertThat(result.size()).isNotNull();
    }

    @Test
    @DisplayName("[성공] 날짜, 공연제목을 정확하게 입력한 경우")
    public void 공연_날짜이름_정보_조회() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("2020-06-20");
        String title = "국립무용단 <산조>";

        List<Performance> result = performanceRepository.findByTitleAndStartDateGreaterThanEqualOrderByStartDate(title, startDate);

        assertThat(result.size()).isNotNull();
    }

    @Test
    @DisplayName("[실패] NOT FOUND ERROR")
    public void 공연_날짜_정보_조회실패() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("2021-06-31");

        List<Performance> result = performanceRepository.findByStartDateGreaterThanEqualOrderByStartDateAsc(startDate);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("[실패] BAD REQUEST")
    public void 공연_날짜이름_정보_조회실패() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("ㄴ");
        String title = "국립무용단 <산조>";

        List<Performance> result = performanceRepository.findByTitleAndStartDateGreaterThanEqualOrderByStartDate(title, startDate);

        assertThat(result).isNull();
    }
}
