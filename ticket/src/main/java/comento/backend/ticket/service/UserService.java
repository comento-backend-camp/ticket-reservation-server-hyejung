package comento.backend.ticket.service;

import comento.backend.ticket.config.customException.DuplicatedException;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.UserDto;
import comento.backend.ticket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(UserDto userDto){
        User user = userDto.toEntity();

        if(checkEmailDuplicate(user.getEmail())) {
            return userRepository.save(user);
        }else{
            throw new DuplicatedException("UserService");
        }
    }

    private boolean checkEmailDuplicate(String email){
        User user = userRepository.findByEmail(email).orElseGet(()->null);
        return Objects.isNull(user); //null이면 true
    }
}
