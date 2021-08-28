package com.ark.inflearnback.domain.member.controller;

import com.ark.inflearnback.config.model.HttpResponse;
import com.ark.inflearnback.domain.member.dto.SignUpRequestDto;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.Schema.schema;
import static com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import static org.springframework.web.reactive.function.BodyInserters.fromProducer;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberManagementApiControllerTest {
    private final ObjectMapper objectMapper;

    public MemberManagementApiControllerTest(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        webTestClient = MockMvcWebTestClient.bindToApplicationContext(context)
                .configureClient()
                .filter(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception {
        // given
        Mono<String> request = Mono.just(objectMapper.writeValueAsString(
                SignUpRequestDto.of("test@email.com", "AASHFKHQWFQYWqwhfgqwf123!"))
        );

        String expected = objectMapper.writeValueAsString(HttpResponse.of(HttpStatus.OK, "회원가입 완료."));

        // when
        ResponseSpec exchange = webTestClient.post()
                .uri("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(fromProducer(request, String.class))
                .exchange();

        // then
        exchange.expectStatus().isOk()
                .expectBody().json(expected)
                .consumeWith(document("회원가입",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("회원")
                                        .summary("신규 회원 정보 생성")
                                        .description("신규 회원 정보를 생성한다")
                                        .requestSchema(schema("SignUpRequestDto"))
                                        .responseSchema(schema("HttpResponse"))
                                        .requestFields(
                                                fieldWithPath("email").description("이메일"),
                                                fieldWithPath("password").description("비밀번호")
                                        )
                                        .responseFields(
                                                fieldWithPath("responseCode").description("응답코드"),
                                                fieldWithPath("responseMessage").description("응답메시지"),
                                                fieldWithPath("responseBody").description("응답바디")
                                        )
                                        .build()
                        )));
    }
}
