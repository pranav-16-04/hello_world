import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/notes';

class NoteService {
  getAllNotes() {
    return axios.get(API_BASE_URL);
  }

  getNoteById(noteId) {
    return axios.get(`${API_BASE_URL}/${noteId}`);
  }

  createNote(note) {
    return axios.post(API_BASE_URL, note);
  }

  updateNote(noteId, note) {
    return axios.put(`${API_BASE_URL}/${noteId}`, note);
  }

  deleteNote(noteId) {
    return axios.delete(`${API_BASE_URL}/${noteId}`);
  }

  searchNotes(query) {
    return axios.get(`${API_BASE_URL}/search?query=${query}`);
  }
}

export default new NoteService();