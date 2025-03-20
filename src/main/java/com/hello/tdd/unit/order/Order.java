package com.hello.tdd.unit.order;

import com.hello.tdd.unit.beverage.Beverage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Order {
    private final LocalDateTime orderDate;
    private final List<Beverage> beverages;
}
