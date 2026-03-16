// ============================================
// REPOSITORY LAYER (Data Access Layer)
// The ONLY layer that interacts with the database.
// In this case, we use an in-memory array as the database.
// ============================================

let posts = [];
let nextId = 1;

const findAll = () => {
  return [...posts];
};

const findById = (id) => {
  return posts.find((post) => post.id === id) || null;
};

const create = (postData) => {
  const newPost = {
    id: nextId++,
    title: postData.title,
    content: postData.content,
    createdAt: new Date().toISOString(),
  };
  posts.push(newPost);
  return newPost;
};

const deleteById = (id) => {
  const index = posts.findIndex((post) => post.id === id);
  if (index === -1) return null;
  const deleted = posts.splice(index, 1);
  return deleted[0];
};

module.exports = {
  findAll,
  findById,
  create,
  deleteById,
};
