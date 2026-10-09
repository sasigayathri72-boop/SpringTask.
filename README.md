TASKFLOW - SPRING BOOT REST API

Requirements:
- JDK 17 or later
- Maven installed and available in PATH
- Internet connection for the first build (to download dependencies)

Build:
    mvn clean package

Run:
    mvn spring-boot:run

Or, after a successful build:
    java -jar target/taskflow-0.0.1-SNAPSHOT.jar

API endpoints:
GET     http://localhost:8080/api/tasks
GET     http://localhost:8080/api/tasks/1
GET     http://localhost:8080/api/tasks?priority=HIGH
POST    http://localhost:8080/api/tasks
PUT     http://localhost:8080/api/tasks/1
PATCH   http://localhost:8080/api/tasks/1/toggle
DELETE  http://localhost:8080/api/tasks/1

Example POST JSON:
{
  "title": "Complete Java assignment",
  "description": "Finish the Spring Boot task manager",
  "priority": "HIGH"
}

Allowed priorities: LOW, MEDIUM, HIGH
Allowed statuses: PENDING, COMPLETED

H2 console:
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:taskflowdb
User Name: sa
Password: (leave blank)

Note: H2 is an in-memory database. Its data is cleared when the app stops.