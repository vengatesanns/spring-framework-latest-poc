package com.vengatx.springai.custom.config;

import com.vengatx.springai.custom.model.CustomChatModel;
import com.vengatx.springai.custom.model.CustomEmbeddingModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomBeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ChatModel chatModel(CustomChatClientAPI customChatClientAPI) {
        return new CustomChatModel(customChatClientAPI);
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }

    @Bean
    public EmbeddingModel embeddingModel(CustomChatClientAPI customChatClientAPI) {
        return new CustomEmbeddingModel(customChatClientAPI);
    }

}
