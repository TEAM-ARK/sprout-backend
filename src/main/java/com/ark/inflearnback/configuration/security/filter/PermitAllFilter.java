package com.ark.inflearnback.configuration.security.filter;

import static java.util.Objects.nonNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class PermitAllFilter extends FilterSecurityInterceptor {

    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

    private final boolean observeOncePerRequest = true;
    private final List<RequestMatcher> permitAllRequestMatchers = new ArrayList<>();

    public PermitAllFilter(final String... permitAllResources) {
        for (String resource : permitAllResources) {
            String[] strings = resource.split(",");
            if (strings.length == 2) {
                /*
                strings[0] = uri patterns
                strings[1] = http methods (ex. get, post, delete, put ...)
                 */
                permitAllRequestMatchers.add(new AntPathRequestMatcher(strings[0], strings[1]));
                continue;
            }
            permitAllRequestMatchers.add(new AntPathRequestMatcher(resource));
        }
    }

    public void invoke(final FilterInvocation fi) throws IOException, ServletException {
        if ((nonNull(fi.getRequest()))
            && (nonNull(fi.getRequest().getAttribute(FILTER_APPLIED)))
            && observeOncePerRequest) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } else {
            if (nonNull(fi.getRequest()) && observeOncePerRequest) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }
            final InterceptorStatusToken token = beforeInvocation(fi);
            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.finallyInvocation(token);
            }
            super.afterInvocation(token, null);
        }
    }

    @Override
    protected InterceptorStatusToken beforeInvocation(final Object object) {
        if (isPermit((FilterInvocation) object)) {
            return null;
        }
        return super.beforeInvocation(object);
    }

    private boolean isPermit(final FilterInvocation object) {
        return permitAllRequestMatchers.stream()
            .anyMatch(requestMatcher -> requestMatcher.matches(object.getRequest()));
    }

}
