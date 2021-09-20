package comento.backend.ticket.repository;

import comento.backend.ticket.domain.User;
import comento.backend.ticket.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    public void 사용자_등록(){
        //given
        User user = new User();
        user.setEmail("kimhyejung12@naver.com");

        //when
        User result = userRepository.save(user);

        //then
        if(result != null)
            System.out.println(result.getId());
        else
            System.out.println("실패");
    }
}
