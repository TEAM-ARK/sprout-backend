package com.ark.inflearnback.domain.member.controller;

import com.ark.inflearnback.config.model.HttpResponse;
import com.ark.inflearnback.domain.member.dto.SignRequestDto;
import com.ark.inflearnback.domain.member.service.MemberManagementService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberManagementApiController {

    private final MemberManagementService memberManagementService;

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody final SignRequestDto request) {
        memberManagementService.signUp(request);
        return ResponseEntity.ok(HttpResponse.of(HttpStatus.OK, "sign-up successful"));
    }

}
