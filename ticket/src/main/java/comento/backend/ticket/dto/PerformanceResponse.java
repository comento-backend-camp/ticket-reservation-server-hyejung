package comento.backend.ticket.dto;

import comento.backend.ticket.domain.Performance;
import lombok.Data;

import java.util.*;

@Data
public class PerformanceResponse {
    private Long id;
    private String title;
    private Date startDate;
    private Date endDate;
    private String price;
    private String genere;
    private String description;
    private String runningTime;

    public PerformanceResponse(Long id, String title, Date startDate, Date endDate, String price, String genere, String description, String runningTime) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.genere = genere;
        this.description = description;
        this.runningTime = runningTime;
    }

    //convert
    public static PerformanceResponse of(Performance performance){
        return new PerformanceResponse(
                performance.getId(),
                performance.getTitle(),
                performance.getStartDate(),
                performance.getEndDate(),
                performance.getPrice(),
                performance.getGenere(),
                performance.getDescription(),
                performance.getRunningTime()
        );
    }
}
