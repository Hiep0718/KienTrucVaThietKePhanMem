/**
 * WordCountPlugin
 * 
 * Computes the total number of words across all post contents.
 */

function init(app, db) {
    app.get('/api/wordcount', (req, res) => {
        let totalWords = 0;
        
        for (const post of db) {
            if (post.content) {
                // Split by spaces, filter out empty strings
                const words = post.content.trim().split(/\s+/);
                totalWords += words.length > 0 && words[0] !== "" ? words.length : 0;
            }
        }

        res.json({
            totalWords
        });
    });
}

module.exports = { init };
