package comento.backend.ticket.service;

import comento.backend.ticket.config.ErrorCode;
import comento.backend.ticket.config.customException.DuplicatedException;
import comento.backend.ticket.config.customException.NoAuthException;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.UserDto;
import comento.backend.ticket.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User saveUser(final UserDto userDto){
        User user = userDto.toEntity();

        if(!checkEmailDuplicate(user.getEmail())) {
            throw new DuplicatedException(ErrorCode.INVALID_USER);
        }

        return userRepository.save(user);
    }

    private boolean checkEmailDuplicate(final String email){
        User user = userRepository.findByEmail(email).orElseGet(()->null);
        return Objects.isNull(user); //null이면 true
    }

    public User getUser(final String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new NoAuthException(ErrorCode.NO_USER));
    }
}
