package team.ark.sprout.adapter.in.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.ark.sprout.domain.account.Account;
import team.ark.sprout.domain.account.AccountBasic;
import team.ark.sprout.domain.account.AccountDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpForm {
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @Pattern(
        regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,15}$",
        message = "닉네임은 공백 없이 영문 or 숫자 or _ or - 으로 이루어진 3~15 자리의 문자열이여야 합니다"
    )
    private String nickname;

    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*])(?!.*(.)\\1\\1\\1)[0-9a-zA-Z!@#$%&*]{12,32}$",
        message = "비밀번호는 공백 없이 영문/숫자/특수문자로 이루어진 12~32 자리의 문자열이어야 합니다"
    )
    private String password;

    public static SignUpForm create(String email, String nickname, String password) {
        return new SignUpForm(email, nickname, password);
    }

    public Account mapToDomain() {
        AccountBasic accountBasic = AccountBasic.of(email, nickname, password);
        AccountDetails accountDetails = AccountDetails.create();
        return Account.create(accountBasic, accountDetails);
    }
}
