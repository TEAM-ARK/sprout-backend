package com.ark.sprout.service;


import com.ark.sprout.entity.Member;
import com.ark.sprout.entity.Role;
import com.ark.sprout.exception.DuplicateEmailException;
import com.ark.sprout.exception.RoleNotFoundException;
import com.ark.sprout.form.SignForm;
import com.ark.sprout.repository.MemberRepository;
import com.ark.sprout.repository.RoleRepository;
import com.ark.sprout.type.RoleType;
import com.ark.sprout.util.LogUtils;
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
        log.info(LogUtils.info("Sign-up request", request));
        verifyEmail(request);
        signUpComplete(request);
        log.info(LogUtils.info("Sign-up completed."));
    }

    private void verifyEmail(final SignForm request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            log.warn(LogUtils.warn("Cannot sign-up because the email is already in use."));
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
            log.error(LogUtils.error(RoleType.USER + " not found or not active. please check the ROLE table."));
            throw new RoleNotFoundException("internal server error.");
        }
    }

}
