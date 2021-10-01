package comento.backend.ticket.domain;

import comento.backend.ticket.dto.SeatType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Column(name = "seat_type", nullable = true)
    @Enumerated(EnumType.STRING) //enum 타입으로 DB에 저장
    private SeatType seatType;

    @Column(name = "seat_number", nullable = true)
    private Integer seatNumber;

    @Column(name = "is_booking", columnDefinition = "boolean default false")
    private boolean isBooking;

    public Seat(){}

    @Builder
    public Seat(Long id, Performance performance, SeatType seatType, Integer seatNumber, boolean isBooking) {
        this.id = id;
        this.performance = performance;
        this.seatType = seatType;
        this.seatNumber = seatNumber;
        this.isBooking = isBooking;
    }
}
