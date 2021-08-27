package com.ark.inflearnback.domain.security.controller;

import com.ark.inflearnback.domain.security.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @PostMapping("/")
    public void signUp(@Valid @RequestBody final SignUpRequestDto request){

    }
}


