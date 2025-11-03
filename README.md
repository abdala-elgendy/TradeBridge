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

## 📚 Learning Resources & Improvement Roadmap

This project includes a comprehensive backend learning roadmap to deepen your knowledge and apply various advanced topics.

### 🎯 Getting Started with Improvements

1. **New to the project?** Start here:
   - Read [TOPIC_PRIORITY_GUIDE.md](./TOPIC_PRIORITY_GUIDE.md) to choose your learning path
   - Follow [QUICK_START_IMPLEMENTATION.md](./QUICK_START_IMPLEMENTATION.md) for hands-on tutorials

2. **Ready for advanced topics?**
   - See [BACKEND_IMPROVEMENTS_ROADMAP.md](./BACKEND_IMPROVEMENTS_ROADMAP.md) for 12 comprehensive topics
   - Each topic includes: why, what, how, and code examples

### 📖 Available Topics

**Phase 1: Foundation & Quality (Weeks 1-4)**
1. ✅ Comprehensive Logging & Monitoring
2. ✅ API Documentation with Swagger
3. ✅ Global Exception Handling
4. ✅ Testing Strategy (Unit, Integration, E2E)

**Phase 2: Performance & Scalability (Weeks 5-8)**
5. ✅ Caching Strategy with Redis
6. ✅ Asynchronous Processing with Message Queues
7. ✅ API Rate Limiting
8. ✅ Circuit Breaker Pattern with Resilience4j

**Phase 3: Advanced Architecture (Weeks 9-12)**
9. ✅ Distributed Tracing with Zipkin
10. ✅ Containerization with Docker
11. ✅ CI/CD Pipeline with GitHub Actions
12. ✅ Advanced Security Features (OAuth2, 2FA)

### 🎓 Learning Approach

Each topic in the roadmap includes:
- **Why**: Business value and use cases
- **What**: Concepts and patterns
- **How**: Step-by-step implementation guide
- **Code**: Complete working examples
- **Resources**: Links to documentation and tutorials

### 💡 Quick Wins to Start Today

Implement these 4 topics in ~2 weeks for immediate impact:

```bash
# 1. Comprehensive Logging (2-3 hours)
# 2. Swagger API Documentation (2-3 hours)  
# 3. Global Exception Handling (3-4 hours)
# 4. Basic Testing Suite (5-8 hours)
```

See [QUICK_START_IMPLEMENTATION.md](./QUICK_START_IMPLEMENTATION.md) for detailed steps.

---

## License

This project is open source and available under the [MIT License](LICENSE).

---
