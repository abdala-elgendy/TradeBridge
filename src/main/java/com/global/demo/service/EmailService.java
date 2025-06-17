package com.global.demo.service;

import com.global.demo.entity.Order;
import com.global.demo.entity.OrderStatus;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${application.frontend-url}")
    private String frontendUrl;

    public void sendVerificationEmail(String toEmail, String token) {
        // Validate email configuration
        if (fromEmail == null || fromEmail.trim().isEmpty() || fromEmail.equals("your-email@gmail.com")) {
            log.warn("Email configuration not properly set up. Skipping email verification for user: {}", toEmail);
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Verify your email address");

            String verificationLink = frontendUrl + "/verify-email?token=" + token;
            String content = String.format("""
                    <html>
                        <body>
                            <h2>Email Verification</h2>
                            <p>Thank you for registering with our application.
                             Please click the link below to verify
                             your email address:</p>
                            <p><a href="%s">Verify Email</a></p>
                            <p>If you didn't create an account, you can safely ignore this email.</p>
                        </body>
                    </html>
                    """, verificationLink);

            helper.setText(content, true);
            mailSender.send(message);
            log.info("Verification email sent successfully to: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send verification email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send verification email", e);
        }
    }
    
    /**
     * Sends an email to the customer with the current location/status of their order
     * 
     * @param order The order with updated location/status information
     */
    public void sendOrderLocationUpdate(Order order) {
        if (order.getCustomer() == null || order.getCustomer().getEmail() == null) {
            throw new IllegalArgumentException("Customer email is required");
        }

        // Validate email configuration
        if (fromEmail == null || fromEmail.trim().isEmpty() || fromEmail.equals("your-email@gmail.com")) {
            log.warn("Email configuration not properly set up. Skipping order update email for order: {}", order.getOrderId());
            return;
        }
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(order.getCustomer().getEmail());
            helper.setSubject("Update on Your Order #" + order.getOrderId());
            
            String orderTrackingLink = frontendUrl + "/orders/" + order.getOrderId();
            String statusInfo = getStatusDescription(order.getStatus());
            String locationInfo = getLocationInfo(order);
            
            String content = String.format("""
                    <html>
                        <body>
                            <h2>Order Update</h2>
                            <p>Hello %s,</p>
                            <p>We have an update on your order #%d:</p>
                            <p><strong>Current Status:</strong> %s</p>
                            %s
                            <p>You can track your order in detail by clicking the link below:</p>
                            <p><a href="%s">Track Your Order</a></p>
                            <p>Thank you for shopping with us!</p>
                        </body>
                    </html>
                    """, order.getCustomer().getName(), order.getOrderId(), order.getStatus(), locationInfo, orderTrackingLink);

            helper.setText(content, true);
            mailSender.send(message);
            log.info("Order update email sent successfully for order: {}", order.getOrderId());
        } catch (MessagingException e) {
            log.error("Failed to send order location update email for order: {}", order.getOrderId(), e);
            throw new RuntimeException("Failed to send order location update email", e);
        }
    }
    

    private String getStatusDescription(OrderStatus status) {
        return switch (status) {
            case PENDING -> "Your order is being processed";
            case CONFIRMED -> "Your order has been confirmed and is being prepared";
            case SHIPPED -> "Your order is on the way to you";
            case DELIVERED -> "Your order has been delivered";
            case CANCELLED -> "Your order has been cancelled";
            default -> "Status information unavailable";
        };
    }
    
    /**
     * Generates location information based on order data
     */
    private String getLocationInfo(Order order) {
        StringBuilder locationInfo = new StringBuilder();
        
        if (order.getStatus() == OrderStatus.SHIPPED && order.getShipper() != null) {
            locationInfo.append(String.format("<p><strong>Shipping Partner:</strong> %s</p>", order.getShipper().getName()));
            
            if (order.getShippedDate() != null) {
                locationInfo.append(String.format("<p><strong>Shipped On:</strong> %s</p>", order.getShippedDate().toLocalDate()));
            }
            
            locationInfo.append(String.format("<p><strong>Delivery Address:</strong> %s, %s</p>", 
                    order.getShipAddress(), order.getShipCity()));
        } else if (order.getStatus() == OrderStatus.DELIVERED) {
            locationInfo.append("<p>Your order has been delivered to the specified address.</p>");
        } else {
            locationInfo.append("<p>Your order is being prepared at our facility.</p>");
        }
        
        return locationInfo.toString();
    }
}