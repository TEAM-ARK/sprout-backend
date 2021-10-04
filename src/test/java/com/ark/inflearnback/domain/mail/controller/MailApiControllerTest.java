package com.ark.inflearnback.domain.mail.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;
import static org.springframework.web.reactive.function.BodyInserters.fromProducer;
import com.ark.inflearnback.configuration.http.model.form.HttpResponse;
import com.ark.inflearnback.configuration.security.model.entity.Member;
import com.ark.inflearnback.configuration.security.model.entity.Role;
import com.ark.inflearnback.configuration.security.repository.MemberRepository;
import com.ark.inflearnback.configuration.security.repository.RoleRepository;
import com.ark.inflearnback.configuration.security.type.RoleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MailApiControllerTest {
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private WebTestClient webTestClient;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    public MailApiControllerTest(final ObjectMapper objectMapper, final PasswordEncoder passwordEncoder, final MemberRepository memberRepository, final RoleRepository roleRepository) {
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }

    @BeforeEach
    void setUp(WebApplicationContext context) {
        webTestClient =
            MockMvcWebTestClient.bindToApplicationContext(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .configureClient()
                .build();
    }

    @Test
    @DisplayName("이메일 인증키 보내기")
    @Transactional
    void informationMailTest() throws Exception {
        final String email = "dyctdy@gmail.com";
        final String password = passwordEncoder.encode("AASHFKHQWFQYW#qwhfgqwf123!");
        final Role role = roleRepository.findByRoleType(RoleType.USER).get();
        memberRepository.saveAndFlush(Member.of(email, password, role, null, null, false));

        //given
        Mono<String> request = Mono.just(objectMapper.writeValueAsString(Map.of(
            "email", email
        )));
        String response = objectMapper.writeValueAsString(HttpResponse.of(OK, "mail send successful"));

        //when
        WebTestClient.ResponseSpec exchange = webTestClient.post()
            .uri("/api/v1/mail/informationMail")
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