// ============================================
// App Component
// Main application component that orchestrates
// state management and delegates to service layer.
// ============================================

import { useState, useEffect } from 'react';
import PostForm from './components/PostForm';
import PostList from './components/PostList';
import * as apiService from './services/apiService';

function App() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchPosts = async () => {
    try {
      const data = await apiService.getAllPosts();
      setPosts(data);
    } catch (err) {
      console.error('Failed to fetch posts:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  const handleCreatePost = async (title, content) => {
    const newPost = await apiService.createPost(title, content);
    setPosts((prev) => [newPost, ...prev]);
  };

  const handleDeletePost = async (id) => {
    await apiService.deletePost(id);
    setPosts((prev) => prev.filter((post) => post.id !== id));
  };

  return (
    <div className="app">
      <header className="app-header">
        <div className="header-content">
          <div className="logo">
            <span className="logo-icon">📰</span>
            <h1>Mini CMS</h1>
          </div>
          <p className="tagline">A lightweight Content Management System</p>
        </div>
        <div className="header-glow"></div>
      </header>

      <main className="main-content">
        <div className="container">
          <PostForm onPostCreated={handleCreatePost} />
          <PostList
            posts={posts}
            onDelete={handleDeletePost}
            loading={loading}
          />
        </div>
      </main>

      <footer className="app-footer">
        <p>Mini CMS &mdash; Layered Architecture Demo &bull; React + Express</p>
      </footer>
    </div>
  );
}

export default App;
