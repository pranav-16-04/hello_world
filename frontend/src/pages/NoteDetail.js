import React, { useState, useEffect } from 'react';
import { Container, Card, Button, Spinner } from 'react-bootstrap';
import { useParams, Link, useNavigate } from 'react-router-dom';
import NoteService from '../services/NoteService';

const NoteDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [note, setNote] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchNote = async () => {
      try {
        setLoading(true);
        const response = await NoteService.getNoteById(id);
        setNote(response.data);
        setError(null);
      } catch (err) {
        setError('Failed to fetch note. It may have been deleted or does not exist.');
        console.error('Error fetching note:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchNote();
  }, [id]);

  const handleDelete = async () => {
    if (window.confirm('Are you sure you want to delete this note?')) {
      try {
        await NoteService.deleteNote(id);
        navigate('/notes');
      } catch (err) {
        setError('Failed to delete note. Please try again later.');
        console.error('Error deleting note:', err);
      }
    }
  };

  const formatDate = (dateString) => {
    const options = { 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  if (loading) {
    return (
      <Container className="text-center mt-5">
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
      </Container>
    );
  }

  if (error) {
    return (
      <Container className="mt-5">
        <div className="alert alert-danger">{error}</div>
        <Link to="/notes">
          <Button variant="primary">Back to Notes</Button>
        </Link>
      </Container>
    );
  }

  return (
    <Container className="mt-4">
      <div className="mb-4">
        <Link to="/notes">
          <Button variant="outline-secondary">&larr; Back to Notes</Button>
        </Link>
      </div>

      {note && (
        <Card>
          <Card.Header className="d-flex justify-content-between align-items-center">
            <h2>{note.title}</h2>
            <div>
              <Link to={`/notes/edit/${note.id}`} className="me-2">
                <Button variant="outline-primary">Edit</Button>
              </Link>
              <Button variant="outline-danger" onClick={handleDelete}>Delete</Button>
            </div>
          </Card.Header>
          <Card.Body>
            <Card.Text className="note-content">{note.content}</Card.Text>
          </Card.Body>
          <Card.Footer>
            <small className="text-muted">
              Created: {formatDate(note.createdAt)}
              <br />
              Last updated: {formatDate(note.updatedAt)}
            </small>
          </Card.Footer>
        </Card>
      )}
    </Container>
  );
};

export default NoteDetail;