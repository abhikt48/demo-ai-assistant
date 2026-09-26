package com.enterprisecodelabs.demo.ai.assistant.dto;

import java.util.List;

public record TechConcept(
        String topic,
        String category,
        Difficulty difficulty,
        String summary,
        List<String> useCases,
        String recommendation

) {
    enum Difficulty { EASY, MEDIUM, HARD }
}
