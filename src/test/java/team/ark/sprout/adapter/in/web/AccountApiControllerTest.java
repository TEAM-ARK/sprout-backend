package team.ark.sprout.adapter.in.web;

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
import org.springframework.transaction.annotation.Transactional;

@DisplayName("회원 API")
class AccountApiControllerTest extends ControllerTests {
    @Test
    @Transactional
    @DisplayName("회원가입 - 성공")
    void regist1() throws Exception {
        // ...given
        SignUpForm request = SignUpForm.create("test@email.com", "nickname123", "passwordPassword123!");
        String expected = objectMapper.writeValueAsString(ResponseData.create(OK, "가입되었습니다"));

        // ...when
        ResultActions resultActions = post(request);

        // ...then
        resultActionsExpect(resultActions, status().isOk(), expected, "account/post-200");
    }

    @Test
    @Transactional
    @DisplayName("회원가입 - 이메일 형식 오류")
    void regist2() throws Exception {
        // ...given
        SignUpForm request = SignUpForm.create("testemail.com", "nickname123", "passwordPassword123!");
        String expected = objectMapper.writeValueAsString(ResponseData.create(BAD_REQUEST, "[email] 올바른 이메일 형식이 아닙니다"));

        // ...when
        ResultActions resultActions = post(request);

        // ...then
        resultActionsExpect(resultActions, status().isBadRequest(), expected, "account/post-email-400-1");
    }

    @Test
    @Transactional
    @DisplayName("회원가입 - 이미 사용중인 이메일")
    void regist5() throws Exception {
        // ...given
        SignUpForm request1 = SignUpForm.create("test@email.com", "nickname1231", "passwordPassword123!");
        SignUpForm request2 = SignUpForm.create("test@email.com", "nickname1232", "passwordPassword123!");
        String expected = objectMapper.writeValueAsString(ResponseData.create(BAD_REQUEST, "[email] 이미 사용중인 이메일입니다"));

        // ...when
        post(request1);
        ResultActions resultActions = post(request2);

        // ...then
        resultActionsExpect(resultActions, status().isBadRequest(), expected, "account/post-email-400-2");
    }

    @Test
    @Transactional
    @DisplayName("회원가입 - 닉네임 형식 오류")
    void regist3() throws Exception {
        // ...given
        SignUpForm request = SignUpForm.create("test@email.com", "nn", "passwordPassword123!");
        String expected = objectMapper.writeValueAsString(ResponseData.create(BAD_REQUEST, "[nickname] 닉네임은 언더바, 하이픈을 제외한 문자로 이루어진 3~15자리의 문자열이여야 합니다"));

        // ...when
        ResultActions resultActions = post(request);

        // ...then
        resultActionsExpect(resultActions, status().isBadRequest(), expected, "account/post-nickname-400-1");
    }

    @Test
    @Transactional
    @DisplayName("회원가입 - 이미 사용중인 닉네임")
    void regist6() throws Exception {
        // ...given
        SignUpForm request1 = SignUpForm.create("test1@email.com", "nickname123", "passwordPassword123!");
        SignUpForm request2 = SignUpForm.create("test2@email.com", "nickname123", "passwordPassword123!");
        String expected = objectMapper.writeValueAsString(ResponseData.create(BAD_REQUEST, "[nickname] 이미 사용중인 닉네임입니다"));

        // ...when
        post(request1);
        ResultActions resultActions = post(request2);

        // ...then
        resultActionsExpect(resultActions, status().isBadRequest(), expected, "account/post-nickname-400-2");
    }

    @Test
    @Transactional
    @DisplayName("회원가입 - 비밀번호 형식 오류")
    void regist4() throws Exception {
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
