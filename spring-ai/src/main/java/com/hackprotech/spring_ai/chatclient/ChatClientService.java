package com.hackprotech.spring_ai.chatclient;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatClientService {

    private final ChatClient chatClient;

    public ChatClientService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    public ChatResponse getChatResponse(String query) {
        return chatClient.prompt(query).call().chatResponse();
    }


    public String getChatMessage(String query) {
        return chatClient.prompt(query).call().content();
    }


    public String getChatMessageWithPrompt(String query) {
        Message systemMessage = new SystemMessage("""
                You are a sales manager of bike showroom and always suggest the good bikes with all parameters considered
                And you should give why this bike is recommended in all aspects""");
        Message userMessage = new UserMessage(query);
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        return chatClient.prompt(prompt).call().content();
    }


    public String getChatMessageWithPromptTemplate(String query, String brandName, String fuelType) {
        String systemMessageStr = """
                You are a good sales manager of Brand: {brandName} Bike Showroom.
                Suggest good bikes with all parameters considered like price, engine cc, features., etc
                And based on this fuel type: {fuelType} as well
                """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemMessageStr);
        Map<String, Object> systemParams = new HashMap<>();
        systemParams.put("brandName", brandName);
        systemParams.put("fuelType", fuelType);
        Message systemMessage = systemPromptTemplate.createMessage(systemParams);
        Message userMessage = new UserMessage(query);
        List<Message> messageList = new ArrayList<>();
        messageList.add(userMessage);
        messageList.add(systemMessage);
        Prompt prompt = new Prompt(messageList);
        return chatClient.prompt(prompt).call().content();
    }


}
