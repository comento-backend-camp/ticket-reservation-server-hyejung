package comento.backend.ticket.domain;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="booking_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column(name="create_at")
    @CreationTimestamp //Entity가 생성되어 저장될 때 시간이 자동 저장
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    public Booking(){}

    @Builder
    public Booking(Long id, User user, Performance performance, Seat seat, Date createAt) {
        this.id = id;
        this.user = user;
        this.performance = performance;
        this.seat = seat;
        this.createAt = createAt;
    }
}

