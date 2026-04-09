# Finance Data Processing and Access Control Backend

## Overview

This project is a backend system for managing financial records with role-based access control and dashboard analytics.

It demonstrates backend architecture, API design, data modeling, authentication, authorization, and business logic implementation in a clean and maintainable way.

---

## Tech Stack

* Java 21
* Spring Boot 4
* Spring Security
* JWT Authentication
* Spring Data JPA (Hibernate)
* MySQL
* Lombok
* Maven

---

## Features

### User and Role Management

* Create users
* Assign roles (ADMIN, ANALYST, VIEWER)
* Manage user status (ACTIVE / INACTIVE)

### Authentication and Security

* JWT-based authentication
* BCrypt password encryption
* Role-based access control using `@PreAuthorize`

### Financial Records

* Create, update, delete records
* Filter records by type, category, and date
* Pagination and sorting
* Soft delete implementation

### Dashboard APIs

* Total income
* Total expenses
* Net balance
* Category-wise totals
* Monthly trends
* Recent activity

### Validation and Error Handling

* Request validation
* Global exception handling
* Proper HTTP status codes

---

## Architecture

The project follows a layered modular architecture:

* Controller Layer – Handles HTTP requests
* Service Layer – Business logic
* Repository Layer – Database interaction
* DTO Layer – Data transfer
* Security Layer – Authentication and authorization

---

## Project Structure

src/main/java/com/niraj/zorvynfinancebackend

* common
* security
* user
* record
* dashboard

---

## Role Permissions

| Feature               | ADMIN | ANALYST | VIEWER |
| --------------------- | ----- | ------- | ------ |
| Manage Users          | Yes   | No      | No     |
| Create Records        | Yes   | No      | No     |
| View Records          | Yes   | Yes     | No     |
| Update/Delete Records | Yes   | No      | No     |
| Dashboard Access      | Yes   | Yes     | Yes    |

---

## Getting Started

### Prerequisites

* Java 21
* Maven
* MySQL (8 or above)

---

### Setup Steps

#### 1. Clone the repository

git clone https://github.com/YOUR_USERNAME/zorvyn-finance-backend.git
cd zorvyn-finance-backend

#### 2. Create database

CREATE DATABASE zorvyn_finance;

### 3. Configure application.properties

Update database credentials before running the application:

spring.datasource.url=jdbc:mysql://localhost:3306/zorvyn_finance
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

Replace:
- your_mysql_username → your local MySQL username
- your_mysql_password → your local MySQL password

Note:
Make sure MySQL is running and the database "zorvyn_finance" is already created.

If database is not created, run:
CREATE DATABASE zorvyn_finance;

#### 4. Run the application

mvn spring-boot:run

Application runs at:
http://localhost:8080

---

## Authentication

POST /api/auth/login

Request:
{
"email": "[admin@test.com](mailto:admin@test.com)",
"password": "password"
}

Response:
{
"token": "your_jwt_token"
}

Use token in header:
Authorization: Bearer <token>

---

## API Endpoints

### Auth

| Method | Endpoint        | Description         | Auth |
| ------ | --------------- | ------------------- | ---- |
| POST   | /api/auth/login | Login and get token | No   |

---

### Users

| Method | Endpoint               | Description    | Auth  |
| ------ | ---------------------- | -------------- | ----- |
| POST   | /api/users             | Create user    | ADMIN |
| GET    | /api/users             | Get all users  | ADMIN |
| GET    | /api/users/{id}        | Get user by id | ADMIN |
| PATCH  | /api/users/{id}/role   | Update role    | ADMIN |
| PATCH  | /api/users/{id}/status | Update status  | ADMIN |

---

### Records

| Method | Endpoint            | Description        | Auth           |
| ------ | ------------------- | ------------------ | -------------- |
| POST   | /api/records        | Create record      | ADMIN          |
| GET    | /api/records        | Get all records    | ADMIN, ANALYST |
| GET    | /api/records/{id}   | Get record by id   | ADMIN, ANALYST |
| PUT    | /api/records/{id}   | Update record      | ADMIN          |
| DELETE | /api/records/{id}   | Soft delete record | ADMIN          |
| GET    | /api/records/filter | Filter records     | ADMIN, ANALYST |

---

### Dashboard

| Method | Endpoint                        | Description                | Auth |
| ------ | ------------------------------- | -------------------------- | ---- |
| GET    | /api/dashboard/summary          | Income and expense summary | ALL  |
| GET    | /api/dashboard/category-summary | Category totals            | ALL  |
| GET    | /api/dashboard/monthly-trend    | Monthly trend              | ALL  |
| GET    | /api/dashboard/recent-activity  | Recent activity            | ALL  |

---

## Enhancements Implemented

* JWT Authentication
* Role-based authorization
* Pagination
* Soft delete
* Dashboard analytics

---

## Assumptions

* Roles are predefined (ADMIN, ANALYST, VIEWER)
* Soft delete is used instead of hard delete
* MySQL is used for persistence
* JWT is used for authentication

---

## Notes

The project is designed with focus on clean architecture, maintainability, and real-world backend practices.

---

## Author

Niraj Gehlot
