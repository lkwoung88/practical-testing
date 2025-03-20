package com.hello.tdd.unit;

import com.hello.tdd.unit.beverage.Americano;
import com.hello.tdd.unit.beverage.Latte;

public class CafeKioskRunner {

    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        cafeKiosk.add(new Latte());

        int totalPrice = cafeKiosk.getTotalPrice();
        System.out.println("totalPrice = " + totalPrice);
    }


}
