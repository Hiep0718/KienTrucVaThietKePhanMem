-- =========================================================================
-- BÀI LAB: KỸ THUẬT DATABASE PARTITIONING TRÊN MARIADB
-- =========================================================================

-- -------------------------------------------------------------------------
-- PHẦN 1: FUNCTIONAL PARTITIONING (PHÂN MẢNH THEO CHỨC NĂNG)
-- Mục tiêu: Tách biệt dữ liệu theo nghiệp vụ thay vì nhét chung vào một Database.
-- -------------------------------------------------------------------------

CREATE DATABASE IF NOT EXISTS ecom_sales;
CREATE DATABASE IF NOT EXISTS ecom_hr;

-- -------------------------------------------------------------------------
-- PHẦN 2: VERTICAL PARTITIONING (PHÂN MẢNH DỌC)
-- Mục tiêu: Tách một bảng có quá nhiều cột thành 2 bảng để truy vấn nhanh hơn.
-- -------------------------------------------------------------------------
USE ecom_sales;

-- Tạo bảng chứa các cột cốt lõi (hay truy xuất)
CREATE TABLE IF NOT EXISTS products_core (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Tạo bảng chứa các cột nặng (ít truy xuất)
CREATE TABLE IF NOT EXISTS products_detail (
    product_id INT PRIMARY KEY,
    long_description TEXT,
    image_blob LONGBLOB,
    FOREIGN KEY (product_id) REFERENCES products_core(id)
);

-- Chèn dữ liệu mẫu
INSERT INTO products_core (name, price) VALUES
('Laptop Dell XPS 15', 1500.00),
('iPhone 15 Pro Max', 1200.00);

-- Gán chi tiết cho các sản phẩm ở trên
INSERT INTO products_detail (product_id, long_description, image_blob) VALUES
(1, 'Laptop cao cấp dành cho doanh nhân và lập trình viên...', NULL),
(2, 'Điện thoại flagship mới nhất của Apple với thiết kế titan...', NULL);

-- -------------------------------------------------------------------------
-- PHẦN 3: HORIZONTAL PARTITIONING (PHÂN MẢNH NGANG - NATIVE MARIADB)
-- Mục tiêu: Chia cắt các dòng của một bảng tỷ lệ thuận với thời gian.
-- -------------------------------------------------------------------------
USE ecom_sales;

-- Lưu ý: Cột dùng để partition (order_date) PHẢI là một phần của PRIMARY KEY.
CREATE TABLE IF NOT EXISTS orders (
    order_id INT NOT NULL,
    order_date DATE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (order_id, order_date)
)
PARTITION BY RANGE (YEAR(order_date)) (
    PARTITION p2022 VALUES LESS THAN (2023),
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- Chèn 4 dòng dữ liệu mẫu rải rác vào các năm khác nhau
INSERT INTO orders (order_id, order_date, total_amount) VALUES
(1, '2022-05-15', 250.00),   -- Dòng này sẽ lọt vào p2022
(2, '2023-11-20', 1200.00),  -- Dòng này sẽ lọt vào p2023
(3, '2024-02-10', 85.50),    -- Dòng này sẽ lọt vào p2024
(4, '2025-08-30', 999.99);   -- Dòng này sẽ lọt vào p_future

-- -------------------------------------------------------------------------
-- PHẦN 4: KIỂM CHỨNG LƯU TRỮ TRÊN PARTITION
-- -------------------------------------------------------------------------

-- Truy vấn xem mỗi partition đang chứa bao nhiêu dòng dữ liệu
SELECT 
    PARTITION_NAME,
    TABLE_ROWS
FROM 
    information_schema.PARTITIONS
WHERE 
    TABLE_SCHEMA = 'ecom_sales' 
    AND TABLE_NAME = 'orders';
