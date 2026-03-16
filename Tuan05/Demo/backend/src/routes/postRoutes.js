// ============================================
// ROUTES LAYER (API Endpoint Definitions)
// Defines API endpoints only.
// Extracts the HTTP request and forwards it to the controllers.
// ============================================

const express = require('express');
const router = express.Router();
const postController = require('../controllers/postController');

// GET /api/posts - Get all posts
router.get('/', postController.getAllPosts);

// POST /api/posts - Create a new post
router.post('/', postController.createPost);

// DELETE /api/posts/:id - Delete a specific post
router.delete('/:id', postController.deletePost);

module.exports = router;
