package com.hackprotech.spring_ai.chatclient;


import com.hackprotech.spring_ai.chatclient.records.ChatTemplateRecord;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/ai/chat-message-with-prompt")
    public String getChatMessageWithPrompt(@RequestParam("query") String query) {
        return chatClientService.getChatMessageWithPrompt(query);
    }

    @PostMapping("/ai/chat-message-with-prompttemplate")
    public String getChatMessageWithPrompt(@RequestBody ChatTemplateRecord chatTemplateRecord) {
        return chatClientService.getChatMessageWithPromptTemplate(chatTemplateRecord.query(), chatTemplateRecord.brandName(), chatTemplateRecord.fuelType());
    }

}
