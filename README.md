# Bank Management Syste

A robust and secure banking management system built with Spring Boot and Angular, offering comprehensive banking operations with role-based access control.

## 🚀 Features

### User Management
- Role-based access control (ADMIN, USER, EMPLOYEE)
- Secure authentication using JWT
- User profile management
- Password encryption and security measures

### Account Management
- Create and manage bank accounts
- Real-time account balance tracking
- Account status monitoring
- Multi-currency support

### Transaction Management
- Real-time transaction processing
- Transaction history with Elasticsearch integration
- Various transaction types support
- Scheduled transactions
- Transaction approval workflow for high-value transfers

### Loan Management
- Loan application processing
- Multiple loan types support
- Approval workflow
- Interest calculation
- Repayment scheduling

### Bill Payment System
- Utility bill payments
- Recurring payment setup
- Payment history tracking
- Bill reminder notifications

### Analytics and Reporting
- Transaction analytics
- Account statements
- Financial reports
- Audit logging

## 🛠 Tech Stack

- **Backend**: Spring Boot 3.x
- **Security**: Spring Security with JWT
- **Database**: PostgreSQL
- **Search Engine**: Elasticsearch (for transaction history)
- **Frontend**: Angular
- **Build Tool**: Maven
- **API Documentation**: OpenAPI (Swagger)
- **Testing**: JUnit, Mockito
- **Other Tools & Libraries**:
    - MapStruct for DTO mapping
    - Lombok for boilerplate reduction
    - Liquibase for database versioning
    - Docker for containerization

## 📋 Prerequisites

- JDK 17 or later
- Maven 3.6+
- PostgreSQL 13+
- Elasticsearch 7.x
- Docker & Docker Compose (optional)
- Node.js & npm (for Angular frontend)

## 🚀 Getting Started

1. **Clone the repository**
```bash
git clone https://github.com/HMZElidrissi/bank-management-system.git
cd bank-management-system
```

2. **Configure application properties**
- Update `application.yml` with your database credentials
- Configure Elasticsearch connection details

3. **Build the project**
```bash
./mvnw clean install
```

4. **Run with Docker**
```bash
docker-compose up
```

Or **Run without Docker**
```bash
./mvnw spring-boot:run
```

5. **Access the application**
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Frontend: http://localhost:4200

## 📚 API Documentation

The API documentation is available through Swagger UI. After starting the application, visit:
```
http://localhost:8080/swagger-ui.html
```

## 🔑 Role-Based Access

### ADMIN
- Full system access
- User management
- Account management
- Transaction approval
- Loan approval
- System configuration

### USER (Customer)
- Personal account management
- Transaction operations
- Loan requests
- Bill payments
- Statement viewing

### EMPLOYEE
- Customer account viewing
- Transaction processing
- Loan application processing
- Customer support operations
