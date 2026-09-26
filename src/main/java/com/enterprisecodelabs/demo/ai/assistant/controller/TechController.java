package com.enterprisecodelabs.demo.ai.assistant.controller;

import com.enterprisecodelabs.demo.ai.assistant.dto.ChatApi.ChatRequest;
import com.enterprisecodelabs.demo.ai.assistant.dto.TechConcept;
import com.enterprisecodelabs.demo.ai.assistant.service.TechService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/tech/")
public class TechController {

    private final TechService techService;

    public TechController(TechService techService) {
        this.techService = techService;
    }

    @PostMapping("/explain")
    public TechConcept explain(@RequestBody ChatRequest chatRequest){
        return techService.explainTechConcept(chatRequest);
    }
}
