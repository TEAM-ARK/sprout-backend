package team.ark.sprout.common.config.extension;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class UrlMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap;

    public UrlMetadataSource(UrlResourcesMapFactoryBean urlResourcesMapFactoryBean) {
        this.resourceMap = urlResourcesMapFactoryBean.getObject();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (resourceMap != null) {
            for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : resourceMap.entrySet()) {
                RequestMatcher matcher = entry.getKey();
                if (matcher.matches(((FilterInvocation) object).getRequest())) {
                    return entry.getValue();
                }
            }
        }

        // Never executed
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : resourceMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
