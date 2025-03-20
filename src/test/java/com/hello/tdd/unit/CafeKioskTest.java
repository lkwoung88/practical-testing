package com.hello.tdd.unit;

import com.hello.tdd.unit.beverage.Americano;
import com.hello.tdd.unit.order.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class CafeKioskTest {

    @DisplayName("음료 두잔이 장바구니에 담긴다.")
    @Test
    void addSeveralBeverages() {

        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // when
        cafeKiosk.add(americano, 2);

        // then
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);

    }

    @Test
    void addZeroBeverages() {

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(()->cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Quantity must be greater than 0");

    }

    @Test
    void createOrder() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2024, 8, 30, 10, 00));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("Americano");
    }

    @Test
    @DisplayName("영업 시작 시간 내에서만 주문을 생성할 수 있다.")
    void createOrderOutsideOpenTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(()->cafeKiosk.createOrder(LocalDateTime.of(2024, 8, 30, 9, 59,59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The current time is before/after the shop open time");
    }

}