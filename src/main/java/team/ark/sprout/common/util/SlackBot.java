package team.ark.sprout.common.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public final class SlackBot {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${slack.subscription-notification}")
    private String url;

    public void sendMessage(String message) {
        restTemplate.exchange(url, HttpMethod.POST, makeMessage(message), String.class);
    }

    private HttpEntity<Map<String, Object>> makeMessage(String message) {
        Map<String, Object> request = new HashMap<>();
        request.put("text", message);
        return new HttpEntity<>(request);
    }
}
