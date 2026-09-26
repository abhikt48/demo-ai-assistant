package com.enterprisecodelabs.demo.ai.assistant.controller;

import com.enterprisecodelabs.demo.ai.assistant.dto.ChatApi.ChatResponse;
import com.enterprisecodelabs.demo.ai.assistant.dto.ChatApi.ChatRequest;
import com.enterprisecodelabs.demo.ai.assistant.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest chatRequest){
        return chatService.chat(chatRequest);
    }
}
