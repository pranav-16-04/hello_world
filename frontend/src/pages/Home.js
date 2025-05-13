import React from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <Container>
      <Row className="mb-4">
        <Col>
          <div className="text-center py-5">
            <h1>Welcome to AI Demo App</h1>
            <p className="lead">
              A Spring Boot application with Spring JPA, Spring AI, and AWS Bedrock integration
            </p>
          </div>
        </Col>
      </Row>

      <Row>
        <Col md={6} className="mb-4">
          <Card className="h-100">
            <Card.Body>
              <Card.Title>Notes Management</Card.Title>
              <Card.Text>
                Create, read, update, and delete notes with our simple and intuitive interface.
                Search through your notes to find exactly what you're looking for.
              </Card.Text>
              <Link to="/notes">
                <Button variant="primary">Go to Notes</Button>
              </Link>
            </Card.Body>
          </Card>
        </Col>

        <Col md={6} className="mb-4">
          <Card className="h-100">
            <Card.Body>
              <Card.Title>AI Tools</Card.Title>
              <Card.Text>
                Leverage the power of AWS Bedrock and Spring AI to summarize text,
                generate creative ideas, improve your writing, and answer questions.
              </Card.Text>
              <Link to="/ai-tools">
                <Button variant="primary">Explore AI Tools</Button>
              </Link>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row className="mt-4">
        <Col>
          <Card className="bg-light">
            <Card.Body>
              <Card.Title>About This Project</Card.Title>
              <Card.Text>
                This application demonstrates the integration of Spring Boot with AWS Bedrock
                for AI capabilities, along with a React frontend for a seamless user experience.
                The backend uses Spring JPA for data persistence and Spring AI for AI model integration.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Home;