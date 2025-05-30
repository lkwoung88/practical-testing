package com.hello.tdd.spring.domain.order;

import com.hello.tdd.spring.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.hello.tdd.spring.domain.order.OrderStatus.INIT;
import static com.hello.tdd.spring.domain.product.ProductSellingStatus.SELLING;
import static com.hello.tdd.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice(){

        // given
        List<Product> products = List.of(createProduct("001", 1000),
                createProduct("002", 2000));

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT 이다.")
    @Test
    void init(){

        // given
        List<Product> products = List.of(createProduct("001", 1000),
                createProduct("002", 2000));

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getStatus()).isEqualByComparingTo(INIT);
    }

    @DisplayName("주문 생성 시 등록 시간을 기록한다.")
    @Test
    void registeredDateTime(){

        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        List<Product> products = List.of(createProduct("001", 1000),
                createProduct("002", 2000));

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .type(HANDMADE)
                .productNumber(productNumber)
                .sellingStatus(SELLING)
                .name("메뉴이름")
                .price(price)
                .build();
    }
}