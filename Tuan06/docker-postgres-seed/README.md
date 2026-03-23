# Thực hành: Tạo Custom Docker Image cho PostgreSQL chứa sẵn dữ liệu (Seed Data)

## 1. Tại sao `docker commit` không hiệu quả với PostgreSQL?

Một cám dỗ thường gặp khi muốn tạo image có sẵn dữ liệu là:
1. Chạy container PostgreSQL.
2. Truy cập vào container và `INSERT` dữ liệu.
3. Dùng lệnh `docker commit` để tạo thành một image mới.

**Tuy nhiên, cách này KHÔNG hoạt động với PostgreSQL image chính thức.** 

**Lý do (Cơ chế Volume của Docker):** 
Trong `Dockerfile` gốc của PostgreSQL, thư mục chứa dữ liệu `/var/lib/postgresql/data` đã được khai báo là một `VOLUME`. Trong Docker, những thay đổi diễn ra bên trong một `VOLUME` sẽ ghi trực tiếp xuống host (bo qua Union File System) và **không được lưu lại** khi bạn thực hiện lệnh `docker commit`. Do đó, image mới được tạo ra sẽ hoàn toàn trống rỗng và không hề chứa dữ liệu bạn vừa thêm vào.

---

## 2. Giải pháp đúng chuẩn (Best Practice): Sử dụng thư mục `/docker-entrypoint-initdb.d/`

Cách tốt nhất là sử dụng cơ chế khởi tạo có sẵn của PostgreSQL image. Image này được thiết kế để tự động quét thư mục `/docker-entrypoint-initdb.d/` trong lần khởi động đầu tiên (khi database trống). Nó sẽ thực thi tất cả các file `.sql`, `.sql.gz`, hoặc `.sh` mà nó tìm thấy trong thư mục này.

---

## 3. Các bước thực hành

### Bước 1: Chuẩn bị script khởi tạo (`init.sql`)

Tạo một file tên là `init.sql` và thêm đoạn mã sau để tạo bảng và chèn dữ liệu mẫu:

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL
);

INSERT INTO users (username, email) VALUES
('alice', 'alice@example.com'),
('bob', 'bob@example.com'),
('charlie', 'charlie@example.com');
```

### Bước 2: Viết `Dockerfile`

Tạo file `Dockerfile` nằm cùng thư mục với `init.sql`:

```dockerfile
FROM postgres:latest

# Copy file script khởi tạo vào thư mục đặc biệt của PostgreSQL image
COPY init.sql /docker-entrypoint-initdb.d/
```

### Bước 3: Build Docker Image

Mở Terminal tại thư mục chứa 2 file trên và chạy lệnh sau để build image:

```bash
docker build -t my-postgres-with-data .
```

### Bước 4: Chạy container từ Image vừa tạo

Khởi chạy container từ image `my-postgres-with-data`. Đừng quên cung cấp biến môi trường `POSTGRES_PASSWORD` vì đây là cấu hình bắt buộc của Postgres.

```bash
docker run -d \
  --name my-postgres-container \
  -e POSTGRES_PASSWORD=mysecretpassword \
  -p 5432:5432 \
  my-postgres-with-data
```

### Bước 5: Kiểm tra dữ liệu (Verify)

Sử dụng lệnh `docker exec` kết hợp với công cụ `psql` phân hệ CLI của Postgres để truy cập thẳng vào container và thực hiện câu lệnh `SELECT`:

```bash
docker exec -it my-postgres-container psql -U postgres -c "SELECT * FROM users;"
```

**Kết quả màn hình sẽ hiển thị:**
```
 id | username |        email        
----+----------+---------------------
  1 | alice    | alice@example.com
  2 | bob      | bob@example.com
  3 | charlie  | charlie@example.com
(3 rows)
```

Điều này chứng minh dữ liệu (seed data) đã được tự động thêm vào thông qua file `init.sql` ngay trong quá trình container khởi tạo lần đầu!
