# Bank Management System

A robust banking management system built with Spring Boot and Angular, this repository serves as a guide and foundation of best practices for building enterprise-grade applications.

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
- copy `.env.example` to `.env` and update the values
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

> to run a dingle service separately, run the following command for example:
```bash
docker-compose -f docker/services/postgres.yml up
docker-compose -f docker/services/elasticsearch.yml up
```

Or **Run without Docker**
```bash
./mvnw spring-boot:run
```

> to run a service separately, run the following command for example:
```bash
docker-compose -f docker/services/postgres.yml up
docker-compose -f docker/services/elasticsearch.yml up
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
