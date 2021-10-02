package comento.backend.ticket.domain;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="BookingHistory")
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bh_id") //booking_histroy id
    private Long id;

    @Column(name = "is_success", columnDefinition = "boolean default false")
    private Boolean isSuccess;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column(name="create_at")
    @CreationTimestamp //Entity가 생성되어 저장될 때 시간이 자동 저장
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    public BookingHistory(){}

    @Builder
    public BookingHistory(Boolean isSuccess, User user, Performance performance, Seat seat) {
        this.isSuccess = isSuccess;
        this.user = user;
        this.performance = performance;
        this.seat = seat;
    }
}
