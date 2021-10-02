package com.ark.inflearnback.domain.member.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.Schema.schema;
import static com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper.document;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import static org.springframework.web.reactive.function.BodyInserters.fromProducer;
import com.ark.inflearnback.configuration.http.model.form.HttpResponse;
import com.ark.inflearnback.configuration.security.model.entity.Member;
import com.ark.inflearnback.configuration.security.model.entity.Role;
import com.ark.inflearnback.configuration.security.repository.MemberRepository;
import com.ark.inflearnback.configuration.security.repository.RoleRepository;
import com.ark.inflearnback.configuration.security.type.RoleType;
import com.ark.inflearnback.domain.member.model.form.SignForm;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {

    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private WebTestClient webTestClient;

    public MemberApiControllerTest(final ObjectMapper objectMapper, final PasswordEncoder passwordEncoder, final MemberRepository memberRepository, final RoleRepository roleRepository) {
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        webTestClient =
            MockMvcWebTestClient.bindToApplicationContext(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .configureClient()
                .filter(
                    documentationConfiguration(restDocumentation).snippets().withEncoding("UTF-8"))
                .build();
    }

    @Test
    @Transactional
    @DisplayName("회원가입")
    void signUp() throws Exception {
        // given
        Mono<String> request =
            Mono.just(
                objectMapper.writeValueAsString(
                    SignForm.of("test@email.com", "AASHFKHQWFQYW#qwhfgqwf123!")));

        String expected =
            objectMapper.writeValueAsString(HttpResponse.of(HttpStatus.OK, "sign-up successful"));

        // when
        ResponseSpec exchange =
            webTestClient
                .post()
                .uri("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(fromProducer(request, String.class))
                .exchange();

        // then
        exchange
            .expectStatus()
            .isOk()
            .expectBody()
            .json(expected)
            .consumeWith(
                document(
                    "회원가입",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("회원")
                            .summary("신규 회원 정보 생성")
                            .description("신규 회원 정보를 생성한다")
                            .requestSchema(schema("SignRequestDto"))
                            .responseSchema(schema("HttpResponse"))
                            .requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호"))
                            .responseFields(
                                fieldWithPath("responseCode").description("응답코드"),
                                fieldWithPath("responseMessage").description("응답메시지"),
                                fieldWithPath("responseBody").description("응답바디"))
                            .build())));
    }

    @Test
    @Transactional
    @DisplayName("로그인")
    void login() throws Exception {
        // given
        final String email = "test@email.com";
        final String password = passwordEncoder.encode("AASHFKHQWFQYW#qwhfgqwf123!");
        final Role role = roleRepository.findByRoleType(RoleType.USER).get();
        memberRepository.saveAndFlush(Member.of(email, password, role, null, null, false));

        Mono<String> request =
            Mono.just(
                objectMapper.writeValueAsString(
                    SignForm.of("test@email.com", "AASHFKHQWFQYW#qwhfgqwf123!")));

        String expected =
            objectMapper.writeValueAsString(HttpResponse.of(HttpStatus.OK, "log-in successful"));

        // when
        WebTestClient.ResponseSpec exchange =
            webTestClient
                .post()
                .uri("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(fromProducer(request, String.class))
                .exchange();

        // then
        exchange
            .expectStatus()
            .isOk()
            .expectBody()
            .json(expected)
            .consumeWith(
                document(
                    "로그인",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    resource(
                        ResourceSnippetParameters.builder()
                            .tag("회원")
                            .summary("로그인")
                            .description("로그인 성공")
                            .requestSchema(schema("SignRequestDto"))
                            .responseSchema(schema("HttpResponse"))
                            .requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호"))
                            .responseFields(
                                fieldWithPath("responseCode").description("응답코드"),
                                fieldWithPath("responseMessage").description("응답메시지"),
                                fieldWithPath("responseBody").description("응답바디"))
                            .build())));
    }

    @Test
    @DisplayName("이메일 인증키 보내기")
    void informationMailTest() throws Exception {
        final String email = "test@email.com";
        final String password = passwordEncoder.encode("AASHFKHQWFQYW#qwhfgqwf123!");
        final Role role = roleRepository.findByRoleType(RoleType.USER).get();
        memberRepository.saveAndFlush(Member.of(email, password, role, null, null, false));

        //given
        Mono<String> request = Mono.just(objectMapper.writeValueAsString(Map.of(
            "email", "test@email.com"
        )));
        String response = objectMapper.writeValueAsString(HttpResponse.of(OK, "mail send successful"));

        //when
        WebTestClient.ResponseSpec exchange = webTestClient.post()
            .uri("/api/v1/member/informationMail")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(fromProducer(request, String.class))
            .exchange();

        //then
        exchange
            .expectStatus().isOk()
            .expectBody()
            .json(response);
    }

}
