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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberManagementService {
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(final SignUpRequestDto request) throws DuplicateEmailException {
        log.info(String.format("%s 회원 가입 진행.", request.getEmail()));
        verifyEmail(request);
        signUpComplete(request);
        log.info(String.format("%s 회원 가입 완료.", request.getEmail()));
    }

    private void verifyEmail(final SignUpRequestDto request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }
    }

    private void signUpComplete(final SignUpRequestDto request) {
        memberRepository.save(Member.of(request.getEmail(), passwordEncoder.encode(request.getPassword()), findRoleMember()));
    }

    private Role findRoleMember() {
        try {
            return roleRepository.findByRoleType(RoleType.ROLE_MEMBER)
                    .orElseThrow(RoleNotFoundException::new);
        }
        catch (RoleNotFoundException e) {
            log.error(String.format("%s를 찾을 수 없거나 활성화 돼있지 않습니다. ROLE 테이블을 확인하세요.", RoleType.ROLE_MEMBER));
            throw new RoleNotFoundException("내부 서버 에러.");
        }
    }
}
