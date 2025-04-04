package com.vnextgen.spring_ai;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatClientExampleController {

    private final ChatClient chatClient;

    public ChatClientExampleController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("ai/chat")
    String getChatResponse(@RequestBody String userInput) {
        return chatClient.prompt().user(userInput).call().content();
    }

}
