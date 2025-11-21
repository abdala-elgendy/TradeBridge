package com.global.demo.service;

import com.global.demo.dto.kafka.OrderMessage;
import com.global.demo.dto.kafka.ProductMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Topic names - can be moved to application.properties
    public static final String ORDER_TOPIC = "order-events";
    public static final String PRODUCT_TOPIC = "product-events";

    /**
     * Send order message to Kafka
     */
    public void sendOrderMessage(OrderMessage orderMessage) {
        String key = String.valueOf(orderMessage.getOrderId());
        
        CompletableFuture<SendResult<String, Object>> future = 
            kafkaTemplate.send(ORDER_TOPIC, key, orderMessage);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Order message sent successfully: orderId={}, offset={}", 
                    orderMessage.getOrderId(), 
                    result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send order message: orderId={}", 
                    orderMessage.getOrderId(), ex);
            }
        });
    }

    /**
     * Send order message with custom topic
     */
    public void sendOrderMessage(String topic, OrderMessage orderMessage) {
        String key = String.valueOf(orderMessage.getOrderId());
        
        CompletableFuture<SendResult<String, Object>> future = 
            kafkaTemplate.send(topic, key, orderMessage);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Order message sent to topic {}: orderId={}, offset={}", 
                    topic, 
                    orderMessage.getOrderId(), 
                    result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send order message to topic {}: orderId={}", 
                    topic, orderMessage.getOrderId(), ex);
            }
        });
    }

    /**
     * Send product message to Kafka
     */
    public void sendProductMessage(ProductMessage productMessage) {
        String key = String.valueOf(productMessage.getProductId());
        
        CompletableFuture<SendResult<String, Object>> future = 
            kafkaTemplate.send(PRODUCT_TOPIC, key, productMessage);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Product message sent successfully: productId={}, offset={}", 
                    productMessage.getProductId(), 
                    result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send product message: productId={}", 
                    productMessage.getProductId(), ex);
            }
        });
    }

    /**
     * Send product message with custom topic
     */
    public void sendProductMessage(String topic, ProductMessage productMessage) {
        String key = String.valueOf(productMessage.getProductId());
        
        CompletableFuture<SendResult<String, Object>> future = 
            kafkaTemplate.send(topic, key, productMessage);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Product message sent to topic {}: productId={}, offset={}", 
                    topic, 
                    productMessage.getProductId(), 
                    result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send product message to topic {}: productId={}", 
                    topic, productMessage.getProductId(), ex);
            }
        });
    }

    /**
     * Send generic message to any topic
     */
    public void sendMessage(String topic, String key, Object message) {
        CompletableFuture<SendResult<String, Object>> future = 
            kafkaTemplate.send(topic, key, message);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message sent to topic {}: key={}, offset={}", 
                    topic, key, result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send message to topic {}: key={}", topic, key, ex);
            }
        });
    }
}

