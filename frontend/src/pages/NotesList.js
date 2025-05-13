import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button, Form, InputGroup, Spinner } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import NoteService from '../services/NoteService';

const NotesList = () => {
  const [notes, setNotes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');

  useEffect(() => {
    fetchNotes();
  }, []);

  const fetchNotes = async () => {
    try {
      setLoading(true);
      const response = await NoteService.getAllNotes();
      setNotes(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch notes. Please try again later.');
      console.error('Error fetching notes:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!searchQuery.trim()) {
      fetchNotes();
      return;
    }

    try {
      setLoading(true);
      const response = await NoteService.searchNotes(searchQuery);
      setNotes(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to search notes. Please try again later.');
      console.error('Error searching notes:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this note?')) {
      try {
        await NoteService.deleteNote(id);
        setNotes(notes.filter(note => note.id !== id));
      } catch (err) {
        setError('Failed to delete note. Please try again later.');
        console.error('Error deleting note:', err);
      }
    }
  };

  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  return (
    <Container>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1>Notes</h1>
        <Link to="/notes/new">
          <Button variant="primary">Create New Note</Button>
        </Link>
      </div>

      <Form onSubmit={handleSearch} className="mb-4">
        <InputGroup>
          <Form.Control
            type="text"
            placeholder="Search notes..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
          <Button type="submit" variant="outline-secondary">Search</Button>
          {searchQuery && (
            <Button 
              variant="outline-secondary" 
              onClick={() => {
                setSearchQuery('');
                fetchNotes();
              }}
            >
              Clear
            </Button>
          )}
        </InputGroup>
      </Form>

      {error && <div className="alert alert-danger">{error}</div>}

      {loading ? (
        <div className="text-center">
          <Spinner animation="border" role="status">
            <span className="visually-hidden">Loading...</span>
          </Spinner>
        </div>
      ) : notes.length === 0 ? (
        <div className="text-center">
          <p>No notes found. Create your first note!</p>
        </div>
      ) : (
        <Row xs={1} md={2} lg={3} className="g-4">
          {notes.map((note) => (
            <Col key={note.id}>
              <Card className="note-card h-100">
                <Card.Body>
                  <Card.Title>{note.title}</Card.Title>
                  <Card.Text>
                    {note.content.length > 100
                      ? `${note.content.substring(0, 100)}...`
                      : note.content}
                  </Card.Text>
                </Card.Body>
                <Card.Footer className="bg-white">
                  <small className="text-muted">
                    Last updated: {formatDate(note.updatedAt)}
                  </small>
                  <div className="mt-2 d-flex justify-content-between">
                    <Link to={`/notes/${note.id}`}>
                      <Button variant="outline-primary" size="sm">View</Button>
                    </Link>
                    <div>
                      <Link to={`/notes/edit/${note.id}`} className="me-2">
                        <Button variant="outline-secondary" size="sm">Edit</Button>
                      </Link>
                      <Button 
                        variant="outline-danger" 
                        size="sm"
                        onClick={() => handleDelete(note.id)}
                      >
                        Delete
                      </Button>
                    </div>
                  </div>
                </Card.Footer>
              </Card>
            </Col>
          ))}
        </Row>
      )}
    </Container>
  );
};

export default NotesList;