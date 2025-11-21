package com.global.demo.service;

import com.global.demo.dto.kafka.OrderMessage;
import com.global.demo.dto.kafka.ProductMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {


    @KafkaListener(
        topics = "order-events",
        groupId = "trade-bridge-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeOrderMessage(
            @Payload OrderMessage message,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {
        
        try {
            log.info("Received Order Message - OrderId: {}, EventType: {}, Key: {}, Partition: {}, Offset: {}", 
                message.getOrderId(), 
                message.getEventType(),
                key, 
                partition, 
                offset);
            
            // Process the order message
            processOrderMessage(message);
            
            // Acknowledge the message
            acknowledgment.acknowledge();
            
        } catch (Exception e) {
            log.error("Error processing order message: orderId={}", message.getOrderId(), e);
            // In a production environment, you might want to send to a dead letter queue
            // For now, we'll acknowledge to prevent infinite retries
            acknowledgment.acknowledge();
        }
    }

    /**
     * Listen to product events topic
     */
    @KafkaListener(
        topics = "product-events",
        groupId = "trade-bridge-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeProductMessage(
            @Payload ProductMessage message,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {
        
        try {
            log.info("Received Product Message - ProductId: {}, EventType: {}, Key: {}, Partition: {}, Offset: {}", 
                message.getProductId(), 
                message.getEventType(),
                key, 
                partition, 
                offset);
            
            // Process the product message
            processProductMessage(message);
            
            // Acknowledge the message
            acknowledgment.acknowledge();
            
        } catch (Exception e) {
            log.error("Error processing product message: productId={}", message.getProductId(), e);
            // In a production environment, you might want to send to a dead letter queue
            // For now, we'll acknowledge to prevent infinite retries
            acknowledgment.acknowledge();
        }
    }

    /**
     * Process order message - implement your business logic here
     */
    private void processOrderMessage(OrderMessage message) {
        log.info("Processing order message: OrderId={}, Status={}, EventType={}", 
            message.getOrderId(), 
            message.getStatus(), 
            message.getEventType());
        

        
        switch (message.getEventType()) {
            case "CREATED":
                log.info("New order created: {}", message.getOrderId());
                break;
            case "UPDATED":
                log.info("Order updated: {}", message.getOrderId());
                break;
            case "SHIPPED":
                log.info("Order shipped: {}", message.getOrderId());
                break;
            case "DELIVERED":
                log.info("Order delivered: {}", message.getOrderId());
                break;
            case "CANCELLED":
                log.info("Order cancelled: {}", message.getOrderId());
                break;
            default:
                log.warn("Unknown event type: {}", message.getEventType());
        }
    }

    /**
     * Process product message - implement your business logic here
     */
    private void processProductMessage(ProductMessage message) {
        log.info("Processing product message: ProductId={}, EventType={}, StockQuantity={}", 
            message.getProductId(), 
            message.getEventType(),
            message.getStockQuantity());
        
        // Add your business logic here, for example:
        // - Update search index
        // - Send notifications for low stock
        // - Update cache
        // - Trigger analytics
        
        switch (message.getEventType()) {
            case "CREATED":
                log.info("New product created: {}", message.getProductId());
                break;
            case "UPDATED":
                log.info("Product updated: {}", message.getProductId());
                break;
            case "STOCK_UPDATED":
                log.info("Product stock updated: {}, New stock: {}", 
                    message.getProductId(), 
                    message.getStockQuantity());
                // Example: Check if stock is low and send alert
                if (message.getStockQuantity() < 10) {
                    log.warn("Low stock alert for product: {}", message.getProductId());
                }
                break;
            case "DELETED":
                log.info("Product deleted: {}", message.getProductId());
                break;
            default:
                log.warn("Unknown event type: {}", message.getEventType());
        }
    }
}

