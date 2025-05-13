package com.example.aidemo.controller;

import com.example.aidemo.dto.AIRequest;
import com.example.aidemo.dto.AIResponse;
import com.example.aidemo.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // For React frontend
public class AIController {

    private final AIService aiService;

    @PostMapping("/summarize")
    public ResponseEntity<AIResponse> summarizeText(@RequestBody AIRequest request) {
        String summary = aiService.generateSummary(request.getText());
        return ResponseEntity.ok(new AIResponse(summary));
    }

    @PostMapping("/ideas")
    public ResponseEntity<AIResponse> generateIdeas(@RequestBody AIRequest request) {
        String ideas = aiService.generateIdeas(request.getText());
        return ResponseEntity.ok(new AIResponse(ideas));
    }

    @PostMapping("/improve")
    public ResponseEntity<AIResponse> improveText(@RequestBody AIRequest request) {
        String improvedText = aiService.improveText(request.getText());
        return ResponseEntity.ok(new AIResponse(improvedText));
    }

    @PostMapping("/answer")
    public ResponseEntity<AIResponse> answerQuestion(@RequestBody AIRequest request) {
        String answer = aiService.answerQuestion(request.getText());
        return ResponseEntity.ok(new AIResponse(answer));
    }
}