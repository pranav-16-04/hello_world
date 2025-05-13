import React, { useState } from 'react';
import { Container, Row, Col, Card, Form, Button, Spinner } from 'react-bootstrap';
import AIService from '../services/AIService';

const AITools = () => {
  const [activeTab, setActiveTab] = useState('summarize');
  const [inputText, setInputText] = useState('');
  const [result, setResult] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!inputText.trim()) {
      setError('Please enter some text.');
      return;
    }

    setLoading(true);
    setError(null);
    
    try {
      let response;
      
      switch (activeTab) {
        case 'summarize':
          response = await AIService.summarizeText(inputText);
          break;
        case 'ideas':
          response = await AIService.generateIdeas(inputText);
          break;
        case 'improve':
          response = await AIService.improveText(inputText);
          break;
        case 'answer':
          response = await AIService.answerQuestion(inputText);
          break;
        default:
          throw new Error('Invalid tool selected');
      }
      
      setResult(response.data.result);
    } catch (err) {
      setError('An error occurred while processing your request. Please try again later.');
      console.error('AI service error:', err);
    } finally {
      setLoading(false);
    }
  };

  const getPlaceholderText = () => {
    switch (activeTab) {
      case 'summarize':
        return 'Enter the text you want to summarize...';
      case 'ideas':
        return 'Enter a topic to generate ideas about...';
      case 'improve':
        return 'Enter the text you want to improve...';
      case 'answer':
        return 'Enter your question...';
      default:
        return 'Enter text...';
    }
  };

  const getButtonText = () => {
    switch (activeTab) {
      case 'summarize':
        return 'Summarize';
      case 'ideas':
        return 'Generate Ideas';
      case 'improve':
        return 'Improve Text';
      case 'answer':
        return 'Get Answer';
      default:
        return 'Submit';
    }
  };

  return (
    <Container className="mt-4">
      <h1 className="mb-4">AI Tools</h1>
      
      <Row className="mb-4">
        <Col>
          <div className="d-flex flex-wrap">
            {['summarize', 'ideas', 'improve', 'answer'].map((tab) => (
              <Card 
                key={tab}
                className={`ai-tool-card m-2 ${activeTab === tab ? 'active' : ''}`}
                style={{ width: '12rem', cursor: 'pointer' }}
                onClick={() => {
                  setActiveTab(tab);
                  setResult('');
                }}
              >
                <Card.Body className="text-center">
                  <Card.Title>
                    {tab === 'summarize' && 'Summarize Text'}
                    {tab === 'ideas' && 'Generate Ideas'}
                    {tab === 'improve' && 'Improve Text'}
                    {tab === 'answer' && 'Answer Questions'}
                  </Card.Title>
                </Card.Body>
              </Card>
            ))}
          </div>
        </Col>
      </Row>

      <Row>
        <Col>
          <Card>
            <Card.Body>
              <Card.Title>
                {activeTab === 'summarize' && 'Text Summarization'}
                {activeTab === 'ideas' && 'Idea Generation'}
                {activeTab === 'improve' && 'Text Improvement'}
                {activeTab === 'answer' && 'Question Answering'}
              </Card.Title>
              
              <Card.Text className="mb-4">
                {activeTab === 'summarize' && 'Enter a long text and get a concise summary.'}
                {activeTab === 'ideas' && 'Enter a topic and get creative ideas related to it.'}
                {activeTab === 'improve' && 'Enter text and get an improved, more engaging version.'}
                {activeTab === 'answer' && 'Ask a question and get a helpful answer.'}
              </Card.Text>

              {error && <div className="alert alert-danger">{error}</div>}

              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                  <Form.Control
                    as="textarea"
                    rows={6}
                    value={inputText}
                    onChange={(e) => setInputText(e.target.value)}
                    placeholder={getPlaceholderText()}
                  />
                </Form.Group>

                <div className="d-grid">
                  <Button 
                    variant="primary" 
                    type="submit"
                    disabled={loading || !inputText.trim()}
                  >
                    {loading ? (
                      <>
                        <Spinner
                          as="span"
                          animation="border"
                          size="sm"
                          role="status"
                          aria-hidden="true"
                          className="me-2"
                        />
                        Processing...
                      </>
                    ) : (
                      getButtonText()
                    )}
                  </Button>
                </div>
              </Form>

              {result && (
                <div className="mt-4">
                  <h5>Result:</h5>
                  <div className="ai-result">{result}</div>
                </div>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default AITools;