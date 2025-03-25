package com.hello.tdd.spring.api.service.order;

import com.hello.tdd.spring.IntegrationTestSupport;
import com.hello.tdd.spring.client.mail.MailSendClient;
import com.hello.tdd.spring.domain.history.mail.MailSendHistory;
import com.hello.tdd.spring.domain.history.mail.MailSendHistoryRepository;
import com.hello.tdd.spring.domain.order.Order;
import com.hello.tdd.spring.domain.order.OrderRepository;
import com.hello.tdd.spring.domain.order.OrderStatus;
import com.hello.tdd.spring.domain.orderproduct.OrderProductRepository;
import com.hello.tdd.spring.domain.product.Product;
import com.hello.tdd.spring.domain.product.ProductRepository;
import com.hello.tdd.spring.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.hello.tdd.spring.domain.product.ProductSellingStatus.SELLING;
import static com.hello.tdd.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class OrderStatisticsServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticMail() {

        LocalDateTime now = LocalDateTime.of(2025, 3, 23, 0, 0);

        // given
        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = cratePaymentCompletedOrder(products, LocalDateTime.of(2025, 3, 22, 23, 59,59));
        Order order2 = cratePaymentCompletedOrder(products, now);
        Order order3 = cratePaymentCompletedOrder(products, LocalDateTime.of(2025, 3, 23, 23, 59, 59));
        Order order4 = cratePaymentCompletedOrder(products, LocalDateTime.of(2025, 3, 24, 0, 0, 0));

        // stubbing
        Mockito.when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2025, 3, 23), "test@test.com");

        // then
        assertThat(result).isTrue();
        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 18000원 입니다.");

    }

    private Order cratePaymentCompletedOrder(List<Product> products, LocalDateTime now) {
        Order order = Order.builder()
                .products(products)
                .status(OrderStatus.PAYMENT_COMPLETION)
                .registeredDateTime(now)
                .build();
        return orderRepository.save(order);
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(SELLING)
                .name("메뉴이름")
                .price(price)
                .build();
    }
}