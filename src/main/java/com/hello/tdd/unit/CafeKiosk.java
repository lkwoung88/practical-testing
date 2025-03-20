package com.hello.tdd.unit;

import com.hello.tdd.unit.beverage.Beverage;
import com.hello.tdd.unit.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 00);
    private final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 00);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int quantity) {

        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        for (int i = 0; i < quantity; i++) {
            beverages.add(beverage);
        }
    }

    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();
    }

    public int getTotalPrice() {
        int totalPrice = 0;

        for (Beverage beverage : this.beverages) {
            totalPrice += beverage.getPrice();
        }

        return totalPrice;
    }

    public Order createOrder(LocalDateTime currentDateTime) {
        LocalTime currentTime = currentDateTime.toLocalTime();

        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalArgumentException("The current time is before/after the shop open time");
        }

        return new Order(LocalDateTime.now(), this.beverages);
    }
}
