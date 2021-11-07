package com.ark.inflearn.web;

import static org.springframework.http.HttpStatus.OK;
import com.ark.inflearn.form.SignForm;
import com.ark.inflearn.form.HttpResponse;
import com.ark.inflearn.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody final SignForm request) {
        memberService.signUp(request);
        return ResponseEntity.ok(
            HttpResponse.of(OK, "sign-up successful"));
    }

}
