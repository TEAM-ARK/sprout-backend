package team.ark.sprout.adapter.http;

import javax.validation.Valid;
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
}
