package com.ark.inflearnback.domain.member.service;

import com.ark.inflearnback.domain.member.model.form.SignForm;
import com.ark.inflearnback.configuration.exception.DuplicateEmailException;
import com.ark.inflearnback.configuration.exception.RoleNotFoundException;
import com.ark.inflearnback.configuration.security.model.entity.Member;
import com.ark.inflearnback.configuration.security.model.entity.Role;
import com.ark.inflearnback.configuration.security.repository.MemberRepository;
import com.ark.inflearnback.configuration.security.repository.RoleRepository;
import com.ark.inflearnback.configuration.security.type.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(final SignForm request) throws DuplicateEmailException {
        log.info("Sign-up request. email: {}", request.getEmail());
        verifyEmail(request);
        signUpComplete(request);
        log.info("Sign-up completed.");
    }

    private void verifyEmail(final SignForm request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            log.warn("Cannot sign-up because the email is already in use.");
            throw new DuplicateEmailException("email is already in use.");
        }
    }

    private void signUpComplete(final SignForm request) {
        memberRepository.save(Member.of(request.encodePassword(passwordEncoder), findRoleMember(), null, null, false));
    }

    private Role findRoleMember() {
        try {
            return roleRepository.findByRoleType(RoleType.USER)
                .orElseThrow(RoleNotFoundException::new);
        } catch (RoleNotFoundException e) {
            log.error("{} not found or not active. please check the ROLE table.", RoleType.USER);
            throw new RoleNotFoundException("internal server error.");
        }
    }

}
