package comento.backend.ticket.dto;

import comento.backend.ticket.domain.User;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
@Data
public class UserDto {
    @Email
    private String email;

    public UserDto(){}

    public UserDto(String email) {
        this.email = email;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .build();
    }
}
