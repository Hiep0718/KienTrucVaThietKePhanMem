// ============================================
// SERVICE LAYER (Business Logic Layer)
// Contains 100% of the core business logic.
// Calls the Repository layer for data access.
// ============================================

const postRepository = require('../repositories/postRepository');

const getAllPosts = () => {
  return postRepository.findAll();
};

const createPost = (title, content) => {
  // Business validation
  if (!title || !title.trim()) {
    throw { status: 400, message: 'Title is required' };
  }
  if (!content || !content.trim()) {
    throw { status: 400, message: 'Content is required' };
  }

  const postData = {
    title: title.trim(),
    content: content.trim(),
  };

  return postRepository.create(postData);
};

const deletePost = (id) => {
  const numericId = parseInt(id, 10);
  if (isNaN(numericId)) {
    throw { status: 400, message: 'Invalid post ID' };
  }

  const deleted = postRepository.deleteById(numericId);
  if (!deleted) {
    throw { status: 404, message: 'Post not found' };
  }

  return deleted;
};

module.exports = {
  getAllPosts,
  createPost,
  deletePost,
};
