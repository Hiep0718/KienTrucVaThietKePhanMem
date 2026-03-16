# Mini CMS — Walkthrough

## Overview
Built a full-stack CMS with **strict Layered Architecture** using React (Vite) + Node.js (Express) with an in-memory database.

## Architecture

```mermaid
graph TD
    subgraph "Frontend (React/Vite - :5173)"
        A["Components<br/>PostForm, PostList"] -->|import| B["services/apiService.js<br/>(HTTP layer)"]
    end
    B -->|fetch /api/posts| C

    subgraph "Backend (Express - :3001)"
        C["routes/postRoutes.js"] -->|forward| D["controllers/postController.js"]
        D -->|call| E["services/postService.js<br/>(Business Logic)"]
        E -->|call| F["repositories/postRepository.js<br/>(Data Access - In-Memory Array)"]
    end
```

## Project Structure
```
Demo/
├── backend/
│   ├── server.js                          # Express entry point
│   ├── package.json
│   └── src/
│       ├── routes/postRoutes.js           # API endpoint definitions
│       ├── controllers/postController.js  # HTTP request/response handling
│       ├── services/postService.js        # Business logic & validation
│       └── repositories/postRepository.js # Data access (in-memory array)
└── frontend/
    ├── index.html
    ├── vite.config.js                     # Vite config with API proxy
    ├── package.json
    └── src/
        ├── main.jsx
        ├── App.jsx                        # Main app (state orchestration)
        ├── index.css                      # Premium dark theme CSS
        ├── services/apiService.js         # Centralized HTTP requests
        └── components/
            ├── PostForm.jsx               # Create post UI
            └── PostList.jsx               # Display & delete posts UI
```

## Verification Results

All 3 features tested successfully via browser:

### 1. Initial UI — Empty State
![Initial page](C:/Users/Student/.gemini/antigravity/brain/b74755d7-b496-417e-90a8-c68736628433/initial_page_ui_1773663406088.png)

### 2. Create & Read — Two Posts Created
![Two posts](C:/Users/Student/.gemini/antigravity/brain/b74755d7-b496-417e-90a8-c68736628433/two_posts_created_1773663456853.png)

### 3. Delete — First Post Removed
![After delete](C:/Users/Student/.gemini/antigravity/brain/b74755d7-b496-417e-90a8-c68736628433/delete_post_success_1773663471490.png)

### Full Test Recording
![CMS full test recording](C:/Users/Student/.gemini/antigravity/brain/b74755d7-b496-417e-90a8-c68736628433/cms_full_test_1773663384229.webp)

## Start Commands

| Server   | Command         | URL                     |
|----------|-----------------|-------------------------|
| Backend  | `node server.js` (from `backend/`) | http://localhost:3001 |
| Frontend | `npm run dev` (from `frontend/`)   | http://localhost:5173 |
