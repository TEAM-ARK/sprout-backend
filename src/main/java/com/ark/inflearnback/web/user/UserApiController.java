package com.ark.inflearnback.web.user;

import com.ark.inflearnback.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {

    /**
     * TODO : @ExceptionHandler를 모아서 처리할 수 있는 @RestControllerAdvice 클래스 구현
     */

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> joinUser(@RequestBody @Validated UserRequestDto userRequestDto,
                                           BindingResult bindingResult) {
        log.info("requestUser={}", userRequestDto);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors().get(0), HttpStatus.BAD_REQUEST);
        }

        userService.userRegistration(userRequestDto);
        return ResponseEntity.ok().body("회원가입 완료");
    }
}
