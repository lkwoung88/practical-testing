package com.hello.tdd.spring.api.service.order;

import com.hello.tdd.spring.api.service.mail.MailService;
import com.hello.tdd.spring.domain.order.Order;
import com.hello.tdd.spring.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.hello.tdd.spring.domain.order.OrderStatus.PAYMENT_COMPLETION;

/**
 * 메일 전송 같이 긴 작업은 트랜잭션에 참여하지 않아도 되므로 걸지 않는 것이 좋다.
 * 조회 같은 경우 repository에서 트랜잭션이 걸릴것이기 때문에 여기서 걸 필요가 없다.
 */
@Service
@RequiredArgsConstructor
public class OrderStatisticsService {

    private final OrderRepository orderRepository;
    private final MailService mailService;

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
        List<Order> orders = orderRepository.findOrdersBy(
                orderDate.atStartOfDay(),
                orderDate.plusDays(1).atStartOfDay(),
                PAYMENT_COMPLETION
        );

        int totalAmount = orders.stream()
                .mapToInt(Order::getTotalPrice)
                .sum();

        boolean result = mailService.sendMail(
                "no-reply@cafekiosk.com",
                email,
                String.format("[매출통계] %s", orderDate),
                String.format("총 매출 합계는 %s원 입니다.", totalAmount)
        );

        if (!result) {
            throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
        }

        return true;
    }

}
