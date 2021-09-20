package comento.backend.ticket.service;

import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//given
//when
//then

@SpringBootTest
@Transactional
public class UserServiceTest {
    private final UserService userService;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void 이메일_등록(){
        //given
        UserDto userDto = new UserDto("kimhyejung12@naver.com");
        UserDto userDto2 = new UserDto("kimhyejung12@naver.com"); //error 409

        //when
        Optional<User> result = Optional.ofNullable(userService.saveUser(userDto));
        Optional<User> result2 = Optional.ofNullable(userService.saveUser(userDto2));

        //then
        result.ifPresent(selectUser -> {
            System.out.println(selectUser.getId());
            System.out.println(selectUser.getEmail());
            System.out.println(selectUser.getCreate_at());
        });

        result2.ifPresent(selectUser -> {
            System.out.println(selectUser.getId());
            System.out.println(selectUser.getEmail());
            System.out.println(selectUser.getCreate_at());
        });
    }

}
