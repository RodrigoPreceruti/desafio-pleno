# Incident Management API

> **Important:** This project requires **JDK 21**. Make sure you have JDK 21 installed and set the `JAVA_PATH` environment variable to your JDK 21 installation path (e.g., `C:\Program Files\Java\jdk-21\bin`) before building or running the project.

A Spring Boot RESTful API for managing incidents and users, featuring JWT authentication, role-based access control, and OpenAPI documentation. Designed for extensibility and ease of deployment.

---

## Table of Contents
- [Incident Management API](#incident-management-api)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
  - [Features](#features)
  - [Tech Stack](#tech-stack)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Clone the repository](#clone-the-repository)
    - [Build the project](#build-the-project)
    - [Run the application](#run-the-application)
    - [Default Admin User](#default-admin-user)
  - [Running with Docker](#running-with-docker)
  - [API Documentation](#api-documentation)
  - [Authentication \& Authorization](#authentication--authorization)
    - [Roles](#roles)
  - [API Endpoints](#api-endpoints)
    - [Auth](#auth)
    - [Users](#users)
    - [Incidents](#incidents)
  - [Testing](#testing)
  - [Environment Variables](#environment-variables)
  - [H2 Database Console](#h2-database-console)
  - [License](#license)

---

## Overview
This project provides a backend API for incident management, supporting user registration, authentication, and CRUD operations for incidents. It is built with Spring Boot, uses JWT for secure authentication, and includes role-based access (admin/basic). The API is documented with Swagger/OpenAPI.

## Features
- User registration (admin only)
- JWT-based authentication
- Role-based access control (ADMIN, BASIC)
- CRUD operations for incidents
- Pagination and ordering for incident queries
- In-memory H2 database for development/testing
- OpenAPI (Swagger) documentation
- Docker support for easy deployment

## Tech Stack
- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **Spring Security**
- **H2 Database** (in-memory)
- **Lombok**
- **JWT (java-jwt)**
- **Springdoc OpenAPI**
- **JUnit 5** (testing)
- **Docker**

## Getting Started

### Prerequisites
- Java 21+ (**JDK 21 required**)
- Maven 3.9+

**Set JAVA_PATH environment variable:**
- On Windows, set `JAVA_PATH` to your JDK 21 `bin` directory (e.g., `C:\Program Files\Java\jdk-21\bin`).
- On Linux/macOS, set `JAVA_PATH` to your JDK 21 `bin` directory (e.g., `/usr/lib/jvm/java-21-openjdk/bin`).

Ensure your `JAVA_PATH` is correctly set before proceeding.

### Clone the repository
```bash
git clone <repo-url>
cd desafio-pleno
```

### Build the project
```bash
./mvnw clean install
```

### Run the application
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

### Default Admin User
On first run, an admin user is created automatically:
- **Username:** `admin`
- **Password:** `123456`

## Running with Docker

Build the Docker image:
```bash
docker build -t incident-api .
```

Run the container:
```bash
docker run -p 8080:8080 incident-api
```

## API Documentation

Interactive API docs are available via Swagger UI:
- [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Authentication & Authorization

- **Authentication:** JWT Bearer tokens. Obtain a token via `/auth` endpoint.
- **Authorization:**
  - `ADMIN` can create users and perform all incident operations.
  - `BASIC` can only access incident endpoints.
- **Token:** Pass the JWT in the `Authorization` header as `Bearer <token>`.

### Roles
- `ADMIN`: Full access, including user management.
- `BASIC`: Limited to incident operations.

## API Endpoints

### Auth
- `POST /auth` — Authenticate and receive JWT token
  - **Body:** `{ "login": "admin", "password": "123456" }`
  - **Response:** `{ "token": "<JWT>" }`

### Users
- `POST /users` — Create a new user (ADMIN only)
  - **Body:** `{ "login": "user", "password": "pass", "role": "BASIC" }`

### Incidents
- `POST /incidents` — Create incident
- `PUT /incidents/{id}` — Update incident
- `DELETE /incidents/{id}` — Close incident
- `GET /incidents` — List incidents (paginated)
  - **Query:** `page`, `size`
- `GET /incidents/{id}` — Get incident by ID
- `GET /incidents/ordered` — List incidents ordered by creation date

> **Note:** All endpoints (except `/auth` and Swagger docs) require a valid JWT token.

## Testing

Run tests with:
```bash
./mvnw test
```

## Environment Variables

- `JWT_SECRET` — Secret key for signing JWT tokens (default: `my_secret_key`)

## H2 Database Console

Access at [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User:** `sa`
- **Password:** `password`

## License

This project is licensed under the MIT License.
