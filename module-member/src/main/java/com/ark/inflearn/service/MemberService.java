package com.ark.inflearn.service;


import com.ark.inflearn.entity.Member;
import com.ark.inflearn.entity.Role;
import com.ark.inflearn.exception.DuplicateEmailException;
import com.ark.inflearn.exception.RoleNotFoundException;
import com.ark.inflearn.form.SignForm;
import com.ark.inflearn.repository.MemberRepository;
import com.ark.inflearn.repository.RoleRepository;
import com.ark.inflearn.type.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Member member = Member.of(request.encodePassword(passwordEncoder), findRoleMember());
        memberRepository.save(member);
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
