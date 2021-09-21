package comento.backend.ticket.repository;

import comento.backend.ticket.domain.User;
import comento.backend.ticket.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

//given
//when
//then

@SpringBootTest
@Transactional
public class UserRepositryTest {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("이메일을 입력하면 사용자 등록에 성공하여 DB에 저장한다.")
    public void 사용자_등록_성공(){
        //given
        User user = new User();
        user.setEmail("kimhyejung12@naver.com");

        //when
        User result = userRepository.save(user);

        //then
        assertThat(result.getId()).isNotNull();
    }

    @Test
    @DisplayName("이메일을 입력하면 사용자 등록에 성공하여 DB에 저장한다.")
    public void 사용자_등록_중복으로_인한_실패(){
        //given
        User user = new User();
        user.setEmail("kimhyejung12@naver.com"); //error 409

        //when
        User result = userRepository.save(user);

        //then
        assertThat(result.getId()).isNotNull();
    }
}
