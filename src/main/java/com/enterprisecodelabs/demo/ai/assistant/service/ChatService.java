package com.enterprisecodelabs.demo.ai.assistant.service;

import com.enterprisecodelabs.demo.ai.assistant.dto.ChatApi.ChatRequest;
import com.enterprisecodelabs.demo.ai.assistant.dto.ChatApi.ChatResponse;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    private final OpenAiChatModel openAiChatModel;
    private final ChatMemory chatMemory;

    public ChatService(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        this.openAiChatModel = openAiChatModel;
        this.chatMemory = chatMemory;
    }

    public ChatResponse chat(ChatRequest chatRequest){
        String conversationId = chatRequest.conversationId() !=null ?
                chatRequest.conversationId() : UUID.randomUUID().toString();

        chatMemory.add(conversationId, new UserMessage(chatRequest.message()));

        List<Message> messages = chatMemory.get(conversationId);
        String response = openAiChatModel.call(new Prompt(messages)).getResult().getOutput().getText();
        chatMemory.add(conversationId, new AssistantMessage(response));

        return new ChatResponse(response, conversationId);
    }

}
