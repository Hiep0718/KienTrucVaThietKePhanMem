package iuh.fit.util;


import java.util.Base64;

public class SecurityUtils {
    // Giả lập mã hóa (Thực tế dùng AES, ở đây dùng Base64 cho dễ demo)
    public static String encrypt(String rawData) {
        return Base64.getEncoder().encodeToString(rawData.getBytes());
    }
}
