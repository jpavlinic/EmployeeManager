🛠️ Employee Management System

A backend system built with **Spring Boot** that enables secure employee management with user authentication, role-based access control, and activity logging.

🔥 About the Project

The **Employee Management System** is a Spring Boot-based backend application designed to provide a secure API for managing employee records. The system includes authentication using **JWT**, role-based access with **Spring Security**, and **Spring Data JPA** for database operations. Admins have the ability to create, update, delete, and retrieve employee records, while regular users have limited access to their own records. The system also integrates **Spring AOP** for logging activities like employee modifications and authentication events. 

🎉 Features

🚀 **User Authentication & JWT**: Secure authentication using JSON Web Tokens (JWT).

🛡️ **Role-Based Access Control (RBAC)**: Admins can manage all employees, while regular users can view their own records.

🗂️ **Employee Management**: Full CRUD operations for employees with database persistence.

🛠️ **Spring JPA & Hibernate**: ORM-based database handling for employee records.


🛠️ Tech Stack

**Frameworks & Libraries:**
- **Spring Boot** (Backend framework)
- **Spring Security** (Authentication & Authorization)
- **Spring Data JPA** (Database operations)
- **Spring AOP** (Logging and monitoring)
- **Bucket4j** (Rate limiting for API protection)
- **Lombok** (Boilerplate code reduction)

**Database:**
- **MySQL** (Relational database for persistence)

**Other Tools:**
- **Maven** (Build tool)
- **Postman** (For API testing)

📡 Core Functionalities

### 1️⃣ User Authentication & Security
- **JWT Authentication**: Users receive a secure token upon login.
- **Spring Security**: Protects API endpoints and enforces access control.
- **User Roles**:
  - **Admin**: Manage all employees.
  - **User**: View their own details.

### 2️⃣ Employee Management (REST API)
- **Admin Features**:
  - Add, update, delete employees.
  - View all employees.
- **User Features**:
  - View only their own details.



🚀 Future Enhancements

### Logging (Spring AOP)
- Logs activities such as employee creation, updates, and deletions.
- Logs authentication attempts for security auditing.

###  Global Exception Handling
- Centralized error handling using `@ControllerAdvice`.
- Custom exceptions like `ResourceNotFoundException`, `UnauthorizedException`.

### 5 Rate Limiting (Bucket4j)
- Prevents API abuse by limiting requests.
- Configured for `10 requests per minute per user`.



