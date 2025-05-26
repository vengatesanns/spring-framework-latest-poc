package com.hackprotech.spring_ai.chatclient;


import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatClientController {


    private final ChatClientService chatClientService;


    public ChatClientController(ChatClientService chatClientService) {
        this.chatClientService = chatClientService;
    }

    @GetMapping("/ai/chat-response")
    public ChatResponse getChatResponse(@RequestParam("query") String query) {
        return chatClientService.getChatResponse(query);
    }

    @GetMapping("/ai/chat-message")
    public String getChatMessage(@RequestParam("query") String query) {
        return chatClientService.getChatMessage(query);
    }


}
