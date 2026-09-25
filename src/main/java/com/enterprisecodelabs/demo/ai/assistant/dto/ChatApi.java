package com.enterprisecodelabs.demo.ai.assistant.dto;

public interface ChatApi {

    record ChatRequest(String message, String conversationId){}
    record ChatResponse(String answer, String conversationId){}
}
