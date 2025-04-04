package com.vnextgen.spring_ai.ollama;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.OllamaChatModel;
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

    private final OllamaChatModel chatModel;
    private final EmbeddingModel embeddingModel;
//    private final VectorStore vectorStore;

    @Autowired
    public LlamaModelChatController(OllamaChatModel chatModel, EmbeddingModel embeddingModel) {
        this.chatModel = chatModel;
        this.embeddingModel = embeddingModel;
//        this.vectorStore = vectorStore;

    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "Hi") String message) {
        return Map.of("generation", this.chatModel.call(message));
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
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }


/*    @GetMapping("/ai/vectore/search")
    public List<Document> getFromVector() {
        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

        vectorStore.add(documents);
        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());
        return results;
    }*/

}
