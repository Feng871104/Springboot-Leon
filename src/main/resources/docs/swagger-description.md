### Welcome

---

This document provides a detailed overview of the Springboot-Leon application, including its architecture, security, and API endpoints.

### Project Overview

The Springboot-Leon application is a RESTful API built with Spring Boot. It provides a secure and scalable solution for managing users and their authentication.

### Technologies Used

*   **Spring Boot**: The core framework for the application.
*   **Spring Security**: Provides authentication and authorization features.
*   **Spring Data JPA**: Used for data access and persistence.
*   **PostgreSQL**: The database used by the application.
*   **Testcontainers**: Used for integration testing with a real database.
*   **Swagger**: Provides API documentation and a UI to interact with the API.

### Security

The application uses JSON Web Tokens (JWT) for authentication and authorization. The following security measures are in place:

*   **JWT Authentication**: All API endpoints, except for the authentication endpoint, are secured with JWT authentication.
*   **Role-Based Access Control**: The application uses role-based access control to restrict access to certain endpoints. The following roles are available:
    *   **USER**: Can access general endpoints.
    *   **ADMIN**: Can access all endpoints, including those that are restricted to administrators.
*   **Password Encryption**: All user passwords are encrypted using the Argon2 password hashing algorithm.

### API Endpoints

For a complete list of endpoints and their documentation, please refer to the Swagger UI.
