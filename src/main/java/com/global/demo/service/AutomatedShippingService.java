package com.global.demo.service;

import com.global.demo.entity.Order;
import com.global.demo.entity.OrderStatus;
import com.global.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutomatedShippingService {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final EmailService emailService;

    /**
     * Scheduled task that runs every day at 6:00 PM
     * Finds all CONFIRMED orders from the previous day and marks them as SHIPPED
     */
    @Scheduled(cron = "0 0 18 * * ?") // Runs at 6:00 PM every day
    @Transactional
    public void processDailyShipments() {
        LocalDateTime yesterdayStart = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
        LocalDateTime yesterdayEnd = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);

        // Find all CONFIRMED orders from yesterday
        List<Order> ordersToShip = orderRepository.findByStatusAndOrderDateBetween(
            OrderStatus.CONFIRMED,
            yesterdayStart,
            yesterdayEnd
        );

        // Update each order to SHIPPED status
        for (Order order : ordersToShip) {
            orderService.updateOrderStatus(order, OrderStatus.SHIPPED);
        }
    }
} 