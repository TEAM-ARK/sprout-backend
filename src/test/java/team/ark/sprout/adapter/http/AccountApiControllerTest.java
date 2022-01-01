package team.ark.sprout.adapter.http;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import team.ark.sprout.adapter.http.AccountApiController.SignUpForm;

@DisplayName("회원 API")
class AccountApiControllerTest extends ControllerTests {
    @Test
    @WithMockUser
    @DisplayName("회원가입")
    void regist() throws Exception {
        // ...given
        SignUpForm request = SignUpForm.of("test@email.com", "nickname123", "passwordPassword123!");
        String expected = objectMapper.writeValueAsString(ResponseData.create(HttpStatus.OK, "가입되었습니다"));

        // ...when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/accounts")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        );

        // ...then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().json(expected))
            .andDo(MockMvcRestDocumentation.document("account/post-200",
                documentRequest(),
                documentResponse(),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("HTTP 메시지"),
                    fieldWithPath("data").type(JsonFieldType.STRING).description("요청에 대한 응답 메시지")
                )
            ));
    }
}
