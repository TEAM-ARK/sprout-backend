package team.ark.sprout.domain.account;

import lombok.Value;

@Value(staticConstructor = "of")
public class AccountBasic {
    String email;
    String nickname;
    String password;
}
