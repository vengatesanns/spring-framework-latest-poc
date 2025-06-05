package com.hackprotech.spring_ai.cmdrunner;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ChatCmdLineRunner implements CommandLineRunner {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Override
    public void run(String... args) throws Exception {
        String prompt = "Suggest some bikes which is best for city ride and under 2lacs on road and engine cc between 150 and 200 max?";
        String response = chatClientBuilder.build().prompt(prompt).call().content();
        System.out.println(response);
    }
}
