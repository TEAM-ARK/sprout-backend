package team.ark.sprout.adapter.http;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountApiController {
    @PostMapping
    public ResponseEntity<ResponseData<String>> regist(@RequestBody @Valid SignUpForm signUpForm) {
        return ResponseEntity.ok(ResponseData.create(HttpStatus.OK, "가입되었습니다"));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    protected static class SignUpForm {
        @Email
        @NotNull(message = "올바른 이메일 형식이 아닙니다")
        private String email;

        @Length(min = 3, max = 20)
        @Pattern(
            regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,15}$",
            message = "닉네임은 한글, 영어, 숫자, 언더바, 하이픈으로 이루어진 3~15자리의 문자열이여야 합니다"
        )
        private String nickname;


        @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*])(?!.*(.)\\1\\1\\1)[0-9a-zA-Z!@#$%&*]{12,32}$",
            message = "비밀번호는 공백 없이 영문/숫자/특수문자로 이루어진 12~32자리의 문자열이어야 합니다"
        )
        private String password;

        protected static SignUpForm of(String email, String nickname, String password) {
            return new SignUpForm(email, nickname, password);
        }
    }
}
