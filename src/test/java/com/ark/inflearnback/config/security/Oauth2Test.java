package com.ark.inflearnback.config.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class Oauth2Test {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void google로그인 () throws Exception {
        mockMvc.perform(get("http://localhost:8080/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}