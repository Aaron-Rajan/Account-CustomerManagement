# Account & Customer Management (Spring Boot + JPA + PostgreSQL)

A clean, layered **Spring Boot** service for managing **Customers** and **Accounts** (Savings/Checking), including CRUD, deposits/withdrawals, and transfers. Built for clarity, testability, and easy local setup (Dockerized PostgreSQL).

---

## ✨ Features
- Customers: create, read, update, delete
- Accounts: create (checking/savings), read, update, delete
- Money ops: deposit, withdraw, transfer between accounts (basic validation)
- DTOs + Services + Repositories (separation of concerns)
- Global exception handling & meaningful error responses
- OpenAPI (Swagger UI) for interactive API docs
- Unit tests with JUnit & Mockito

---

## 🧱 Tech Stack
- **Java** 19+
- **Spring Boot** 3.x (Web, Validation, Data JPA)
- **PostgreSQL** (Docker/local)
- **Maven**
- **springdoc-openapi** for Swagger UI
- **JUnit/Mockito** for testing

---

## 📂 Project Structure (typical)
```
account-customer-management/
├─ src/main/java/com/fdmgroup/management/AccountCustMgmt/
│  ├─ controller/        # REST controllers
│  ├─ services/          # business logic
│  ├─ repository/        # Spring Data JPA repos
│  ├─ model/             # entities + DTOs (Account, SavingsAccount, CheckingAccount, Customer, ...)
│  └─ AccountCustMgmtApplication.java
├─ src/test/java/...     # unit tests
├─ pom.xml
└─ README.md
```

---

## 🚀 Getting Started

### Prerequisites
- JDK 19+
- Maven 3.9+
- Docker (optional but recommended for PostgreSQL)

### 1) Start PostgreSQL (Docker)
```bash
docker run -d --name pg-accounts \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=accountdb \
  -p 5432:5432 postgres:16
```

### 2) Configure App Properties
`src/main/resources/application.properties` (or use env vars):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/accountdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# swagger (springdoc)
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
```

> **Maven Swagger dependency (pom.xml):**
```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.6.0</version>
</dependency>
```

### 3) Run the App
```bash
mvn spring-boot:run
```
- API base: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

### 4) Run Tests
```bash
mvn test
```

---

## 🔌 API Overview (typical)
> Adjust paths to match your controllers.

**Customers**
| Method | Path                  | Description            |
|-------:|-----------------------|------------------------|
|  GET   | `/api/customers`      | List all customers     |
|  GET   | `/api/customers/{id}` | Get one customer       |
|  POST  | `/api/customers`      | Create customer        |
|  PUT   | `/api/customers/{id}` | Update customer        |
| DELETE | `/api/customers/{id}` | Delete customer        |

**Accounts**
| Method | Path                         | Description                          |
|-------:|------------------------------|--------------------------------------|
|  GET   | `/api/accounts`              | List all accounts                    |
|  GET   | `/api/accounts/{id}`         | Get one account                      |
|  POST  | `/api/accounts`              | Create account (checking/savings)    |
|  PUT   | `/api/accounts/{id}`         | Update account                       |
| DELETE | `/api/accounts/{id}`         | Delete account                       |
|  POST  | `/api/accounts/{id}/deposit` | Deposit into an account              |
|  POST  | `/api/accounts/{id}/withdraw`| Withdraw from an account             |
|  POST  | `/api/accounts/transfer`     | Transfer between accounts            |

