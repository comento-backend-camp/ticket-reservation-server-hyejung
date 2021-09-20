package comento.backend.ticket.service;

import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.UserDto;
import comento.backend.ticket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //이메일 등록
    public User saveUser(UserDto userDto){
        User user = userDto.toEntity();
        if(verifyEmail(user.getEmail()))
            if(checkEmailDuplicate(user.getEmail()))
                throw new RuntimeException("409");
            else
                return userRepository.save(user);
        else
            throw new RuntimeException("400");
    }

    //이메일 검증 (이메일 형식인지 확인)
    private boolean verifyEmail(String email) {
        String email2 = email;
        String regex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        return Pattern.matches(regex, email2);
    }

    //이메일 중복 확인
    private boolean checkEmailDuplicate(String email){
        return userRepository.findByEmail(email).isPresent() ? true : false;
    }
}
