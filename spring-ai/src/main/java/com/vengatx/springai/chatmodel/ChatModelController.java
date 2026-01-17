package com.vengatx.springai.chatmodel;


import com.vengatx.springai.chatclient.records.ChatTemplateRecord;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChatModelController {


    @Autowired
    private OllamaChatModel ollamaChatModel;


    @PostMapping("chatmodel/prompttemplate")
    public List<Generation> getChatResponse(@RequestBody ChatTemplateRecord chatTemplateRecord) {
        String systemMessageStr = """
                You are a good sales manager of Brand: {brandName} Bike Showroom.
                Suggest good bikes with all parameters considered like price, engine cc, features., etc
                And based on this fuel type: {fuelType} as well
                """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemMessageStr);
        Map<String, Object> systemParams = new HashMap<>();
        systemParams.put("brandName", chatTemplateRecord.brandName());
        systemParams.put("fuelType", chatTemplateRecord.fuelType());
        Message systemMessage = systemPromptTemplate.createMessage(systemParams);
        Message userMessage = new UserMessage(chatTemplateRecord.query());

        List<Message> messageList = new ArrayList<>();
        messageList.add(userMessage);
        messageList.add(systemMessage);

        Prompt prompt = new Prompt(messageList);
        return ollamaChatModel.call(prompt).getResults();
    }
}
