package com.vnextgen.spring_ai.ollama;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LlamaModelChatController {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private EmbeddingModel embeddingModel;


    @GetMapping("/ai/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "Hi") String message) {
        String response = this.chatModel.call(message);
        return response;
    }

    @GetMapping("ai/custom-response")
    public ChatResponse getResponse() throws JsonProcessingException {
        String jsonSchema = """
                {
                    "type": "object",
                    "properties": {
                        "steps": {
                            "type": "array",
                            "items": {
                                "type": "object",
                                "properties": {
                                    "explanation": { "type": "string" },
                                    "output": { "type": "string" }
                                },
                                "required": ["explanation", "output"],
                                "additionalProperties": false
                            }
                        },
                        "final_answer": { "type": "string" }
                    },
                    "required": ["steps", "final_answer"],
                    "additionalProperties": false
                }
                """;

        Prompt prompt = new Prompt("how can I solve 8x + 7 = -23", OllamaOptions.builder().model(OllamaModel.LLAMA3_2.getName()).format(new ObjectMapper().readValue(jsonSchema, Map.class)).build());
        SystemMessage systemMessage;
        UserMessage userMessage;
        ChatResponse response = this.chatModel.call(prompt);
        return response;
    }

    @GetMapping("/ai/embedding")
    public EmbeddingResponse embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(message));
        return embeddingResponse;
    }

}
