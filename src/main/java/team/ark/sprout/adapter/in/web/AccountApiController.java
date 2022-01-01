package team.ark.sprout.adapter.in.web;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.ark.sprout.common.validate.SignUpFormValidator;
import team.ark.sprout.port.in.RegistAccount;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountApiController {
    private final RegistAccount registAccount;
    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm")
    public void initBind(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @PostMapping
    public ResponseEntity<ResponseData<String>> regist(@RequestBody @Valid SignUpForm signUpForm) {
        registAccount.regist(signUpForm.mapToDomain());
        return ResponseEntity.ok(ResponseData.create(HttpStatus.OK, "가입되었습니다"));
    }
}
