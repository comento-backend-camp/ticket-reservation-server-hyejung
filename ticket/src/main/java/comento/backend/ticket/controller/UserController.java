package comento.backend.ticket.controller;

import comento.backend.ticket.config.SuccessCode;
import comento.backend.ticket.config.SuccessResponse;
import comento.backend.ticket.dto.UserDto;
import comento.backend.ticket.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
<<<<<<< HEAD
	private static final String CREATED_MSG = "등록 성공";
	private SuccessCode successCode = SuccessCode.CREATED;
	private final UserService userService;
=======
    private static final String CREATED_MSG = "등록 성공";
    private SuccessCode successCode = SuccessCode.CREATED;
    private final UserService userService;
>>>>>>> main

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

<<<<<<< HEAD
	@PostMapping("/signup")
	public ResponseEntity<Object> addEmail(@Validated @RequestBody UserDto userDto) {
		userService.saveUser(userDto);
		return ResponseEntity.status(successCode.getHttpStatus())
			.body(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), CREATED_MSG));
	}
=======
        @PostMapping("/signup")
    public ResponseEntity<Object> addEmail(@Validated @RequestBody UserDto userDto){
        userService.saveUser(userDto);
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(SuccessResponse.res(successCode.getStatus(), successCode.getMessage(), CREATED_MSG));
    }
>>>>>>> main
}
