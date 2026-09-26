package com.enterprisecodelabs.demo.ai.assistant.service;

import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SearchService {

    private final OpenAiChatModel openAiChatModel;

    public SearchService(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    public String search(String input){
        Generation chatResult = openAiChatModel.call(new Prompt(input)).getResult();
        return  chatResult.getOutput().getText();
    }

    public Flux<String> searchStream(String input){
        return openAiChatModel
                .stream(new Prompt(input))
                .map(chatResponse -> chatResponse.getResult().getOutput().getText());
    }
}
