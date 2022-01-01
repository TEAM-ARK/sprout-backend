package team.ark.sprout.adapter.http;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@DisplayName("회원 API")
class AccountApiControllerTest extends ControllerTests {
    @Test
    @DisplayName("회원가입 - 200")
    void regist_200() throws Exception {
        // ...given
        SignUpForm request = SignUpForm.create("test@email.com", "nickname123", "passwordPassword123!");
        String expected = objectMapper.writeValueAsString(ResponseData.create(OK, "가입되었습니다"));

        // ...when
        ResultActions resultActions = post(request);

        // ...then
        resultActionsExpect(resultActions, status().isOk(), expected, "account/post-200");
    }

    @Test
    @DisplayName("회원가입 - 이메일 400")
    void regist_email_400() throws Exception {
        // ...given
        SignUpForm request = SignUpForm.create("testemail.com", "nickname123", "passwordPassword123!");
        String expected = objectMapper.writeValueAsString(ResponseData.create(BAD_REQUEST, "[email] 올바른 이메일 형식이 아닙니다"));

        // ...when
        ResultActions resultActions = post(request);

        // ...then
        resultActionsExpect(resultActions, status().isBadRequest(), expected, "account/post-email-400");
    }

    @Test
    @DisplayName("회원가입 - 닉네임 400")
    void regist_nickname_400() throws Exception {
        // ...given
        SignUpForm request = SignUpForm.create("test@email.com", "nn", "passwordPassword123!");
        String expected = objectMapper.writeValueAsString(ResponseData.create(BAD_REQUEST, "[nickname] 닉네임은 한글, 영어, 숫자, 언더바, 하이픈으로 이루어진 3~15자리의 문자열이여야 합니다"));

        // ...when
        ResultActions resultActions = post(request);

        // ...then
        resultActionsExpect(resultActions, status().isBadRequest(), expected, "account/post-nickname-400");
    }

    @Test
    @DisplayName("회원가입 - 비밀번호 400")
    void regist_password_400() throws Exception {
        // ...given
        SignUpForm request = SignUpForm.create("test@email.com", "nickname123", "passwordPassword123");
        String expected = objectMapper.writeValueAsString(ResponseData.create(BAD_REQUEST, "[password] 비밀번호는 공백 없이 영문/숫자/특수문자로 이루어진 12~32자리의 문자열이어야 합니다"));

        // ...when
        ResultActions resultActions = post(request);

        // ...then
        resultActionsExpect(resultActions, status().isBadRequest(), expected, "account/post-password-400");
    }

    private ResultActions post(SignUpForm request) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
        );
    }

    private void resultActionsExpect(ResultActions resultActions, ResultMatcher status, String expected, String identifier) throws Exception {
        resultActions
            .andExpect(status)
            .andExpect(content().json(expected))
            .andDo(MockMvcRestDocumentation.document(identifier,
                documentRequest(),
                documentResponse(),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        .attributes(getFormatAttribute("#@가 포함된 이메일 형식#")),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                        .attributes(getFormatAttribute("#한글, 영어, 숫자, 언더바, 하이픈으로 이루어진 3~15자리 문자열#")),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        .attributes(getFormatAttribute("#공백 없이 영문/숫자/특수문자로 이루어진 12~32자리의 문자열#"))
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("HTTP 메시지"),
                    fieldWithPath("data").type(JsonFieldType.STRING).description("요청에 대한 응답 메시지")
                )
            ));
    }
}
