package com.example.aidemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.bedrock.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AIServiceTest {

    @Mock
    private AnthropicChatModel anthropicChatModel;

    @Mock
    private ChatResponse chatResponse;

    @Mock
    private Generation generation;

    @Mock
    private Message responseMessage;

    @InjectMocks
    private AIService aiService;

    @BeforeEach
    void setUp() {
        when(chatResponse.getResult()).thenReturn(generation);
        when(generation.getOutput()).thenReturn(responseMessage);
    }

    @Test
    void generateSummary_ShouldReturnSummary() {
        // Arrange
        String inputText = "This is a long text that needs to be summarized.";
        String expectedSummary = "Summary of the text.";
        
        when(responseMessage.getContent()).thenReturn(expectedSummary);
        when(anthropicChatModel.call(any(Prompt.class))).thenReturn(chatResponse);

        // Act
        String actualSummary = aiService.generateSummary(inputText);

        // Assert
        assertEquals(expectedSummary, actualSummary);
        verify(anthropicChatModel, times(1)).call(any(Prompt.class));
    }

    @Test
    void generateIdeas_ShouldReturnIdeas() {
        // Arrange
        String topic = "AI in healthcare";
        String expectedIdeas = "1. AI for diagnosis\n2. AI for treatment planning";
        
        when(responseMessage.getContent()).thenReturn(expectedIdeas);
        when(anthropicChatModel.call(any(Prompt.class))).thenReturn(chatResponse);

        // Act
        String actualIdeas = aiService.generateIdeas(topic);

        // Assert
        assertEquals(expectedIdeas, actualIdeas);
        verify(anthropicChatModel, times(1)).call(any(Prompt.class));
    }

    @Test
    void improveText_ShouldReturnImprovedText() {
        // Arrange
        String inputText = "This text needs improvement.";
        String expectedImprovedText = "This text has been significantly improved.";
        
        when(responseMessage.getContent()).thenReturn(expectedImprovedText);
        when(anthropicChatModel.call(any(Prompt.class))).thenReturn(chatResponse);

        // Act
        String actualImprovedText = aiService.improveText(inputText);

        // Assert
        assertEquals(expectedImprovedText, actualImprovedText);
        verify(anthropicChatModel, times(1)).call(any(Prompt.class));
    }

    @Test
    void answerQuestion_ShouldReturnAnswer() {
        // Arrange
        String question = "What is artificial intelligence?";
        String expectedAnswer = "Artificial intelligence is the simulation of human intelligence by machines.";
        
        when(responseMessage.getContent()).thenReturn(expectedAnswer);
        when(anthropicChatModel.call(any(Prompt.class))).thenReturn(chatResponse);

        // Act
        String actualAnswer = aiService.answerQuestion(question);

        // Assert
        assertEquals(expectedAnswer, actualAnswer);
        verify(anthropicChatModel, times(1)).call(any(Prompt.class));
    }
}