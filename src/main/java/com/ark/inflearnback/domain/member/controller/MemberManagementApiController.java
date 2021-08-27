package com.ark.inflearnback.domain.member.controller;

import com.ark.inflearnback.config.model.HttpResponse;
import com.ark.inflearnback.domain.member.dto.SignUpRequestDto;
import com.ark.inflearnback.domain.member.service.MemberManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberManagementApiController {
    private final MemberManagementService memberManagementService;

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody final SignUpRequestDto request) {
        memberManagementService.signUp(request);
        return ResponseEntity.ok(HttpResponse.of(HttpStatus.OK, "회원가입 완료."));
    }
}


