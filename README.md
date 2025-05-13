# AI Demo Application

A full-stack application demonstrating the integration of Spring Boot with AWS Bedrock for AI capabilities, along with a React frontend.

## Project Structure

### Backend (Spring Boot)
- **Spring Boot**: Framework for creating stand-alone, production-grade Spring-based applications
- **Spring JPA**: Data persistence with JPA/Hibernate
- **Spring AI**: Integration with AI models
- **AWS Bedrock**: Access to foundation models like Claude from Anthropic

### Frontend (React)
- **React**: JavaScript library for building user interfaces
- **React Router**: Navigation between pages
- **React Bootstrap**: UI components
- **Axios**: HTTP client for API calls

## Features

### Notes Management
- Create, read, update, and delete notes
- Search notes by title or content

### AI Tools
- Text summarization
- Idea generation
- Text improvement
- Question answering

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js and npm
- AWS account with Bedrock access (for AI features)

### Running the Backend
1. Configure AWS credentials in `application.properties` or through environment variables
2. Run the Spring Boot application:
   ```
   ./mvnw spring-boot:run
   ```

### Running the Frontend
1. Navigate to the frontend directory:
   ```
   cd frontend
   ```
2. Install dependencies:
   ```
   npm install
   ```
3. Start the development server:
   ```
   npm start
   ```
4. Open [http://localhost:3000](http://localhost:3000) in your browser

## API Endpoints

### Notes API
- `GET /api/notes`: Get all notes
- `GET /api/notes/{id}`: Get note by ID
- `POST /api/notes`: Create a new note
- `PUT /api/notes/{id}`: Update a note
- `DELETE /api/notes/{id}`: Delete a note
- `GET /api/notes/search?query={query}`: Search notes

### AI API
- `POST /api/ai/summarize`: Summarize text
- `POST /api/ai/ideas`: Generate ideas
- `POST /api/ai/improve`: Improve text
- `POST /api/ai/answer`: Answer questions