# Patient Management System

A modern microservices-based patient management platform built with Java and Spring Boot. The system provides comprehensive healthcare management capabilities including patient data management, authentication, billing, and analytics.

## 🏗️ System Architecture

This is a **microservices architecture** with the following services:

### Core Services

- **Auth Service** (Port 4005)
  - JWT-based authentication and authorization
  - User credential management
  - Security token generation and validation
  - Built with Spring Security

- **Patient Service** (Port 4000)
  - Patient data management (CRUD operations)
  - Health records and medical history
  - Integration with Kafka for event streaming
  - gRPC communication with other services
  - PostgreSQL database

- **Billing Service**
  - Financial transaction management
  - Billing records and invoice generation
  - Payment processing

- **Analytics Service**
  - Data analysis and reporting
  - Patient statistics and insights
  - Business intelligence

- **API Gateway**
  - Central entry point for all client requests
  - Request routing and load balancing
  - Spring Cloud Gateway Server

## 🔧 Tech Stack

- **Language**: Java 21
- **Framework**: Spring Boot 4.x
- **Communication Protocols**:
  - REST API (HTTP)
  - gRPC for inter-service communication
  - Kafka for event-driven messaging
- **Database**: PostgreSQL
- **Authentication**: JWT (JJWT)
- **API Documentation**: OpenAPI/Swagger (SpringDoc)
- **Build Tool**: Maven
- **Containerization**: Docker

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL 13+ (or use Docker)
- Apache Kafka (included in docker setup)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/sezarfen/develop-more-with-java.git
cd patient-management
```

### 2. Setup with Docker Compose

The easiest way to run the entire system is with Docker:

```bash
cd docker-related
docker-compose up -d
```

This will start:
- PostgreSQL database
- Apache Kafka
- All microservices

### 3. Build Individual Services (Optional)

To build a specific service:

```bash
cd auth-service
mvn clean install
mvn spring-boot:run
```

### 4. Access Services

- **Patient Service**: http://localhost:4000
- **Auth Service**: http://localhost:4005
- **Swagger UI**: Available at `/swagger-ui.html` on each service

## 📡 API Endpoints

### Auth Service (Port 4005)

```bash
POST   /api/auth/login         # User login
POST   /api/auth/register      # User registration
POST   /api/auth/refresh       # Refresh JWT token
POST   /api/auth/logout        # User logout
```

### Patient Service (Port 4000)

```bash
GET    /api/patients           # Get all patients
GET    /api/patients/{id}      # Get patient by ID
POST   /api/patients           # Create new patient
PUT    /api/patients/{id}      # Update patient
DELETE /api/patients/{id}      # Delete patient
```

## 🔐 Authentication

The system uses **JWT (JSON Web Tokens)** for stateless authentication:

1. User logs in via Auth Service
2. Auth Service returns JWT token
3. Include token in `Authorization: Bearer <token>` header for subsequent requests
4. API Gateway validates token on each request

## 📨 Event-Driven Architecture

The system uses **Apache Kafka** for asynchronous communication:

- **Patient Service** publishes patient events to Kafka topics
- Other services consume these events for real-time updates
- Ensures loose coupling between services

## 🔌 gRPC Communication

Patient Service uses gRPC for high-performance inter-service communication:

- Defined in `.proto` files
- Protocol Buffer serialization
- Suitable for internal service-to-service calls

## 📝 Project Structure

```
patient-management/
├── auth-service/          # Authentication & Authorization
├── patient-service/       # Patient data management
├── api-gateway/           # API Gateway & Routing
├── billing-service/       # Billing & Payments
├── analytics-service/     # Analytics & Reporting
├── auth-service-db/       # Auth service database setup
├── docker-related/        # Docker & Docker Compose configuration
├── grpc-requests/         # gRPC request examples
└── api-requests/          # API request examples
```

## 🧪 Testing

Run tests for a specific service:

```bash
cd <service-name>
mvn test
```

## 🐛 Debugging

Enable debug logging in `application.properties`:

```properties
logging.level.com.pm=DEBUG
logging.level.org.springframework.security=DEBUG
```

## 📊 Database Schema

The system uses PostgreSQL with the following main entities:

- **User** (Auth Service)
  - username, email, password (encrypted)
  - roles and permissions

- **Patient** (Patient Service)
  - patient_id, name, age, contact info
  - medical history, health records
  - appointment data

## 🔗 Service Communication Flow

```
Client Request
    ↓
API Gateway (Spring Cloud Gateway)
    ↓
├─→ Auth Service (JWT validation)
├─→ Patient Service (REST/gRPC)
├─→ Billing Service
└─→ Analytics Service
    ↓
PostgreSQL Database
    ↓
Kafka Events (Async messaging)
```

## 🛠️ Development Tips

### Adding a New Service

1. Create a new Maven module following existing service structure
2. Add Spring Boot parent POM configuration
3. Register with API Gateway via routing rules
4. Add to Docker Compose for orchestration

### Monitoring Kafka Messages

```bash
# From docker-related directory
docker exec -it kafka kafka-console-consumer --bootstrap-server kafka:9092 --topic patient-events
```

### Database Connection

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/patient_db
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

## 📚 Documentation

- OpenAPI/Swagger docs available at: `/swagger-ui.html` on each service
- Check `HELP.md` files in each service for additional details

## 🤝 Contributing

1. Create a feature branch
2. Commit changes with meaningful messages
3. Push to repository
4. Submit pull request for review

## 📄 License

This project is open source and available under the MIT License.

## 👥 Support

For issues and questions, please open an issue on the GitHub repository.

---

**Built with ❤️ using Java, Spring Boot, and Microservices Architecture**
