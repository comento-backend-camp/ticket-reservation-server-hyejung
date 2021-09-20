package comento.backend.ticket.domain;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="User")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = true)
    private String email;

    @Column()
    @CreationTimestamp //Entity가 생성되어 저장될 때 시간이 자동 저장
    @Temporal(TemporalType.TIMESTAMP)
    private Date create_at;

    public User(){}

    @Builder
    public User(long id, String email, Date create_at) {
        this.id = id;
        this.email = email;
        this.create_at = create_at;
    }
}
