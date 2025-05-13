package com.example.aidemo.repository;

import com.example.aidemo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
    List<Note> findByTitleContainingIgnoreCase(String title);
    
    List<Note> findByContentContainingIgnoreCase(String content);
}