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
        String prompt = "Hi";
        String response = chatClientBuilder.build().prompt(prompt).call().content();
        System.out.println(response);
    }
}
