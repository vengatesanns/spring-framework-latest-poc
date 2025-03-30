package com.vnextgen.spring_ai.ollama;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LlamaModelChatController {

    private final OllamaChatModel chatModel;

    @Autowired
    public LlamaModelChatController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "Hi") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

}
