package com.enterprisecodelabs.demo.ai.assistant.service;

import com.enterprisecodelabs.demo.ai.assistant.dto.ChatApi.ChatRequest;
import com.enterprisecodelabs.demo.ai.assistant.dto.TechConcept;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TechService {

    private final OpenAiChatModel openAiChatModel;
    private final ChatMemory chatMemory;

    public TechService(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        this.openAiChatModel = openAiChatModel;
        this.chatMemory = chatMemory;
    }

    public TechConcept explainTechConcept(ChatRequest chatRequest){
        BeanOutputConverter<TechConcept> outputConverter = new BeanOutputConverter<>(TechConcept.class);

        String conversationId = chatRequest.conversationId() != null ?
                chatRequest.conversationId() : UUID.randomUUID().toString();

        Message systemMessage = new SystemPromptTemplate("""
                You are a technical educator. Given a technology name, classify it
                and produce a structured explanation. Category should be a short
                domain label like Messaging, Database, API Management, or Cloud.
                """).createMessage();

        Message userMessage = PromptTemplate.builder()
                .template("Explain {topic} {format}")
                .variables(Map.of(
                        "topic", chatRequest.message(),
                        "format", outputConverter.getFormat()
                )).build().createMessage();

        chatMemory.add(conversationId, userMessage);

        List<Message> messages = chatMemory.get(conversationId);
        Prompt prompt = new Prompt(messages);

        String response = openAiChatModel.call(prompt).getResult().getOutput().getText();
        chatMemory.add(conversationId, new AssistantMessage(response));

        return outputConverter.convert(response);
    }
}
