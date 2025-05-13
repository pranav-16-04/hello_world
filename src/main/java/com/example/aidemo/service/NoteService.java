package com.example.aidemo.service;

import com.example.aidemo.model.Note;
import com.example.aidemo.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id: " + id));
    }

    public List<Note> searchNotes(String query) {
        List<Note> titleResults = noteRepository.findByTitleContainingIgnoreCase(query);
        List<Note> contentResults = noteRepository.findByContentContainingIgnoreCase(query);
        
        // Combine results and remove duplicates
        titleResults.removeAll(contentResults);
        titleResults.addAll(contentResults);
        
        return titleResults;
    }

    public Note createNote(Note note) {
        Date now = new Date();
        note.setCreatedAt(now);
        note.setUpdatedAt(now);
        return noteRepository.save(note);
    }

    public Note updateNote(Long id, Note noteDetails) {
        Note note = getNoteById(id);
        
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        note.setUpdatedAt(new Date());
        
        return noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        Note note = getNoteById(id);
        noteRepository.delete(note);
    }
}