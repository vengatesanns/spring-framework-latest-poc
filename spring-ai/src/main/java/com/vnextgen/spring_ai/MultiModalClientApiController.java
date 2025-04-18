package com.vnextgen.spring_ai;


import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultiModalClientApiController {


    @Autowired
    private ChatModel chatModel;


}
