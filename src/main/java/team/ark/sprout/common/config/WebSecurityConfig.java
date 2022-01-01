package team.ark.sprout.common.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.httpBasic().disable();
        http.csrf().disable();
        http.cors().disable();

        http.authorizeRequests()
            .mvcMatchers(HttpMethod.GET, "/docs/index.html").permitAll()
            .mvcMatchers(HttpMethod.POST, "/login", "/api/v1/accounts").permitAll()
            .anyRequest().authenticated();
    }
}
