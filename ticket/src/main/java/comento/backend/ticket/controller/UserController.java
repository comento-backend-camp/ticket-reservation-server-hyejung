package comento.backend.ticket.controller;

import comento.backend.ticket.config.SuccessCode;
import comento.backend.ticket.config.SuccessResponse;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.UserDto;
import comento.backend.ticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private SuccessCode successCode;
    private final UserService userService;
    private static final String CREATED_MSG = "등록 성공";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //이메일 등록
    @PostMapping("/signup")
    public SuccessResponse addEmail(@Validated @RequestBody String email){
        UserDto userDto = new UserDto(email);
        System.out.println(userDto.toString());
        User receive = userService.saveUser(userDto);

        if(receive != null){
            successCode = SuccessCode.CREATED;
            return SuccessResponse.res(
                    successCode.getStatus(), successCode.getMessage(), CREATED_MSG , successCode.getHttpStatus());
        }else{
            throw new RuntimeException("404");
        }
    }


}
