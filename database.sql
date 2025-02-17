CREATE DATABASE IF NOT EXISTS employee_management;
USE employee_management;

-- Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Roles Table
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

-- User Roles Table (Many-to-Many)
CREATE TABLE user_roles (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Employees Table
CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    department VARCHAR(50),
    salary DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Activity Log Table
CREATE TABLE activity_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    action VARCHAR(255) NOT NULL,
    user_id INT,
    employee_id INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE SET NULL
);

-- Insert Sample Roles
INSERT INTO roles (role_name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

-- Insert Sample Users with Hashed Passwords (e.g., bcrypt)
INSERT INTO users (username, password_hash, email) VALUES
('admin_user', '$2a$10$7aW6K..examplehashforadmin', 'admin@example.com'),
('regular_user', '$2a$10$7aW6K..examplehashforuser', 'user@example.com');

-- Assign Roles
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- admin_user -> ROLE_ADMIN
(2, 2); -- regular_user -> ROLE_USER

-- Insert Sample Employees
INSERT INTO employees (first_name, last_name, email, phone_number, department, salary) VALUES
('John', 'Doe', 'john.doe@example.com', '1234567890', 'IT', 75000.00),
('Jane', 'Smith', 'jane.smith@example.com', '9876543210', 'HR', 65000.00),
('Robert', 'Brown', 'robert.brown@example.com', '5556667777', 'Finance', 80000.00);

-- Insert Sample Activity Logs
INSERT INTO activity_log (action, user_id, employee_id) VALUES
('Created employee John Doe', 1, 1),
('Created employee Jane Smith', 1, 2),
('Created employee Robert Brown', 1, 3);

-- Verify Data
SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM user_roles;
SELECT * FROM employees;
SELECT * FROM activity_log;