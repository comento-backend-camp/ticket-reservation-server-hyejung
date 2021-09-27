package comento.backend.ticket.service;

import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.dto.PerformanceDto;
import comento.backend.ticket.dto.PerformanceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest //통합테스트
@Transactional
public class PerformanceServiceTest {
    private final PerformanceService performanceService;

    @Autowired
    public PerformanceServiceTest(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @Test
    @DisplayName("[성공] 날짜를 정확하게 입력한 경우")
    void 공연_날짜_정보_조회() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("2021-06-20");

        PerformanceDto dto = new PerformanceDto(null, startDate);

        List<PerformanceResponse> result = performanceService.getListPerformance(dto);

        assertThat(result.size()).isNotZero();
    }

    @Test
    @DisplayName("[성공] 날짜, 공연제목을 정확하게 입력한 경우")
    void 공연_날짜이름_정보_조회() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("2021-06-20");
        String title = "국립무용단 <산조>";

        PerformanceDto dto = new PerformanceDto(title, startDate);

        List<PerformanceResponse> result = performanceService.getListPerformance(dto);

        assertThat(result.size()).isNotZero();
    }

    @Test
    @DisplayName("[실패] NOT FOUND ERROR")
    void 공연_날짜_정보_조회실패() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("2021-06-31");
        String title = "국립무용단 <산조>";

        PerformanceDto dto = new PerformanceDto(title, startDate);

        List<PerformanceResponse> result = performanceService.getListPerformance(dto);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("[실패] BAD REQUEST")
    void 공연_날짜이름_정보_조회실패() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("S");
        String title = "국립무용단 <산조>";

        PerformanceDto dto = new PerformanceDto(title, startDate);

        List<PerformanceResponse> result = performanceService.getListPerformance(dto);
        assertThat(result).isNull();
    }
}
