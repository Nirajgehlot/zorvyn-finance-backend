# Finance Data Processing and Access Control Backend

## Overview

This is a production-ready backend system for managing financial records with secure authentication, strict role-based access control, and analytical dashboard APIs.

The application is designed following real-world backend practices such as controlled user onboarding, JWT-based authentication, feature-based modular architecture, and clean API design to ensure scalability, security, and maintainability.

---

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Security
- JWT Authentication
- Spring Data JPA (Hibernate)
- MySQL
- Lombok
- Maven

---

## Key Features

### User and Role Management

- First user is automatically created as ADMIN (bootstrap mechanism)
- Public registration is allowed only once (for first ADMIN creation)
- After first user, public registration is disabled
- ADMIN can create:
    - ADMIN
    - ANALYST
    - VIEWER
- ANALYST cannot create users
- VIEWER cannot create users
- User status management (ACTIVE / INACTIVE)

---

### Authentication and Security

- JWT-based authentication
- BCrypt password encryption
- Stateless session management
- Role-based access control using `@PreAuthorize`
- Proper error handling:
    - 401 Unauthorized → invalid credentials
    - 403 Forbidden → access denied

---

### Financial Records

- Create, update, delete records
- Soft delete implementation (records are not permanently removed)
- Filtering:
    - by type (INCOME / EXPENSE)
    - by category
    - by date
- Pagination and sorting support

---

### Dashboard APIs

- Total income
- Total expenses
- Net balance
- Category-wise aggregation
- Monthly trends
- Recent activity

---

### Validation and Error Handling

- Request validation using annotations
- Global exception handling
- Clean HTTP responses:
    - 400 → validation errors
    - 401 → authentication errors
    - 403 → authorization errors
    - 404 → resource not found

---

## Architecture

The project follows **Feature-Based Modular Architecture (Production Style)**.

Modules are organized by feature:

- user
- record
- dashboard
- security
- common

Each module contains:

- Controller → handles API requests
- Service → contains business logic
- Repository → handles database operations
- DTO → request and response objects
- Entity → database mapping

This structure ensures clean separation of concerns and scalability.

---

## Project Structure

src/main/java/com/niraj/zorvynfinancebackend

- common     → exception handling and configuration
- security   → JWT, authentication, filters
- user       → user management
- record     → financial records
- dashboard  → analytics APIs

---

## Registration and User Creation Flow

The system uses a controlled onboarding mechanism:

- If the database has no users:
    - `/api/auth/register` creates the first ADMIN user

- After the first user is created:
    - Public registration is disabled
    - Any further registration attempt returns 403 Forbidden

- All users must be created by ADMIN using `/api/users`

This ensures:
- secure onboarding
- no unauthorized user creation
- proper role control

---

## Role-Based Access Control

| Feature / Action | VIEWER | ANALYST | ADMIN |
|------------------|--------|---------|-------|
| Login | Yes | Yes | Yes |
| View Records | No | Yes | Yes |
| Create Records | No | No | Yes |
| Update Records | No | No | Yes |
| Delete Records | No | No | Yes |
| Dashboard Access | Yes | Yes | Yes |
| Create VIEWER | No | No | Yes |
| Create ANALYST | No | No | Yes |
| Create ADMIN | No | No | Yes |

---

## Getting Started

### Prerequisites

- Java 21
- Maven
- MySQL (8+)

---

### Setup Instructions

#### Step 1: Clone Repository

git clone https://github.com/Nirajgehlot/zorvyn-finance-backend.git  
cd zorvyn-finance-backend

---

#### Step 2: Configure Database

Open:

src/main/resources/application.properties

Update:

spring.datasource.url=jdbc:mysql://localhost:3306/zorvyn_finance?createDatabaseIfNotExist=true  
spring.datasource.username=your_mysql_username  
spring.datasource.password=your_mysql_password

Important:
- Database will be created automatically if not present
- No manual database creation is required

---

#### Step 3: Run Application

mvn spring-boot:run

Application will start at:

http://localhost:8080

---

## Authentication Flow

1. First user registers → becomes ADMIN
2. Login via `/api/auth/login`
3. JWT token is generated
4. Token must be passed in header:

Authorization: Bearer <token>

5. All secured APIs require this token

---

## API Documentation

### Base URL

http://localhost:8080

---

### Register (First Admin Only)

POST /api/auth/register

Request Body:

{
"name": "Admin",
"email": "admin@test.com",
"password": "pass123"
}

Note:
- First call → ADMIN created
- Next calls → 403 Forbidden

---

### Login

POST /api/auth/login

Request Body:

{
"email": "admin@test.com",
"password": "pass123"
}

Response:

{
"token": "jwt_token"
}

---

### Create User (ADMIN Only)

POST /api/users

Headers:

Authorization: Bearer <token>

Request Body:

{
"name": "User",
"email": "user@test.com",
"password": "pass123",
"role": "ANALYST",
"status": "ACTIVE"
}

---

### Create Record (ADMIN)

POST /api/records

{
"amount": 1000,
"type": "INCOME",
"category": "Salary",
"recordDate": "2026-04-01",
"description": "Monthly salary"
}

---

### Get Records

GET /api/records?page=0&size=5

---

### Dashboard APIs

GET /api/dashboard/summary  
GET /api/dashboard/category-summary  
GET /api/dashboard/monthly-trend

---

## Postman Testing Guide

Step 1: Register First Admin  
POST /api/auth/register

Request Body:

{
"name": "Admin",
"email": "admin@test.com",
"password": "pass123"
}


Step 2: Login  
POST /api/auth/login

Request Body:

{
"email": "admin@test.com",
"password": "pass123"
}

Copy the token

Step 3: Set Authorization  
In Postman → Authorization → Bearer Token → paste token

Step 4: Create Users (ADMIN only)  
POST /api/users

Step 5: Test Security

- Without token → 401 Unauthorized
- Analyst creating user → 403 Forbidden
- Admin creating user → Success

Step 6: Test Records

- Create record (ADMIN)
- Fetch records (ANALYST / ADMIN)

Step 7: Test Dashboard

GET /api/dashboard/summary

---

## Enhancements Implemented

- JWT authentication
- Role-based access control
- Soft delete
- Pagination
- Dashboard analytics
- Controlled onboarding (bootstrap admin)

---

## Author

Niraj Gehlot