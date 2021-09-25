package comento.backend.ticket.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PerformanceDto {
    private String title;
    private Date startDate;
    private Date endDate;
    private String price;
    private String genere;
    private String description;
    private String runningTime;

    public PerformanceDto(){}

    public PerformanceDto(String title, Date startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    public PerformanceDto(String title, Date startDate, Date endDate, String price, String genere, String description, String runningTime) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.genere = genere;
        this.description = description;
        this.runningTime = runningTime;
    }
}
