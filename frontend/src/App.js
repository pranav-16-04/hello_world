import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

// Components
import Header from './components/Header';
import Footer from './components/Footer';

// Pages
import Home from './pages/Home';
import NotesList from './pages/NotesList';
import NoteDetail from './pages/NoteDetail';
import NoteForm from './pages/NoteForm';
import AITools from './pages/AITools';
import NotFound from './pages/NotFound';

function App() {
  return (
    <Router>
      <div className="App d-flex flex-column min-vh-100">
        <Header />
        <main className="container flex-grow-1 py-4">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/notes" element={<NotesList />} />
            <Route path="/notes/:id" element={<NoteDetail />} />
            <Route path="/notes/new" element={<NoteForm />} />
            <Route path="/notes/edit/:id" element={<NoteForm />} />
            <Route path="/ai-tools" element={<AITools />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;