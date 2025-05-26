package com.hackprotech.spring_ai.chatclient;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatClientService {

    private final ChatClient chatClient;

    public ChatClientService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    public ChatResponse getChatResponse(String query) {
        return chatClient.prompt().user(query).call().chatResponse();
    }


    public String getChatMessage(String query) {
        return chatClient.prompt().user(query).call().content();
    }

}
