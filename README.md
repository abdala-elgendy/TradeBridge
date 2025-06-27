# TradeBridge

TradeBridge is a secure trading and order management platform built with Java and Spring Boot. It enables suppliers, customers, and admins to manage products, orders, and favorites with robust JWT authentication, role-based access, and automated workflow features.

---

## Key Features

### Authentication & Security
- **JWT-Based Security:** Every API call is protected via token-based authentication and a custom security filter.
- **Role-Based Authorization:** Fine-grained endpoint protection for ADMIN, SUPPLIER, and CUSTOMER using Spring Security.
- **Email Verification:** Users must verify their email during registration.
- **Password Hashing:** User passwords are securely hashed with BCrypt.

### Product Management
- **Suppliers** can add, update, and remove products, manage stock, and view sales.
- **Customers** can view products, purchase, and see top-selling products.
- **Thread-Safe Stock Updates:** Product stock and sales are safely updated in concurrent environments.

### Order Management
- **Customers** can place orders, view their order history or by status, and cancel their own orders.
- **Admins** can cancel any order for management or compliance purposes.
- **Automated Shipping:** Orders confirmed the previous day are automatically marked as "shipped" at 6:00 PM daily.

### Favorites System
- Users can add, view, and remove favorite products for quick access.

### Notification System
- **Email Verification:** Automatic emails for new user signups.
- **Order Status Updates:** Customers receive email updates as their order status or location changes.

---

## REST API Overview

- `/api/v1/auth/register` – Register a new user (customer, supplier, shipper, or admin).
- `/api/v1/auth/login` – Authenticate and receive a JWT.
- `/api/v1/auth/verify-email` – Verify user email with a token.
- `/api/v1/products` – CRUD for products (suppliers) and browsing/purchase (customers).
- `/api/v1/products/top-selling` – List top-selling products.
- `/api/orders` – Place new order (customer).
- `/api/orders/my-orders` – Get all orders for the authenticated customer.
- `/api/orders/my-orders/{orderId}` – Get, cancel, or view a specific customer order.
- `/api/orders/{orderId}/cancel` – Admin cancels an order.
- `/api/favorites` – Add, view, and remove favorite products.

> All endpoints require a valid JWT except registration, login, and verification.

---

## Security Example (Spring Security)

```java
http.csrf(csrf -> csrf.disable())
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/v1/auth/**").permitAll()
        .requestMatchers("/api/v1/public/**").permitAll()
        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
        .requestMatchers("/api/orders/my-orders/**").hasRole("CUSTOMER")
        .requestMatchers("/api/orders").hasRole("CUSTOMER")
        .requestMatchers("/api/orders/**").hasAnyRole("ADMIN", "CUSTOMER")
        .anyRequest().authenticated())
    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authenticationProvider(authenticationProvider())
    .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
```

---

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/abdala-elgendy/TradeBridge.git
   cd TradeBridge
   ```

2. **Configure Environment:**
   - Set up database and SMTP configuration in `application.properties`.
   - Set JWT secret, expiration, and frontend URL for email verification.

3. **Build and Run:**
   ```sh
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

---

## Contribution

Contributions are welcome! Fork this repo, create a branch, and submit a pull request.

---

## License

This project is open source and available under the [MIT License](LICENSE).

---
