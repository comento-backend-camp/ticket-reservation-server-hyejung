package comento.backend.ticket.controller;

import comento.backend.ticket.config.SuccessCode;
import comento.backend.ticket.config.SuccessResponse;
import comento.backend.ticket.domain.Performance;
import comento.backend.ticket.dto.PerformanceDto;
import comento.backend.ticket.dto.PerformanceResponse;
import comento.backend.ticket.dto.SeatResponse;
import comento.backend.ticket.service.PerformanceService;
import comento.backend.ticket.service.SeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.Date;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {
    private final PerformanceService performanceService;
    private final SeatService seatService;
    private SuccessCode successCode = SuccessCode.OK;

    @Autowired
    public PerformanceController(PerformanceService performanceService, SeatService seatService) {
        this.performanceService = performanceService;
        this.seatService = seatService;
    }

    @GetMapping("/info")
    public ResponseEntity showPerformanceInfo(@Valid @RequestParam(value = "date", required = true)
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                              @Valid @RequestParam(value = "title", required = false) String title) {
        PerformanceDto performanceDto = new PerformanceDto(title, date);
        List<PerformanceResponse> result = performanceService.getListPerformance(performanceDto);
        return ResponseEntity.ok().body(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), result));
    }

    @GetMapping("/info/seat")
    public ResponseEntity showPerformanceSeatInfo(@Valid @RequestParam(value = "date", required = true)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                              @Valid @RequestParam(value = "title", required = true) String title) {
        PerformanceDto performanceDto = new PerformanceDto(title, date);
        List<PerformanceResponse> performanceData = performanceService.getListPerformance(performanceDto);

        List<SeatResponse> seatResult = seatService.getListPerformanceSeat(performanceData.get(0));
        return ResponseEntity.ok().body(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), seatResult));
    }
}
