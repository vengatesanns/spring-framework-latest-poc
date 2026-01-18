package com.vengatx.springai.custom.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom")
public class CustomChatController {

    private final ChatClient chatClient;

    public CustomChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String getChat() {
        return chatClient.prompt("Hello").call().content();
    }

}
