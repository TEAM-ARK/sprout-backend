package com.ark.inflearnback.domain.mail.controller;

import static org.springframework.http.HttpStatus.OK;
import com.ark.inflearnback.configuration.http.model.form.HttpResponse;
import com.ark.inflearnback.domain.mail.service.MailService;
import com.ark.inflearnback.domain.member.model.form.PasswordForm;
import java.util.Map;
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
@RequestMapping("/api/v1/mail")
public class MailApiController {

    private final MailService mailService;

    @PostMapping("/informationMail")
    public ResponseEntity<?> findPassword(@Valid @RequestBody final Map<String, String> requestMap) {
        mailService.informationEmail(requestMap.get("email"));
        return ResponseEntity.ok(
            HttpResponse.of(OK, "mail send successful"));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody final PasswordForm passwordForm) {
        mailService.changePassword(passwordForm, "email");
        return ResponseEntity.ok(HttpResponse.of(OK, "change password complete"));
    }
}
