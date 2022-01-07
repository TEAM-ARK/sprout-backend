package team.ark.sprout.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import team.ark.sprout.common.config.extension.OAuth2SuccessHandler;

@Profile("prod")
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityProdConfig extends WebSecurityConfigurerAdapter {
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors(AbstractHttpConfigurer::disable)
            .headers(headers -> headers
                .frameOptions().disable()
            )
            .authorizeRequests(requests -> requests
                .mvcMatchers(HttpMethod.GET, "/docs/index.html").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .oauth2Login(login -> login
                .successHandler(oAuth2SuccessHandler)
            );
    }
}
