# 📚 RentRead – Online Book Rental System (Spring Boot REST API)

RentRead is a RESTful API built using **Spring Boot** and **MySQL** to manage an online book rental system. It supports **user authentication, role-based authorization, book management, and rental management** with proper validations and error handling.

This project is designed using **industry-standard layered architecture** and follows best practices for security, DTO usage, logging, and testing.

---

## 🚀 Tech Stack

* Java 17+
* Spring Boot 3+
* Spring Security (Basic Auth)
* Spring Data JPA (Hibernate)
* MySQL
* Lombok
* JUnit 5, Mockito, MockMvc
* Gradle

---

## 🏗️ Architecture

```
Controller → DTO → Service → Repository → Entity → Database
                    ↓
              Exception Handling
                    ↓
                 Security
```

---

## 🔐 Authentication & Authorization

* **Basic Authentication**
* **Two Roles:**
  * `ADMIN`
  * `USER`

### Public Endpoints

* Signup
* Login

### Secured Endpoints

* All others

### Authorization Rules

| Endpoint             | Access      |
| -------------------- | ----------- |
| `/auth/**`         | Public      |
| `GET /books/**`    | USER, ADMIN |
| `POST /books/**`   | ADMIN       |
| `PUT /books/**`    | ADMIN       |
| `DELETE /books/**` | ADMIN       |
| `/rentals/**`      | USER, ADMIN |

---

## 👤 User Features

* Register using:
  * Email (unique)
  * Password (BCrypt encrypted)
  * First Name
  * Last Name
  * Role (optional → defaults to USER)
* Login using email + password

---

## 📖 Book Features

* Fields:
  * Title
  * Author
  * Genre
  * Availability Status (`AVAILABLE`, `NOT_AVAILABLE`)
* Anyone authenticated can:
  * View books
* Only ADMIN can:
  * Create
  * Update
  * Delete books

---

## 📦 Rental Features

* A user can rent a book
* A user **cannot rent more than 2 books at a time**
* A book becomes `NOT_AVAILABLE` once rented
* User can return a book
* On return:
  * `returnDate` is set
  * Book becomes `AVAILABLE`

---

## ❗ Validations & Error Handling

* Centralized exception handling using `@RestControllerAdvice`
* Proper HTTP status codes:
  * 400 – Bad Request
  * 401 – Unauthorized
  * 403 – Forbidden
  * 404 – Not Found
  * 500 – Internal Server Error

### Error Response Format

```json
{
  "message": "User has already reached maximum book rental limit!",
  "httpStatus": "BAD_REQUEST",
  "localDateTime": "2025-03-10T21:10:53"
}
```

---

## 📝 Logging

* Uses SLF4J + Logback
* Logs:
  * Important business operations
  * API events
  * Errors and exceptions

---

## 🧪 Testing

* Uses:
  * JUnMockMvc
* Tests include:
  * Controller tests
  * Service tests
  * Business rule validation (rental limit)

### Run Tests

```bash
./gradlew test
```

---

## 🔐 Using Secured APIs

All secured APIs use  **Basic Auth** :

```
Username: email
Password: password
```

In Postman:

* Authorization → Type → Basic Auth

---

## 📬 API Documentation

### Base URL

```
http://localhost:8081
```

---

### 1️⃣ Create Admin Account

`POST /auth/signup`

```json
{
  "email": "admin@rentread.com",
  "password": "admin123456",
  "firstName": "admin",
  "lastName": "test",
  "role": "ADMIN"
}
```

Response Code: `201`

---

### 2️⃣ Create User Account

`POST /auth/signup`

```json
{
  "email": "user.test@example.com",
  "password": "user123456",
  "firstName": "RegularTest",
  "lastName": "UserTest"
}
```

Response Code: `201`

---

### 3️⃣ Login

`POST /auth/login`

```json
{
  "email": "admin@rentread.com",
  "password": "admin123456"
}
```

Response Code: `200`

---

### 4️⃣ Create Book (ADMIN)

`POST /books`

---

### 5️⃣ Update Book (ADMIN)

`PUT /books/{bookId}`

---

### 6️⃣ Delete Book (ADMIN)

`DELETE /books/{bookId}`

Response Code: `204`

---

### 7️⃣ Rent Book

`POST /rentals/users/{userId}/books/{bookId}`

Response Code: `201`

---

### 8️⃣ Get Active Rentals

`GET /rentals/active-rentals/users/{userId}`

Response Code: `200`

---

### 9️⃣ Return Book

`PUT /rentals/{rentalId}`

Response Code: `204`

---

### 🔟 Rental Limit Error

If user already has 2 active rentals:

```json
{
  "message": "User has already reached maximum book rental limit!",
  "httpStatus": "BAD_REQUEST",
  "localDateTime": "2025-03-10T21:10:53"
}
```

Response Code: `400`

---

## 🧠 Design Decisions

* Used **Basic Auth** for simplicity
* Used **Role-based authorization**
* Enforced rental limit in **service layer**
* Used **DTOs** to avoid exposing entities
* Used **layered architecture**
* Used **BCrypt** for password encryption

---

## 👨‍💻 Author

**SanthoshKumar K**
Software Engineer

---
