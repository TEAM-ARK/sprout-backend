package com.ark.inflearnback.domain.mail.service;

import com.ark.inflearnback.configuration.exception.NotExistEmailException;
import com.ark.inflearnback.configuration.security.model.entity.Member;
import com.ark.inflearnback.configuration.security.repository.MemberRepository;
import com.ark.inflearnback.domain.member.model.form.PasswordForm;
import com.ark.inflearnback.domain.member.utils.MailEnum;
import com.ark.inflearnback.domain.member.utils.TokenGenerator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final TokenGenerator tokenGenerator;

    @Value("${spring.mail.username}")
    private String fromAddress;

    public void informationEmail(String email) {
        log.info("Sending for information email.");
        findPasswordEmailValidation(email);
        createInformationEmail(email);
    }

    private void findPasswordEmailValidation(String email) {
        if (!memberRepository.existsByEmail(email)) {
            log.error("Cannot found member email");
            throw new NotExistEmailException("email is not exist.");
        }
    }

    @Transactional
    public void changePassword(PasswordForm passwordForm) {
        Member member = memberRepository.findByEmail(passwordForm.getEmail())
            .orElseThrow(() -> {
                throw new NotExistEmailException("Email is not exist");
            });

        member.updatePassword(passwordForm.encodePassword(passwordEncoder).getPassword());
    }

    private void createInformationEmail(String email) {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setFrom(fromAddress);
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email, false));
            mimeMessage.setSubject(MailEnum.AUTHENTICATION_TITLE.getValue());
            mimeMessage.setContent(mailForm(email), "text/html; charset=utf-8");
        } catch (MessagingException e) {
            log.error("sending email error: {}", e.getMessage());
        }
        javaMailSender.send(mimeMessage);
        log.info("email sending completed.");
    }

    private String mailForm(String email) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>이메일 변경링크 페이지</title></head><body>")
            .append("<div>")
            .append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding: 32px 0 32px 0;background-color: #f1f3f5;\">")
            .append("<tbody><tr><td>")
            .append("<table align=\"center\" width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding: 0 20px 0 20px;background-color: #fff;\">")
            .append("<tbody><tr><td style=\"padding: 20px 0 21px 0;\">")
            .append(
                "<table><tbody><tr><td width=\"100\"><img width=\"100\" height=\"19\" src=\"https://cdn.inflearn.com/assets/email_templates/assets/logo-eng-green-h.png\" loading=\"lazy\"></td><td width=\"460\" style=\"font-size: 13px; line-height: 1.38; letter-spacing: -0.3px; color: #abb0b5; text-align: right; vertical-align: middle;\">나의 성장을 돕는 IT 실무지식 플랫폼</td></tr>")
            .append("</tbody></table></td></tr>")
            .append("<tr><td style=\"border-bottom: 1px solid #f1f3f5;\"></td></tr>")
            .append("<tr><td style=\"overflow: hidden; padding: 32px 0 32px 0; font-size: 26px; font-weight: 500; line-height: 1.31; letter-spacing: -0.3px; color: #1b1c1d; word-break: break-all;\">비밀번호 변경 링크입니다.</td></tr>")
            .append("<tr><td style=\"font-size: 15px; line-height: 1.47; letter-spacing: -0.3px; color: #1b1c1d;\">")
            .append("비밀번호 변경하기 버튼을 눌러 비밀번호를 변경해주세요.<br> 본인의 요청이 아닐 경우에는 Team-ark에 문의해주세요.")
            .append("</td></tr>")
            .append("<tr><td style=\"padding: 32px 0 0 0;\"></td></tr>")
            .append("<tr>")
            .append("<td style=\"text-align: center;\">")

            .append("<a href=\"/password?email=")
            .append(email)
            .append("&token=")
            .append(createToken())
            .append(
                "\" style=\"font-size: 15px; letter-spacing: -0.3px; font-weight: bold; background-color: #00c471;")
            .append("color: #fff; display: inline-block; : 180px; : 48px; line-: 48px; text-align: center; text-decoration: none; border-radius: 4px; cursor: default;\" rel=\"noreferrer noopener\" target=\"_blank\">비밀번호 변경하기</a>")

            .append("</td></tr>")
            .append("<tr>")
            .append("<td style=\"padding: 32px 0 0 0;\"></td>")
            .append("</tr>")
            .append("<tr>")
            .append("<td style=\"border-top: 1px solid #f1f3f5;\"></td>")
            .append("</tr>")
            .append("<tr>")
            .append("<td style=\"padding: 21px 0 20px 0;\">")
            .append("<table>")
            .append("<tbody><tr><td style=\"font-size: 13px; line-height: 1.38; letter-spacing: -0.3px; color: #abb0b5;\">우리는 성장기회의 평등을 추구합니다.</td></tr>")
            .append("<tr><td style=\"padding: 17px 0 0 0;\"></td></tr>")
            .append("<tr><td style=\"font-size: 13px; line-height: 1.38; letter-spacing: -0.3px; color: #abb0b5;\">©Team-Ark. All rights reserved.</td></tr>")
            .append("</tbody>")
            .append("</table></td></tr></tbody></table></td></tr></tbody></table></div>")
            .append("</body></html>");
        return stringBuilder.toString();
    }

    private String createToken() {
        return tokenGenerator.generate();
    }

}
