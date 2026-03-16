// ============================================
// CONTROLLER LAYER (HTTP Request/Response Handler)
// Handles HTTP requests and responses.
// Extracts inputs (body, params) and calls the service layer.
// ============================================

const postService = require('../services/postService');

const getAllPosts = (req, res) => {
  try {
    const posts = postService.getAllPosts();
    res.json({ success: true, data: posts });
  } catch (error) {
    res.status(error.status || 500).json({
      success: false,
      message: error.message || 'Internal server error',
    });
  }
};

const createPost = (req, res) => {
  try {
    const { title, content } = req.body;
    const newPost = postService.createPost(title, content);
    res.status(201).json({ success: true, data: newPost });
  } catch (error) {
    res.status(error.status || 500).json({
      success: false,
      message: error.message || 'Internal server error',
    });
  }
};

const deletePost = (req, res) => {
  try {
    const { id } = req.params;
    const deleted = postService.deletePost(id);
    res.json({ success: true, data: deleted });
  } catch (error) {
    res.status(error.status || 500).json({
      success: false,
      message: error.message || 'Internal server error',
    });
  }
};

module.exports = {
  getAllPosts,
  createPost,
  deletePost,
};
