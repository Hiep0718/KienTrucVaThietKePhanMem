package iuh.fit.pattern.subject;

import iuh.fit.pattern.observer.Observer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class Stock implements Subject {

    private String symbol;
    private float price;
    private List<Observer> investors;

    public Stock(String symbol, float initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
        this.investors = new ArrayList<>();
        System.out.println("✅ Khởi tạo cổ phiếu " + symbol
                + " với giá: " + String.format("%,.0f", initialPrice) + " VNĐ");
    }

    @Override
    public void registerObserver(Observer observer) {
        investors.add(observer);
        System.out.println("➕ Đã đăng ký observer vào " + symbol);
    }

    @Override
    public void removeObserver(Observer observer) {
        investors.remove(observer);
        System.out.println("➖ Đã hủy đăng ký observer khỏi " + symbol);
    }

    @Override
    public void notifyObservers() {
        System.out.println("\n🔔 Thông báo thay đổi giá cổ phiếu " + symbol + "...");
        for (Observer investor : investors) {
            investor.update(symbol, price);
        }
        System.out.println();
    }

    // Phương thức quan trọng: Thay đổi giá và tự động thông báo
    public void setPrice(float newPrice) {
        System.out.println("\n💰 Giá cổ phiếu " + symbol
                + " thay đổi từ " + String.format("%,.0f", price)
                + " VNĐ → " + String.format("%,.0f", newPrice) + " VNĐ");
        this.price = newPrice;
        notifyObservers();
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public float getPrice() {
        return price;
    }
}