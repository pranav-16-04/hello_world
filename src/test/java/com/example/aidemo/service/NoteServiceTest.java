package com.example.aidemo.service;

import com.example.aidemo.model.Note;
import com.example.aidemo.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

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
    void getAllNotes_ShouldReturnAllNotes() {
        // Arrange
        List<Note> expectedNotes = Arrays.asList(testNote);
        when(noteRepository.findAll()).thenReturn(expectedNotes);

        // Act
        List<Note> actualNotes = noteService.getAllNotes();

        // Assert
        assertEquals(expectedNotes, actualNotes);
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void getNoteById_WithValidId_ShouldReturnNote() {
        // Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.of(testNote));

        // Act
        Note actualNote = noteService.getNoteById(1L);

        // Assert
        assertEquals(testNote, actualNote);
        verify(noteRepository, times(1)).findById(1L);
    }

    @Test
    void getNoteById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(noteRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> noteService.getNoteById(999L));
        verify(noteRepository, times(1)).findById(999L);
    }

    @Test
    void createNote_ShouldSetDatesAndSaveNote() {
        // Arrange
        Note inputNote = Note.builder()
                .title("New Note")
                .content("New content")
                .build();
        
        Note savedNote = Note.builder()
                .id(1L)
                .title("New Note")
                .content("New content")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        
        when(noteRepository.save(any(Note.class))).thenReturn(savedNote);

        // Act
        Note result = noteService.createNote(inputNote);

        // Assert
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertEquals(savedNote.getId(), result.getId());
        assertEquals(savedNote.getTitle(), result.getTitle());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void updateNote_WithValidId_ShouldUpdateNote() {
        // Arrange
        Note updatedDetails = Note.builder()
                .title("Updated Title")
                .content("Updated Content")
                .build();
        
        Note existingNote = Note.builder()
                .id(1L)
                .title("Original Title")
                .content("Original Content")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        
        Note expectedNote = Note.builder()
                .id(1L)
                .title("Updated Title")
                .content("Updated Content")
                .createdAt(existingNote.getCreatedAt())
                .updatedAt(any(Date.class))
                .build();
        
        when(noteRepository.findById(1L)).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class))).thenReturn(expectedNote);

        // Act
        Note result = noteService.updateNote(1L, updatedDetails);

        // Assert
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getContent());
        verify(noteRepository, times(1)).findById(1L);
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void deleteNote_WithValidId_ShouldDeleteNote() {
        // Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.of(testNote));
        doNothing().when(noteRepository).delete(testNote);

        // Act
        noteService.deleteNote(1L);

        // Assert
        verify(noteRepository, times(1)).findById(1L);
        verify(noteRepository, times(1)).delete(testNote);
    }

    @Test
    void searchNotes_ShouldReturnMatchingNotes() {
        // Arrange
        List<Note> titleResults = Arrays.asList(testNote);
        List<Note> contentResults = Arrays.asList();
        
        when(noteRepository.findByTitleContainingIgnoreCase("Test")).thenReturn(titleResults);
        when(noteRepository.findByContentContainingIgnoreCase("Test")).thenReturn(contentResults);

        // Act
        List<Note> results = noteService.searchNotes("Test");

        // Assert
        assertEquals(1, results.size());
        assertEquals(testNote, results.get(0));
        verify(noteRepository, times(1)).findByTitleContainingIgnoreCase("Test");
        verify(noteRepository, times(1)).findByContentContainingIgnoreCase("Test");
    }
}