const fs = require('fs');
const path = require('path');

const PLUGINS_DIR = path.join(__dirname, '..', 'plugins');

// Registry to track plugins: { name: { instance, active } }
const pluginRegistry = {};

/**
 * Plugin Manager — scans the plugins/ directory and initializes each plugin.
 */
function loadPlugins(app, db) {
    if (!fs.existsSync(PLUGINS_DIR)) {
        console.log('⚠️  No plugins/ directory found. Skipping plugin loading.');
        return;
    }

    const pluginFolders = fs.readdirSync(PLUGINS_DIR, { withFileTypes: true })
        .filter(entry => entry.isDirectory())
        .map(entry => entry.name);

    console.log(`🔌 Found ${pluginFolders.length} plugin(s). Loading...`);

    for (const folder of pluginFolders) {
        try {
            const pluginPath = path.join(PLUGINS_DIR, folder);
            const plugin = require(pluginPath);

            if (typeof plugin.init === 'function') {
                // Initialize but wrap in our registry
                pluginRegistry[folder] = {
                    instance: plugin,
                    active: true, // Default to true
                    initArgs: [app, db] // Store args just in case needed for true reload later
                };
                plugin.init(app, db);
                console.log(`   ✅ Plugin loaded and active: ${folder}`);
            } else {
                console.log(`   ⚠️  Plugin "${folder}" has no init() function. Skipped.`);
            }
        } catch (err) {
            console.error(`   ❌ Failed to load plugin "${folder}":`, err.message);
        }
    }
}

/**
 * Get a list of all known plugins and their status.
 */
function getPlugins() {
    return Object.keys(pluginRegistry).map(name => ({
        name,
        active: pluginRegistry[name].active
    }));
}

/**
 * Toggle a plugin's active state.
 * For a truly robust system we'd need to unmount routes, but for this minimal CMS
 * we will just set the flag, and the plugin itself or the core might check it.
 * Actually, since routes are already mounted, the best way for a minimal CMS
 * is to have the Core intercept plugin routes if the plugin is disabled via middleware.
 */
function togglePlugin(pluginName) {
    if (pluginRegistry[pluginName]) {
        pluginRegistry[pluginName].active = !pluginRegistry[pluginName].active;
        return pluginRegistry[pluginName].active;
    }
    return null;
}

/**
 * Middleware to check if a route belongs to a disabled plugin.
 * A simple convention: Plugin routes start with /api/[pluginName] or we just map 
 * specific routes to plugins. Since post-stats uses /api/stats, we can map it.
 * For true dynamic routing, this is complex, but let's implement a simple check.
 */
function isPluginActive(pluginName) {
    return pluginRegistry[pluginName] ? pluginRegistry[pluginName].active : false;
}

module.exports = { loadPlugins, getPlugins, togglePlugin, isPluginActive };
