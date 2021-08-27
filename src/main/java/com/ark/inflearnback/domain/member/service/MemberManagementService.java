package com.ark.inflearnback.domain.member.service;

import com.ark.inflearnback.domain.member.dto.SignUpRequestDto;
import com.ark.inflearnback.domain.security.repository.MemberRepository;
import com.ark.inflearnback.domain.security.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberManagementService {
    private RoleRepository roleRepository;
    private MemberRepository memberRepository;

    @Transactional
    public boolean signUp(final SignUpRequestDto request){
        return false;
    }
}
