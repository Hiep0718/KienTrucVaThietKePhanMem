// ============================================
// PostForm Component (Presentation Layer)
// Pure UI component for creating new posts.
// Uses apiService for HTTP requests.
// ============================================

import { useState } from 'react';

function PostForm({ onPostCreated }) {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await onPostCreated(title, content);
      setTitle('');
      setContent('');
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="post-form-card">
      <div className="form-header">
        <div className="form-icon">✍️</div>
        <h2>Create New Post</h2>
      </div>
      {error && <div className="error-banner">{error}</div>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="post-title">Title</label>
          <input
            id="post-title"
            type="text"
            placeholder="Enter an eye-catching title..."
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="post-content">Content</label>
          <textarea
            id="post-content"
            placeholder="Write your post content here..."
            rows={4}
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="btn-submit" disabled={loading}>
          {loading ? (
            <span className="btn-loading">
              <span className="spinner"></span> Publishing...
            </span>
          ) : (
            '🚀 Publish Post'
          )}
        </button>
      </form>
    </div>
  );
}

export default PostForm;
