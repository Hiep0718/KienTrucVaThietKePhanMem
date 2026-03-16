/**
 * LastModifiedPlugin
 * 
 * Returns the most recently added post's title and timestamp.
 */

function init(app, db) {
    app.get('/api/lastmodified', (req, res) => {
        if (db.length === 0) {
            return res.json({
                latestTitle: 'N/A',
                recentlyAddedAt: null
            });
        }

        // The posts array appends to the end, so the last element is the newest
        const lastPost = db[db.length - 1];

        res.json({
            latestTitle: lastPost.title,
            recentlyAddedAt: lastPost.createdAt
        });
    });
}

module.exports = { init };
