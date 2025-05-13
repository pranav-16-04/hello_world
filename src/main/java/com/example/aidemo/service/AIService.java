package com.example.aidemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.bedrock.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIService {

    private final AnthropicChatModel anthropicChatModel;

    public String generateSummary(String text) {
        String prompt = "Please summarize the following text in a concise manner: " + text;
        return generateResponse(prompt);
    }

    public String generateIdeas(String topic) {
        String prompt = "Generate 5 creative ideas related to the following topic: " + topic;
        return generateResponse(prompt);
    }

    public String improveText(String text) {
        String prompt = "Please improve the following text by making it more clear, concise, and engaging: " + text;
        return generateResponse(prompt);
    }

    public String answerQuestion(String question) {
        String prompt = "Please answer the following question in a helpful and informative way: " + question;
        return generateResponse(prompt);
    }

    private String generateResponse(String message) {
        Prompt prompt = new Prompt(List.of(new UserMessage(message)));
        ChatResponse response = anthropicChatModel.call(prompt);
        return response.getResult().getOutput().getContent();
    }
}