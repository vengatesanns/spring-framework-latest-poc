package com.vengatx.springai.custom.model;

import com.vengatx.springai.custom.config.CustomChatClientAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CustomChatModel implements ChatModel {

    private final CustomChatClientAPI customChatClientAPI;

    public CustomChatModel(CustomChatClientAPI customChatClientAPI) {
        this.customChatClientAPI = customChatClientAPI;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        String response = customChatClientAPI.invokeChatAPI(prompt);
        AssistantMessage assistantMessage =
                new AssistantMessage(response);
        Generation generation = new Generation(assistantMessage);
        return new ChatResponse(List.of(generation));
    }
}
