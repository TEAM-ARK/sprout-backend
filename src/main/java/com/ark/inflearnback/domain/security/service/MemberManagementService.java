package com.ark.inflearnback.domain.security.service;

import com.ark.inflearnback.domain.security.dto.SignUpRequestDto;
import com.ark.inflearnback.domain.security.repository.MemberRepository;
import com.ark.inflearnback.domain.security.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberManagementService {
    private MemberRepository memberRepository;
    private RoleRepository roleRepository;

    @Transactional
    public boolean signUp(final SignUpRequestDto request){
        return false;
    }
}
