package com.ark.inflearnback.domain.security.filter;

import com.ark.inflearnback.domain.security.service.SecurityResourceService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.*;

public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final SecurityResourceService resourceService;
    private final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap;

    public UrlFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourcesMap, SecurityResourceService resourceService) {
        this.requestMap = resourcesMap;
        this.resourceService = resourceService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (Objects.nonNull(requestMap)) {
            for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
                RequestMatcher matcher = entry.getKey();
                if (matcher.matches(((FilterInvocation) object).getRequest())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        final Set<ConfigAttribute> allAttributes = new HashSet<>();
        for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public void reload() {
        final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> reloadedRequestMap = resourceService.getResourceList();
        final Iterator<Map.Entry<RequestMatcher, List<ConfigAttribute>>> iterator = reloadedRequestMap.entrySet().iterator();
        requestMap.clear();
        while (iterator.hasNext()) {
            Map.Entry<RequestMatcher, List<ConfigAttribute>> entry = iterator.next();
            requestMap.put(entry.getKey(), entry.getValue());
        }
    }
}
