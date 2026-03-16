/**
 * PostStatsPlugin
 * 
 * Extends the Core CMS by adding a GET /api/stats route
 * that returns the total number of posts and the average title length.
 * 
 * This plugin does NOT modify any Core System code.
 */

function init(app, db) {
    app.get('/api/stats', (req, res) => {
        const totalPosts = db.length;
        const averageTitleLength = totalPosts === 0
            ? 0
            : parseFloat((db.reduce((sum, post) => sum + post.title.length, 0) / totalPosts).toFixed(2));

        res.json({
            totalPosts,
            averageTitleLength
        });
    });
}

module.exports = { init };
