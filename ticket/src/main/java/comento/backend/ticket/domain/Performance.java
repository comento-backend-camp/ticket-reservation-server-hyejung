package comento.backend.ticket.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Entity
@Data
@Table(name = "Performance")
public class Performance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "performance_id")
	private Long id;

	@Column
	private String title;

	@Column
	private String genere;

	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(columnDefinition = "TEXT")
	private String price;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "running_time")
	private String runningTime;

	public Performance() {
	}

	@Builder
	public Performance(Long id, String title, String genere, Date startDate, Date endDate, String price,
		String description, String runningTime) {
		this.id = id;
		this.title = title;
		this.genere = genere;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.description = description;
		this.runningTime = runningTime;
	}
}
