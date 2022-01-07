package team.ark.sprout.common.config.extension;

import static team.ark.sprout.common.config.extension.Role.USER;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.ark.sprout.adapter.account.out.persistence.UrlResourcesRepository;
import team.ark.sprout.common.util.LogUtils;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlResourceService {
    private final UrlResourcesRepository urlResourcesRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resultMap = new LinkedHashMap<>();

        urlResourcesRepository.findAll()
            .forEach(resource -> {
                String url = resource.getUrl();
                String httpMethod = resource.getHttpMethod().name();
                SecurityConfig securityConfig = new SecurityConfig(USER.getAuthority());

                List<ConfigAttribute> configAttributes = Collections.singletonList(securityConfig);

                resultMap.put(new AntPathRequestMatcher(url, httpMethod), configAttributes);
            });

        resourcesSituation(resultMap);
        return resultMap;
    }

    private void resourcesSituation(LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resultMap) {
        LogUtils.info(log, "#################################################################################");
        LogUtils.info(log, "############################ Resources Situation ################################");
        LogUtils.info(log, "#################################################################################");
        for (Entry<RequestMatcher, List<ConfigAttribute>> entry : resultMap.entrySet()) {
            RequestMatcher key = entry.getKey();
            List<ConfigAttribute> value = entry.getValue();
            LogUtils.info(log, key + " : " + value);
        }
        LogUtils.info(log, "#################################################################################");
    }
}
