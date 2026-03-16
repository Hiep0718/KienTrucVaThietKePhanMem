// ============================================
// EXPRESS SERVER - Entry Point
// Configures middleware and mounts routes.
// ============================================

const express = require('express');
const cors = require('cors');
const postRoutes = require('./src/routes/postRoutes');

const app = express();
const PORT = process.env.PORT || 3001;

// Middleware
app.use(cors());
app.use(express.json());

// Routes
app.use('/api/posts', postRoutes);

// Health check
app.get('/api/health', (req, res) => {
  res.json({ status: 'OK', timestamp: new Date().toISOString() });
});

// Start server
app.listen(PORT, () => {
  console.log(`✅ Backend server running at http://localhost:${PORT}`);
});
