import React from 'react';
import { Container } from 'react-bootstrap';

const Footer = () => {
  return (
    <footer className="footer mt-auto py-3 bg-light">
      <Container className="text-center">
        <span className="text-muted">
          &copy; {new Date().getFullYear()} AI Demo App | Built with Spring Boot, Spring AI, AWS Bedrock, and React
        </span>
      </Container>
    </footer>
  );
};

export default Footer;