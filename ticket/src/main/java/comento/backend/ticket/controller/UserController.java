package comento.backend.ticket.controller;

import comento.backend.ticket.config.SuccessCode;
import comento.backend.ticket.config.SuccessResponse;
import comento.backend.ticket.config.customException.NotFoundDataException;
import comento.backend.ticket.domain.User;
import comento.backend.ticket.dto.UserDto;
import comento.backend.ticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private SuccessCode successCode = SuccessCode.CREATED;
    private final UserService userService;
    private static final String CREATED_MSG = "등록 성공";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity addEmail(@Validated @RequestBody UserDto userDto){
        userService.saveUser(userDto);
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), CREATED_MSG));
    }
}
