package iuh.fit;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;

@Service
@Scope("singleton")
public class AppStatsService {

    private int visitCount = 0; // Biến này sẽ được giữ lại trong bộ nhớ

    // Dùng synchronized để đảm bảo an toàn nếu có nhiều người truy cập cùng lúc
    public synchronized void increment() {
        visitCount++;
    }

    public int getVisitCount() {
        return visitCount;
    }
}