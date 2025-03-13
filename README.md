# **Service Provider Platform**

A multi-role platform for managing home services, connecting **customers**, **technicians**, and **managers** in a structured service marketplace.

## **Features**

### **1. User Roles & Functionality**

- **Customers**
    - Browse available services and subservices.
    - Create orders for a selected service.
    - Review offers from verified technicians.
    - Select a technician and proceed with payment.
- **Technicians**
    - Once verified, submit offers for service orders.
    - Complete assigned orders.
- **Managers**
    - Define service categories and subservices.
    - Approve/reject technician registrations.
    - Oversee order progress and ensure service quality.

### **2. Order Workflow**

1. **Customer submits an order** for a service.
2. **Verified technicians** submit offers for the order.
3. **Customer selects a technician**, finalizing the service request.
4. **Order progresses through multiple statuses**, ensuring transparency.
5. **Payment is processed** upon order completion.

## **Tech Stack**

- **Backend:** Java, Hibernate, Spring Boot, Spring Data JPA, Spring MVC, Spring Security
- **Database:** PostgreSQL
- **Testing:** JUnit, Mockito, Postman
- **Architecture:**
    - **Controller**: Handles HTTP requests and responses
    - **Service**: Contains business logic
    - **Repository**: Manages data access and interacts with the database
    - **DTO (Data Transfer Object)**: Facilitates data transfer between layers
    - **Entity**: Represents database models (mapped to database tables)
    - **Exception Handling**: Centralized handling of exceptions throughout the application

## **Development Phases**

1. **Core Java & Hibernate** – Domain model and ORM-based persistence.
2. **Spring Boot & Spring Data JPA** – RESTful APIs and database interactions.
3. **Spring MVC** – Structured controller-service-repository architecture.
4. **Spring Security** – Role-based authentication & authorization (RBAC).


>[!NOTE]
>Phase 3 and phase 4 are built on top of phase 2.
