# Thực hành 3 Kỹ thuật Database Partitioning trên MariaDB

Bài Lab này cung cấp kịch bản tách dữ liệu E-commerce trên MariaDB. Các bạn có thể copy nội dung trong file `lab_partitioning.sql` chèn vào HeidiSQL và chạy lần lượt.

## Cấu trúc bài Lab

1. **Phần 1: Functional Partitioning** - Tách 2 databases `ecom_sales` và `ecom_hr` nhằm phân biệt hoàn toàn dữ liệu của 2 phần mềm khác nhau trên cùng Server.
2. **Phần 2: Vertical Partitioning** - Tách các cột tốn dung lượng như `TEXT`, `BLOB` khỏi bảng chính `products_core` sang bảng `products_detail`. Điều này giúp những câu query danh sách sản phẩm (chỉ cần id, name, price) quét qua đĩa cứng nhanh hơn rất nhiều do kích cỡ mỗi dòng dữ liệu đã được thu nhỏ lại.
3. **Phần 3: Horizontal Partitioning** - Dùng kĩ thuật Native của MariaDB `PARTITION BY RANGE` để chặt dọc các dòng (rows) hóa đơn vứt vào các thùng (partitions) tùy theo năm của hóa đơn đó.

## Phần 4: Kiểm chứng bằng HeidiSQL (Giao diện trực quan)

Sau khi chạy toàn bộ file SQL, bạn có thể kiểm tra xem phân mảnh Horizontal (Ngang) đã hoạt động tốt như thế nào thông qua 2 phương pháp:

### Cách 1: Chạy SQL

Câu SQL ở cuối file:
```sql
SELECT PARTITION_NAME, TABLE_ROWS 
FROM information_schema.PARTITIONS 
WHERE TABLE_SCHEMA = 'ecom_sales' AND TABLE_NAME = 'orders';
```
Kết quả hiển thị `TABLE_ROWS` sẽ là `1` cho mỗi Partition `p2022`, `p2023`, `p2024` và `p_future`. Điều đó chứng tỏ MariaDB đã tự hiểu và nhét đúng dòng dữ liệu vào đúng cấu trúc vật lý năm đó!

### Cách 2: Bấm trên giao diện HeidiSQL

1. Kết nối vào máy chủ MariaDB bằng HeidiSQL.
2. Mở nhánh thư mục database `ecom_sales`, click chọn vào bảng `orders`.
3. Nhìn sang khung bên phải, chọn tab **"Table options"** (hoặc **Options** tùy phiên bản).
4. Kéo xuống dưới, bạn sẽ thấy mục **"Partitioning"**. Tại đây HeidiSQL sẽ vẽ ra cái bảng liệt kê rất trực quan: 
   - Tên Partition (`Danh sách các p2022, p2023...`)
   - Kiểu Partition (`RANGE`)
   - Biểu thức (`YEAR(order_date)`)
   - Giới hạn (`VALUES LESS THAN(2023)`...)
5. Nếu bạn chuyển sang tab **"Data"** của bảng `orders`, một vài phiên bản của HeidiSQL hỗ trợ chọn quét theo Partition để bạn xem chính xác dữ liệu nằm cục bộ ở Partition đó.
