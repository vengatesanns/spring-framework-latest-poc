package com.vengatx.springai.custom.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class CustomChatClientAPI {

    @Value("${genai.openai.key}")
    private String openAIAPIKey;

    @Value("${genai.openai.chat-url}")
    private String chatUrl;

    private final RestTemplate restTemplate;

    public CustomChatClientAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String invokeChatAPI(Prompt prompt) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(openAIAPIKey);
        Map<String, String> reqBody = Map.ofEntries(
                Map.entry("model", "gpt-5"),
                Map.entry("input", prompt.getUserMessage().getText())
        );
        HttpEntity<Map<String, String>> request = new HttpEntity<>(reqBody, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(chatUrl, HttpMethod.POST, request, String.class);
        return extractAssistantText(response.getBody());
    }

    private String extractAssistantText(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode outputArray = root.path("output");
            for (JsonNode item : outputArray) {
                if ("message".equals(item.path("type").asText())) {

                    JsonNode contentArray = item.path("content");

                    for (JsonNode content : contentArray) {
                        if ("output_text".equals(content.path("type").asText())) {
                            return content.path("text").asText();
                        }
                    }
                }
            }
            throw new IllegalStateException("No assistant text found in response");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse OpenAI response", e);
        }
    }


}
