const express = require('express');
const path = require('path');
const pluginManager = require('./pluginManager');

const app = express();
const PORT = 3000;

// ========================
// In-Memory Database
// ========================
const posts = [];
let nextId = 1;

// ========================
// Middleware
// ========================
app.use(express.json());
app.use(express.static(path.join(__dirname, '..', 'public')));

// ========================
// Core CMS Routes
// ========================

// GET /api/posts - Read all posts
app.get('/api/posts', (req, res) => {
    res.json(posts);
});

// POST /api/posts - Create a new post
app.post('/api/posts', (req, res) => {
    const { title, content } = req.body;
    if (!title || !content) {
        return res.status(400).json({ error: 'Title and content are required.' });
    }
    const post = { id: nextId++, title, content, createdAt: new Date().toISOString() };
    posts.push(post);
    res.status(201).json(post);
});

// DELETE /api/posts/:id - Delete a post by ID
app.delete('/api/posts/:id', (req, res) => {
    const id = parseInt(req.params.id, 10);
    const index = posts.findIndex(p => p.id === id);
    if (index === -1) {
        return res.status(404).json({ error: 'Post not found.' });
    }
    const deleted = posts.splice(index, 1);
    res.json(deleted[0]);
});

// ========================
// Plugin Management Routes
// ========================

// Middleware to block requests to disabled plugin routes
// We map the API endpoints to their plugin folder names
app.use((req, res, next) => {
    const rMap = {
        '/api/stats': 'post-stats',
        '/api/wordcount': 'word-count',
        '/api/lastmodified': 'last-modified'
    };
    
    const pluginName = rMap[req.path];
    if (pluginName && !pluginManager.isPluginActive(pluginName)) {
        return res.status(403).json({ error: `Plugin ${pluginName} disabled.` });
    }
    next();
});

// GET /api/plugins - Read all plugins
app.get('/api/plugins', (req, res) => {
    res.json(pluginManager.getPlugins());
});

// POST /api/plugins/:name/toggle - Toggle a plugin
app.post('/api/plugins/:name/toggle', (req, res) => {
    const pluginName = req.params.name;
    const newState = pluginManager.togglePlugin(pluginName);
    if (newState === null) {
        return res.status(404).json({ error: 'Plugin not found.' });
    }
    res.json({ name: pluginName, active: newState });
});

// ========================
// Load Plugins
// ========================
// Note: We load plugins AFTER defining the middleware so it can intercept the route.
// Actually Express applies middleware in order. 
// If the plugin calls app.get('/api/stats', ...), our middleware above will run first!
pluginManager.loadPlugins(app, posts);

// ========================
// Start Server
// ========================
app.listen(PORT, () => {
    console.log(`✅ Microkernel CMS running at http://localhost:${PORT}`);
});
