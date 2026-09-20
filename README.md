# Patient Management

Patient Management is a Java/Spring Boot microservices project with a Vue 3 frontend. The current application supports JWT login, protected patient endpoints, patient listing, and logout from the frontend.

## Architecture

The repository contains the following applications:

| Application | Port | Responsibility |
| --- | ---: | --- |
| `frontend` | `5173` | Vue 3/Vite user interface |
| `api-gateway` | `4004` | Routes client requests and validates JWTs |
| `auth-service` | `4005` | Authenticates users and issues JWTs |
| `patient-service` | `4000` | Provides patient CRUD endpoints |
| `billing-service` | — | Billing account gRPC service |
| `analytics-service` | — | Consumes patient events for analytics |

The API Gateway exposes:

- `POST /auth/login` → Auth Service
- `GET /auth/validate` → Auth Service
- `/api/patients/**` → Patient Service with JWT validation

Patient events are published through Kafka. Patient Service also contains the gRPC client integration used for billing communication.

## Technology Stack

- Java 21
- Spring Boot 4.1.1
- Spring Security and JJWT 0.12.6
- Spring Cloud Gateway
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- gRPC and Protocol Buffers
- OpenAPI/Swagger
- Vue 3, Vue Router, Axios, Vite
- Tailwind CSS 4
- Maven and npm

## Prerequisites

- Java 21
- Maven 3.8+ or the Maven wrapper included in each backend service
- Node.js `22.18+` (or Node.js `24.12+`)
- npm
- PostgreSQL
- Apache Kafka for event-driven features

## Running the Project

### 1. Start backend dependencies

Start PostgreSQL and Kafka using your preferred local or containerized setup. This repository does not currently include a `docker-compose.yml`; database-related PostgreSQL files are stored in the `*-db` directories.

### 2. Start the backend services

Run each service from its own directory:

```bash
cd auth-service
./mvnw spring-boot:run
```

```bash
cd patient-service
./mvnw spring-boot:run
```

```bash
cd api-gateway
./mvnw spring-boot:run
```

Start `billing-service` and `analytics-service` in the same way when those integrations are needed.

On Windows, use `mvnw.cmd` instead of `./mvnw`.

### 3. Start the frontend

```bash
cd frontend
npm install
npm run dev
```

Open <http://localhost:5173>.

The Vite development server proxies `/auth` and `/api` requests to the API Gateway at `http://localhost:4004`.

## Frontend Features

- Login form at `/login`
- Protected patient list at `/patients`
- JWT stored in `localStorage` under `patient-management-token`
- Patient list with name, email, date of birth, address, and record ID
- Profile menu with logout
- Redirect to `/login` when a patient request is unauthorized

## API Usage

### Login

```bash
curl -X POST http://localhost:4004/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"testuser@test.com","password":"password123"}'
```

The response contains a JWT token:

```json
{
  "token": "your-jwt-token"
}
```

### Get patients

Pass the token using the standard Bearer scheme:

```bash
curl http://localhost:4004/api/patients \
  -H "Authorization: Bearer your-jwt-token"
```

Available patient endpoints:

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/patients` | List all patients |
| `GET` | `/api/patients/{id}` | Get one patient |
| `POST` | `/api/patients` | Create a patient |
| `PUT` | `/api/patients/{id}` | Update a patient |
| `DELETE` | `/api/patients/{id}` | Delete a patient |

Example request files are available under [`api-requests`](./api-requests).

## API Documentation

The backend services include SpringDoc OpenAPI support. When a service is running, its Swagger UI is available at:

```text
http://localhost:<service-port>/swagger-ui/index.html
```

For example:

- <http://localhost:4000/swagger-ui/index.html>
- <http://localhost:4005/swagger-ui/index.html>

## Project Structure

```text
patient-management/
├── api-gateway/          # Gateway routes and JWT validation
├── auth-service/         # Login and JWT generation
├── patient-service/      # Patient CRUD and Kafka/gRPC integrations
├── billing-service/      # Billing gRPC service
├── analytics-service/    # Patient event consumer
├── frontend/             # Vue 3 application
├── integration-tests/   # REST integration tests
├── api-requests/         # HTTP request examples
├── grpc-requests/        # gRPC request examples
├── auth-service-db/      # PostgreSQL runtime files
├── patient-service-db/   # PostgreSQL runtime files
└── docker-related/       # PostgreSQL runtime files
```

## Testing and Builds

Run backend tests for an individual service:

```bash
cd patient-service
./mvnw test
```

Run integration tests:

```bash
cd integration-tests
mvn test
```

Build the frontend:

```bash
cd frontend
npm run build
```

Run frontend type checking separately:

```bash
cd frontend
npm run type-check
```

## Development Notes

- Keep secrets and database credentials outside committed source files.
- Use the API Gateway (`4004`) from the frontend rather than calling backend services directly.
- Protected requests require an `Authorization: Bearer <token>` header.
- Kafka and gRPC integrations require their corresponding infrastructure to be running.

## License

No license file is currently included in the repository.
