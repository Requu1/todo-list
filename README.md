# Todo List Application

A simple Todo List web application built with **Spring Boot** and **Java 23**. The application allows users to create, display, mark as completed, and delete tasks. It uses **MySQL** as the main database, running inside a Docker container, and provides both a browser-based interface and REST API endpoints.

## Features

- Add new tasks
- Display all saved tasks
- Mark tasks as completed or in progress
- Delete tasks
- Prevent duplicate task names
- Validate task names
- Web interface rendered with Thymeleaf
- REST API for task management
- MySQL database support through Spring Data JPA
- Unit and repository tests using JUnit, Mockito, and H2

## Technologies Used

- Java 23
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Thymeleaf
- Jakarta Validation
- MySQL
- Docker
- Gradle
- JUnit 5
- Mockito
- H2 database for tests
- Bootstrap 5

## Project Structure

```text
src/
├── main/
│   ├── java/com/github/Requu1/todolist/
│   │   ├── controller/      # Web and REST controllers
│   │   ├── exception/       # Custom exceptions and global exception handling
│   │   ├── model/           # Task entity
│   │   ├── repository/      # Spring Data JPA repository
│   │   ├── service/         # Business logic
│   │   └── TodolistApplication.java
│   └── resources/
│       ├── templates/       # Thymeleaf HTML views
│       └── application.properties
└── test/                    # Repository and service tests
```

## Requirements

Before running the project, make sure you have installed:

- Java 23
- Docker
- Gradle, or use the included Gradle Wrapper

## Database Setup

The application expects a MySQL database available at:

```text
localhost:3307
```

The default database name is:

```text
todolist_db
```

You can start a MySQL container with:

```bash
docker run --name todolist-mysql \
  -e MYSQL_ROOT_PASSWORD=your_password \
  -e MYSQL_DATABASE=todolist_db \
  -p 3307:3306 \
  -d mysql:latest
```

The application reads the database password from the `DB_PASSWORD` environment variable.

On Linux/macOS:

```bash
export DB_PASSWORD=your_password
```

On Windows PowerShell:

```powershell
$env:DB_PASSWORD="your_password"
```

## Running the Application

From the project root directory, run:

```bash
./gradlew bootRun
```

On Windows:

```powershell
.\gradlew.bat bootRun
```

After the application starts, open the browser at:

```text
http://localhost:8080
```

## REST API Endpoints

The application also exposes a REST API under `/api/tasks`.

| Method   | Endpoint                 | Description                   |
| -------- | ------------------------ | ----------------------------- |
| `GET`    | `/api/tasks`             | Get all tasks                 |
| `POST`   | `/api/tasks`             | Create a new task             |
| `PATCH`  | `/api/tasks/{id}/toggle` | Toggle task completion status |
| `DELETE` | `/api/tasks/{id}`        | Delete a task                 |

Example request for creating a task:

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Buy groceries"}'
```

## Running Tests

To run all tests, use:

```bash
./gradlew test
```

On Windows:

```powershell
.\gradlew.bat test
```

Tests use an in-memory H2 database, so a running MySQL container is not required for testing.

## Configuration

Main database configuration is located in:

```text
src/main/resources/application.properties
```

## Author

Created by Szymon Potępa / **Requu1**.
