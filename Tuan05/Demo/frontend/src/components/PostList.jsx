// ============================================
// PostList Component (Presentation Layer)
// Pure UI component for displaying and deleting posts.
// ============================================

function PostList({ posts, onDelete, loading }) {
  if (loading) {
    return (
      <div className="posts-section">
        <div className="section-header">
          <h2>📋 All Posts</h2>
        </div>
        <div className="loading-state">
          <div className="spinner large"></div>
          <p>Loading posts...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="posts-section">
      <div className="section-header">
        <h2>📋 All Posts</h2>
        <span className="post-count">{posts.length} post{posts.length !== 1 ? 's' : ''}</span>
      </div>

      {posts.length === 0 ? (
        <div className="empty-state">
          <div className="empty-icon">📝</div>
          <h3>No posts yet</h3>
          <p>Create your first post to get started!</p>
        </div>
      ) : (
        <div className="posts-grid">
          {posts.map((post) => (
            <article key={post.id} className="post-card">
              <div className="post-card-header">
                <h3 className="post-title">{post.title}</h3>
                <button
                  className="btn-delete"
                  onClick={() => onDelete(post.id)}
                  title="Delete post"
                  aria-label={`Delete post: ${post.title}`}
                >
                  🗑️
                </button>
              </div>
              <p className="post-content">{post.content}</p>
              <div className="post-meta">
                <span className="post-date">
                  🕐 {new Date(post.createdAt).toLocaleString()}
                </span>
                <span className="post-id">#{post.id}</span>
              </div>
            </article>
          ))}
        </div>
      )}
    </div>
  );
}

export default PostList;
