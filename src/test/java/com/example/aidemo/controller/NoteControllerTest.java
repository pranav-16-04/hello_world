package com.example.aidemo.controller;

import com.example.aidemo.model.Note;
import com.example.aidemo.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Note testNote;

    @BeforeEach
    void setUp() {
        testNote = Note.builder()
                .id(1L)
                .title("Test Note")
                .content("This is a test note")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    @Test
    void getAllNotes_ShouldReturnAllNotes() throws Exception {
        // Arrange
        List<Note> notes = Arrays.asList(testNote);
        when(noteService.getAllNotes()).thenReturn(notes);

        // Act & Assert
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Note")))
                .andExpect(jsonPath("$[0].content", is("This is a test note")));

        verify(noteService, times(1)).getAllNotes();
    }

    @Test
    void getNoteById_WithValidId_ShouldReturnNote() throws Exception {
        // Arrange
        when(noteService.getNoteById(1L)).thenReturn(testNote);

        // Act & Assert
        mockMvc.perform(get("/api/notes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Note")))
                .andExpect(jsonPath("$.content", is("This is a test note")));

        verify(noteService, times(1)).getNoteById(1L);
    }

    @Test
    void createNote_ShouldReturnCreatedNote() throws Exception {
        // Arrange
        Note inputNote = Note.builder()
                .title("New Note")
                .content("New content")
                .build();

        Note createdNote = Note.builder()
                .id(2L)
                .title("New Note")
                .content("New content")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        when(noteService.createNote(any(Note.class))).thenReturn(createdNote);

        // Act & Assert
        mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputNote)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("New Note")))
                .andExpect(jsonPath("$.content", is("New content")));

        verify(noteService, times(1)).createNote(any(Note.class));
    }

    @Test
    void updateNote_WithValidId_ShouldReturnUpdatedNote() throws Exception {
        // Arrange
        Note updateNote = Note.builder()
                .title("Updated Note")
                .content("Updated content")
                .build();

        Note updatedNote = Note.builder()
                .id(1L)
                .title("Updated Note")
                .content("Updated content")
                .createdAt(testNote.getCreatedAt())
                .updatedAt(new Date())
                .build();

        when(noteService.updateNote(eq(1L), any(Note.class))).thenReturn(updatedNote);

        // Act & Assert
        mockMvc.perform(put("/api/notes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateNote)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Updated Note")))
                .andExpect(jsonPath("$.content", is("Updated content")));

        verify(noteService, times(1)).updateNote(eq(1L), any(Note.class));
    }

    @Test
    void deleteNote_WithValidId_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(noteService).deleteNote(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/notes/1"))
                .andExpect(status().isNoContent());

        verify(noteService, times(1)).deleteNote(1L);
    }

    @Test
    void searchNotes_ShouldReturnMatchingNotes() throws Exception {
        // Arrange
        List<Note> notes = Arrays.asList(testNote);
        when(noteService.searchNotes("test")).thenReturn(notes);

        // Act & Assert
        mockMvc.perform(get("/api/notes/search").param("query", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Note")));

        verify(noteService, times(1)).searchNotes("test");
    }
}