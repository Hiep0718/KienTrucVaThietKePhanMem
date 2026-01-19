package iuh.fit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    private final AppStatsService appStatsService;

    // Spring tự động tiêm (inject) instance duy nhất của AppStatsService vào đây
    @Autowired
    public HomeController(AppStatsService appStatsService) {
        this.appStatsService = appStatsService;
    }

    @GetMapping
    public String index() {
        // 1. Tăng bộ đếm
        appStatsService.increment();

        // 2. Lấy giá trị hiện tại
        int count = appStatsService.getVisitCount();

        // 3. Trả về kết quả
        return "<h1>This is the Singleton Pattern Demo</h1>" +
                "<h3>Total System Visits: " + count + "</h3>" +
                "<p>Refresh the page to see the number increase!</p>";
    }
}