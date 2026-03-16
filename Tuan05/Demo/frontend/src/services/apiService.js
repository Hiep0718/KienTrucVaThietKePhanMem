// ============================================
// API SERVICE LAYER
// Handles ALL external HTTP requests to the backend.
// Components must import and call these methods,
// never writing fetch() directly inside components.
// ============================================

const API_BASE = '/api/posts';

export const getAllPosts = async () => {
  const response = await fetch(API_BASE);
  const data = await response.json();
  if (!data.success) throw new Error(data.message);
  return data.data;
};

export const createPost = async (title, content) => {
  const response = await fetch(API_BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title, content }),
  });
  const data = await response.json();
  if (!data.success) throw new Error(data.message);
  return data.data;
};

export const deletePost = async (id) => {
  const response = await fetch(`${API_BASE}/${id}`, {
    method: 'DELETE',
  });
  const data = await response.json();
  if (!data.success) throw new Error(data.message);
  return data.data;
};
