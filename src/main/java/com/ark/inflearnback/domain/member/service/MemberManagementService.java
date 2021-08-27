package com.ark.inflearnback.domain.member.service;

import com.ark.inflearnback.domain.member.dto.SignUpRequestDto;
import com.ark.inflearnback.domain.member.exception.DuplicateEmailException;
import com.ark.inflearnback.domain.member.exception.RoleNotFoundException;
import com.ark.inflearnback.domain.security.model.Member;
import com.ark.inflearnback.domain.security.model.Role;
import com.ark.inflearnback.domain.security.repository.MemberRepository;
import com.ark.inflearnback.domain.security.repository.RoleRepository;
import com.ark.inflearnback.domain.security.type.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberManagementService {
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private SignUpRequestDto request;

    @Transactional
    public void signUp(final SignUpRequestDto request) throws DuplicateEmailException {
        verifyEmail(request);
        signUpComplete(request);
    }

    private void verifyEmail(final SignUpRequestDto request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("이미 가입한 이메일입니다.");
        }
    }

    private void signUpComplete(final SignUpRequestDto request) {
        memberRepository.save(Member.of(request, findRoleMember()));
    }

    private Role findRoleMember() {
        try {
            return roleRepository.findByRoleType(RoleType.ROLE_MEMBER)
                    .orElseThrow(RoleNotFoundException::new);
        }
        catch (RoleNotFoundException e) {
            log.error("ROLE_MEMBER를 찾을 수 없거나 활성화 돼있지 않습니다. ROLE 테이블을 확인하세요.");
            throw new RoleNotFoundException("내부 서버 에러.");
        }
    }
}
