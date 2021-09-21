package comento.backend.ticket.service;

import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("이메일을 입력하면 사용자 등록에 성공하여 DB에 저장한다.")
    public void 이메일_등록_성공(){
        //given
        UserDto userDto = new UserDto("kimhyejung12@naver.com");

        //when
        Optional<User> result = Optional.ofNullable(userService.saveUser(userDto));

        //then
        result.ifPresent(selectUser -> {
            System.out.println(selectUser.getId());
            System.out.println(selectUser.getEmail());
            System.out.println(selectUser.getCreateAt());
        });
    }

    @Test
    @DisplayName("이미 존재하는 이메일을 입력하면 사용자 등록에 실패하여 예외를 반환한다.")
    public void 이메일_등록_중복으로_인한_실패(){
        //given
        UserDto userDto = new UserDto("kimhyejung12@naver.com");
        UserDto userDto2 = new UserDto("kimhyejung12@naver.com");

        //when
        Optional<User> result = Optional.ofNullable(userService.saveUser(userDto));
        Optional<User> result2 = Optional.ofNullable(userService.saveUser(userDto2)); //error 409

        //then
        result.ifPresent(selectUser -> {
            System.out.println(selectUser.getId());
            System.out.println(selectUser.getEmail());
            System.out.println(selectUser.getCreateAt());
        });
    }

    @Test
    @DisplayName("이메일 형태가 아닌 경우 or 타입 오류 발생 시 사용자 등록에 실패하여 예외가 반환한다.")
    public void 이메일_등록_타입오류로_인한_실패(){ //@TODO : 에러가 발생해야 정상인데 성공 ... (Postman에서는 정상적으로 에러 발생)
        //given
        String email = "12"; //success?
        UserDto userDto = new UserDto(email);

        //when
        Optional<User> result = Optional.ofNullable(userService.saveUser(userDto)); //error 400

        //then
        result.ifPresent(selectUser -> {
            System.out.println(selectUser.getId());
            System.out.println(selectUser.getEmail());
            System.out.println(selectUser.getCreateAt());
        });
    }

}
