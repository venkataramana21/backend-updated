package com.example.ChatBot.Controller;

import com.example.ChatBot.Service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConversationController {
    @Autowired
    AIService aiService;

    @PostMapping("/chat")
    public String chat(@RequestBody String prompt) {
        return this.aiService.chat(prompt);
    }
}
