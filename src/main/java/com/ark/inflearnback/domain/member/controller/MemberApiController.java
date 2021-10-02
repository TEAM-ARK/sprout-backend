package com.ark.inflearnback.domain.member.controller;

import static org.springframework.http.HttpStatus.OK;
import com.ark.inflearnback.configuration.http.model.form.HttpResponse;
import com.ark.inflearnback.domain.member.model.form.PasswordForm;
import com.ark.inflearnback.domain.member.model.form.SignForm;
import com.ark.inflearnback.domain.member.service.MemberService;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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
    private final JavaMailSender javaMailSender;

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody final SignForm request) {
        memberService.signUp(request);
        return ResponseEntity.ok(
            HttpResponse.of(OK, "sign-up successful"));
    }

    @PostMapping("/informationMail")
    public ResponseEntity<?> findPassword(@Valid @RequestBody final Map<String, String> requestMap) {
        javaMailSender.send(memberService.informationEmail(requestMap.get("email")));
        return ResponseEntity.ok(
            HttpResponse.of(OK, "mail send successful"));
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkKey(@Valid @RequestBody final Map<String, String> requestMap) {
        memberService.checkKey(requestMap.get("key"));
        return ResponseEntity.ok(HttpResponse.of(OK, "certificate check successful"));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody final PasswordForm passwordForm) {
        memberService.changePassword(passwordForm);
        return ResponseEntity.ok(HttpResponse.of(OK, "change password complete"));
    }

}
