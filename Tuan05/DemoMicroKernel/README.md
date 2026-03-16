# 🧩 Microkernel CMS (Core & Plugin Architecture)

Một hệ thống quản lý nội dung (CMS) siêu tối giản được xây dựng bằng Node.js (Express), tuân thủ nghiêm ngặt **Kiến trúc Microkernel** (hay còn gọi là Kiến trúc Core & Plugin).

## 🏛️ Chứng minh Kiến trúc Microkernel

Dự án này là minh chứng rõ ràng cho Kiến trúc Microkernel, được chia thành 2 phần tách biệt hoàn toàn:

### 1. Hệ thống lõi (The Core System)
Nằm trong thư mục `core/`. Lõi là một hệ thống cực kỳ nhỏ gọi, chỉ chứa:
- **`server.js`**: Khởi tạo Express, chạy server, quản lý Database (hiện tại là mảng `posts` trong bộ nhớ RAM), và định nghĩa **chỉ 3 API cơ bản nhất** (Lấy danh sách bài viết, Tạo bài viết, Xóa bài viết).
- **`pluginManager.js`**: Đây là thành phần quan trọng nhất của Lõi. Trách nhiệm của nó là tự động quét thư mục `plugins/`, nạp (load) các plugin vào hệ thống, và quản lý vòng đời của chúng (cho phép Bật/Tắt plugin khi đang chạy).

> Lõi không hề biết trước về sự tồn tại của bất kỳ chức năng thống kê (Stats, WordCount...) nào. Nó cung cấp sự mở rộng vô hạn mà không cần phải chạm vào code của Lõi.

### 2. Các Modules Cắm Trong (The Plugin Modules)
Nằm trong thư mục `plugins/`. Các plugin hoạt động hoàn toàn độc lập với nhau. Hiện tại hệ thống có 3 plugins:
1. `post-stats`: Tính tổng số bài viết và chiều dài trung bình của tiêu đề.
2. `word-count`: Đếm tổng số từ của mọi bài viết cộng lại.
3. `last-modified`: Lấy ra tên bài viết và thời gian của bài viết mới được thêm gần đây nhất.

## 🚀 Hướng dẫn Cài đặt & Chạy

1. Cài đặt thư viện:
```bash
npm install
```

2. Chạy Server Lõi:
```bash
npm start
```
Server sẽ chạy tại `http://localhost:3000`. Khi khởi động, Lõi sẽ tự động quét và nạp 3 plugin vào. Bật trang web lên để thấy giao diện Vanilla HTML/JS hiện ra.

## 🛠️ Cách tạo mới một Plugin 

Vì chúng ta sử dụng kiến trúc Microkernel, việc thêm tính năng mới trở nên vô cùng dễ dàng và **không sợ làm hỏng Lõi (Core)**.

Giả sử bạn muốn tạo một Plugin mới trả về "Bài viết có tiêu đề dài nhất" tên là `longest-title`.

### Bước 1: Tạo thư mục cho Plugin
Tạo một thư mục mới trong `plugins/`:
```
plugins/
  ├── longest-title/
  │    └── index.js
```

### Bước 2: Viết mã cho Plugin
Bên trong `index.js`, bạn chỉ cần export một hàm `init(app, db)`. Trong đó `app` là Express và `db` là mảng dữ liệu bài viết (được Lõi truyền vào).

```javascript
/**
 * plugins/longest-title/index.js
 */
function init(app, db) {
    // Tự động cắm thêm một API mới vào Lõi
    app.get('/api/longesttitle', (req, res) => {
        if (db.length === 0) return res.json({ title: 'N/A' });
        
        let longest = db[0];
        for (const post of db) {
            if (post.title.length > longest.title.length) {
                longest = post;
            }
        }
        res.json({ title: longest.title });
    });
}

module.exports = { init };
```

### Bước 3: Đăng ký Router cho Middleware (Tùy chọn)
Nếu bạn muốn hệ thống Bật/Tắt Plugin của Lõi có thể chặn được API này khi Plugin bị tắt, hãy mở `core/server.js` và thêm map cho nó:
```javascript
const rMap = {
    '/api/stats': 'post-stats',
    '/api/wordcount': 'word-count',
    '/api/lastmodified': 'last-modified',
    '/api/longesttitle': 'longest-title' // Thêm dòng này
};
```

### Bước 4: Khởi động lại Server
Chỉ cần chạy lại `npm start`. Lõi (`pluginManager`) sẽ tự động tìm thấy thư mục `longest-title`, chạy hàm `init()`, và API `/api/longesttitle` của bạn đã sẵn sàng hoạt động mà không cần sửa bất kỳ logic CRUD nào của `core/server.js`!

Bạn có thể test API mới ngay lập tức trên trình duyệt hoặc gọi API này từ Frontend (`public/index.html`) để hiển thị nó lên màn hình.
