package com.ark.inflearnback.config.security;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Oauth2Test {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void google로그인 () throws Exception {
        given()
                .when()
                .redirects().follow(false) // 리다이렉트 방지
                .get("/")
                .then()
                .statusCode(302);

        //FIXME: 바로 이동할 경우에는 이 헤더 테스트를 수행
        //                .header("Location", containsString("https://accounts.google.com/o/oauth2/auth"));
    }
}