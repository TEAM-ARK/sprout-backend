package com.ark.inflearnback.domain.member.service;

import com.ark.inflearnback.configuration.exception.CertificateNotEqualException;
import com.ark.inflearnback.configuration.exception.DuplicateEmailException;
import com.ark.inflearnback.configuration.exception.NotExistEmailException;
import com.ark.inflearnback.configuration.exception.RoleNotFoundException;
import com.ark.inflearnback.configuration.security.model.entity.Member;
import com.ark.inflearnback.configuration.security.model.entity.Role;
import com.ark.inflearnback.configuration.security.repository.MemberRepository;
import com.ark.inflearnback.configuration.security.repository.RoleRepository;
import com.ark.inflearnback.configuration.security.type.RoleType;
import com.ark.inflearnback.domain.member.model.form.PasswordForm;
import com.ark.inflearnback.domain.member.model.form.SignForm;
import com.ark.inflearnback.domain.member.utils.Certification;
import com.ark.inflearnback.domain.member.utils.MailEnum;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private Certification certification;

    @Transactional
    public void signUp(final SignForm request) throws DuplicateEmailException {
        log.info("Sign-up request. email: {}", request.getEmail());
        verifyEmail(request);
        signUpComplete(request);
        log.info("Sign-up completed.");
    }

    public SimpleMailMessage informationEmail(String email) {
        log.info("Sending for information email.");
        findPasswordEmailValidation(email);
        return createInformationEmail(email);
    }

    public void checkKey(String key) {
        if (certification.isNotEqualTo(key)) {
            log.error("certificate key is not equal");
            throw new CertificateNotEqualException("certificate key is not equal");
        }
    }

    @Transactional
    public void changePassword(PasswordForm passwordForm) {
        Member member = memberRepository.findByEmail(certification.getEmail())
            .orElseThrow(() -> {
                throw new NotExistEmailException("이메일이 존재하지 않습니다.");
            });

        member.setPassword(passwordForm.encodePassword(passwordEncoder).getPassword());
    }

    private void verifyEmail(final SignForm request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            log.warn("Cannot sign-up because the email is already in use.");
            throw new DuplicateEmailException("email is already in use.");
        }
    }

    private void signUpComplete(final SignForm request) {
        memberRepository.save(Member.of(request.encodePassword(passwordEncoder), findRoleMember()));
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

    private void findPasswordEmailValidation(String email) {
        if (!memberRepository.existsByEmail(email)) {
            log.error("Cannot found member email");
            throw new NotExistEmailException("email is not exist.");
        }
    }

    private SimpleMailMessage createInformationEmail(String email) {
        final SimpleMailMessage message = new SimpleMailMessage();
        final StringBuilder sb = new StringBuilder();
        certification = Certification.of(email);

        message.setFrom(MailEnum.FROM_ADDRESS.getValue());
        message.setTo(certification.getEmail());
        message.setSubject(MailEnum.AUTHENTICATION_TITLE.getValue());

        sb.append("인증키는 ");
        sb.append(certification.getCertificateNumber());
        sb.append("입니다.");

        message.setText(sb.toString());
        log.info("email sending completed.");
        return message;
    }

}
