import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/ai';

class AIService {
  summarizeText(text) {
    return axios.post(`${API_BASE_URL}/summarize`, { text });
  }

  generateIdeas(topic) {
    return axios.post(`${API_BASE_URL}/ideas`, { text: topic });
  }

  improveText(text) {
    return axios.post(`${API_BASE_URL}/improve`, { text });
  }

  answerQuestion(question) {
    return axios.post(`${API_BASE_URL}/answer`, { text: question });
  }
}

export default new AIService();