package iuh.fit.pattern.observer;

import org.springframework.stereotype.Component;

public class Investor implements Observer {

    private String name;

    public Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(String stockSymbol, float price) {
        System.out.println("📢 [" + name + "] nhận thông báo: "
                + "Cổ phiếu " + stockSymbol
                + " hiện tại: " + String.format("%,.0f", price) + " VNĐ");
    }

    public String getName() {
        return name;
    }
}
