package com.global.demo.entity;

public enum OrderStatus {
    PENDING,      // Order created but not yet processed
    PROCESSING,   // Order is being processed
    CONFIRMED,    // Order confirmed and ready for shipping
    SHIPPED,      // Order has been shipped
    DELIVERED,    // Order has been delivered
    CANCELLED,    // Order was cancelled
    REFUNDED,      // Order was refunded
    ERRORED      // Order was selected wrongly
}