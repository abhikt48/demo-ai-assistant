package com.enterprisecodelabs.demo.ai.assistant.controller;

import com.enterprisecodelabs.demo.ai.assistant.service.SearchService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai/")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(path = "/search", produces = MediaType.TEXT_PLAIN_VALUE)
    public String search(@RequestParam String input){
        return searchService.search(input);
    }

    @GetMapping(path = "/search/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> searchStream(@RequestParam String input){
        return searchService.searchStream(input);
    }
}
