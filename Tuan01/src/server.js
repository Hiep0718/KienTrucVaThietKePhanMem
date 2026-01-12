const express = require('express');
const jwt = require('jsonwebtoken');

const app = express();
app.use(express.json()); // Để đọc được dữ liệu JSON gửi lên

const SECRET_KEY = 'hiep_secret_key_123'; // Khóa bí mật

// 1. Giả lập Database người dùng
const users = [];

// 2. API Đăng ký (Register)
app.post('/register', (req, res) => {
    const { username, password } = req.body;
    users.push({ username, password });
    res.status(201).send({ message: "Đăng ký thành công!" });
});

// 3. API Đăng nhập (Login) - Nơi cấp "Thẻ căn cước" JWT
app.post('/login', (req, res) => {
    const { username, password } = req.body;
    const user = users.find(u => u.username === username && u.password === password);

    if (user) {
        // Nếu đúng user, ký một cái Token
        // Token này chứa thông tin username và hết hạn trong 30 giây
        const token = jwt.sign({ username: user.username }, SECRET_KEY, { expiresIn: '30s' });
        return res.json({ token });
    }

    res.status(401).send({ message: "Sai tài khoản hoặc mật khẩu!" });
});

// 4. API Bảo mật (Protected) - Chỉ ai có Token mới vào được
app.get('/profile', (req, res) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1]; // Lấy phần chuỗi sau chữ "Bearer"

    if (!token) return res.status(401).send({ message: "Bạn chưa đăng nhập!" });

    // Kiểm tra Token có đúng không
    jwt.verify(token, SECRET_KEY, (err, user) => {
        if (err) return res.status(403).send({ message: "Token đã hết hạn hoặc không hợp lệ!" });
        
        res.json({ message: `Chào mừng ${user.username} đến với trang cá nhân!`, data: user });
    });
});

app.listen(3000, () => console.log('Server chạy tại http://localhost:3000'));