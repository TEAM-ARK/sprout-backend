package com.ark.inflearnback.domain.security;

import com.ark.inflearnback.domain.security.factory.UrlResourcesMapFactoryBean;
import com.ark.inflearnback.domain.security.filter.PermitAllFilter;
import com.ark.inflearnback.domain.security.filter.RestLoginProcessingFilter;
import com.ark.inflearnback.domain.security.filter.UrlFilterInvocationSecurityMetadataSource;
import com.ark.inflearnback.domain.security.handler.CustomAuthenticationFailureHandler;
import com.ark.inflearnback.domain.security.handler.CustomAuthenticationSuccessHandler;
import com.ark.inflearnback.domain.security.provider.CustomAuthenticationProvider;
import com.ark.inflearnback.domain.security.repository.MemberRepository;
import com.ark.inflearnback.domain.security.service.CustomUserDetailsService;
import com.ark.inflearnback.domain.security.service.SecurityResourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Order(1)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] PERMIT_ALL_RESOURCES = {"/api/v1/member,POST"};

    private final SecurityResourceService securityResourceService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()

                .authorizeRequests(
                        authorize -> authorize.anyRequest().authenticated()
                                .expressionHandler(expressionHandler())
                )

                .formLogin().disable()

                .addFilterAt(restLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
    }
    //
    //    protected JsonLoginFilter getAuthenticationFilter() {
    //        JsonLoginFilter filter = new JsonLoginFilter();
    //        try {
    //            filter.setFilterProcessesUrl("/api/v1/member/login");
    //            filter.setAuthenticationManager(this.authenticationManagerBean());
    //            filter.setUsernameParameter("email");
    //            filter.setPasswordParameter("password");
    //            filter.setAuthenticationSuccessHandler(new AuthenticationHandler());
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        return filter;
    //    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> expressionHandler() {
        final DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        webSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
        return webSecurityExpressionHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return securityResourceService.assembleAuthorityHierarchy();
    }

    @Bean
    public PermitAllFilter customFilterSecurityInterceptor() throws Exception {
        final PermitAllFilter permitAllFilter = new PermitAllFilter(PERMIT_ALL_RESOURCES);
        permitAllFilter.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        permitAllFilter.setAccessDecisionManager(affirmativeBased());
        permitAllFilter.setAuthenticationManager(authenticationManagerBean());
        return permitAllFilter;
    }

    private AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(getAccessDecisionVoters());
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        final List<AccessDecisionVoter<?>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(roleVoter());
        return accessDecisionVoters;
    }

    @Bean
    public AccessDecisionVoter<?> roleVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

    @Bean
    public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() {
        return new UrlFilterInvocationSecurityMetadataSource(urlResourcesMapFactoryBean().getObject(), securityResourceService);
    }

    private UrlResourcesMapFactoryBean urlResourcesMapFactoryBean() {
        final UrlResourcesMapFactoryBean urlResourcesMapFactoryBean = new UrlResourcesMapFactoryBean();
        urlResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);
        return urlResourcesMapFactoryBean;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(passwordEncoder(), userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(memberRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public RestLoginProcessingFilter restLoginProcessingFilter() throws Exception {
        RestLoginProcessingFilter restLoginProcessingFilter = new RestLoginProcessingFilter();
        restLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        restLoginProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        restLoginProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return restLoginProcessingFilter;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(objectMapper);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler(objectMapper);
    }
}
