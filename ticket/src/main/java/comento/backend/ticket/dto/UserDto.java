package comento.backend.ticket.dto;

import comento.backend.ticket.domain.User;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserDto {
    private String email;

    public UserDto(String email) {
        this.email = email;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .build();
    }
}
