# Database Connection Spring Boot Application

## Overview
This is a Spring Boot application that demonstrates database connectivity with MySQL using JPA/Hibernate and JDBC. The application manages Employee, Department, and Project entities with their relationships and provides REST APIs for CRUD operations.

## Project Structure
```
databaseconnection/
├── src/
│   ├── main/
│   │   ├── java/com/example/databaseconnection/
│   │   │   ├── controller/
│   │   │   │   ├── DepartmentController.java
│   │   │   │   └── EmployeeController.java
│   │   │   ├── entity/
│   │   │   │   ├── Department.java
│   │   │   │   ├── Employee.java
│   │   │   │   ├── EmployeeProject.java
│   │   │   │   ├── EmployeeProjectId.java
│   │   │   │   └── Project.java
│   │   │   ├── repository/
│   │   │   │   ├── jdbc/
│   │   │   │   │   └── EmployeeJdbcRepository.java
│   │   │   │   ├── DepartmentRepository.java
│   │   │   │   ├── EmployeeProjectRepository.java
│   │   │   │   ├── EmployeeRepository.java
│   │   │   │   └── ProjectRepository.java
│   │   │   ├── service/
│   │   │   │   ├── DepartmentService.java
│   │   │   │   └── EmployeeService.java
│   │   │   └── DatabaseconnectionApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── target/
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## Technologies Used
- **Spring Boot 4.0.2** - Main framework
- **Spring Data JPA** - For ORM and database operations
- **Hibernate 7.2.1** - JPA implementation
- **MySQL 8.0** - Database
- **Maven** - Build tool
- **Docker** - Containerization
- **Java 25** - Programming language

## Database Schema

### Tables
1. **department**
   - id (Primary Key)
   - name
   - location

2. **employee**
   - id (Primary Key)
   - name
   - email
   - salary
   - department_id (Foreign Key)

3. **project**
   - id (Primary Key)
   - name
   - description

4. **employee_project** (Junction Table)
   - employee_id (Foreign Key)
   - project_id (Foreign Key)

### Relationships
- **Department ↔ Employee**: One-to-Many (One department can have multiple employees)
- **Employee ↔ Project**: Many-to-Many (Employees can work on multiple projects, projects can have multiple employees)

## Configuration

### Database Configuration (application.properties)
```properties
spring.application.name=databaseconnection
spring.datasource.url=jdbc:mysql://mysql-db:3306/springjavatodb
spring.datasource.username=root
spring.datasource.password=Pradeepkumar21@
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
server.port=9090
```

## API Endpoints

### Employee Operations
- **Create Employee**: `GET http://localhost:9090/employee/create/{department_id}`
- **Get All Employees**: `GET http://localhost:9090/employees`
- **Assign Employee to Project**: `GET http://localhost:9090/employee/{employee_id}/project/{project_id}`

### JPQL Queries
- **Employees with salary greater than X**: `GET http://localhost:9090/employees/salary/{amount}`
- **Employees by Department name**: `GET http://localhost:9090/employees/department/{department_name}`
- **Employees above average salary**: `GET http://localhost:9090/employees/above-average`

### JDBC Operations
- **Employee names using JdbcTemplate**: `GET http://localhost:9090/employees/jdbc`

## Key Features

### 1. JPA/Hibernate Integration
- Entity relationships with proper annotations
- Automatic table creation/updates
- JPQL custom queries
- Transaction management

### 2. JDBC Template Usage
- Direct SQL execution
- Custom repository implementation
- Raw database access

### 3. RESTful APIs
- Clean REST endpoints
- JSON response format
- Error handling

### 4. Database Relationships
- **@OneToMany**: Department → Employees
- **@ManyToMany**: Employee ↔ Projects
- **@JoinTable**: Custom junction table configuration

## How to Run

### Option 1: Using Docker (Recommended)
```bash
# Build the application
mvn clean package -DskipTests

# Start with Docker Compose
docker-compose up -d
```

### Option 2: Local MySQL Setup
1. Install MySQL locally
2. Create database: `springjavatodb`
3. Update `application.properties` with local MySQL URL:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/springjavatodb
   ```
4. Run: `mvn spring-boot:run`

## Database Setup Commands
```sql
-- Check tables
SELECT * FROM department;
SELECT * FROM employee;
SELECT * FROM project;
SELECT * FROM employee_project;

-- Sample data insertion (if needed)
INSERT INTO department (name, location) VALUES ('IT', 'Bangalore');
INSERT INTO department (name, location) VALUES ('HR', 'Mumbai');
```

## Testing the Application

### 1. Create Department (if not exists)
First ensure departments exist in the database.

### 2. Test Employee Creation
```
GET http://localhost:9090/employee/create/1
```

### 3. View All Employees
```
GET http://localhost:9090/employees
```

### 4. Test JPQL Queries
```
GET http://localhost:9090/employees/salary/50000
GET http://localhost:9090/employees/department/IT
GET http://localhost:9090/employees/above-average
```

### 5. Test JDBC Operations
```
GET http://localhost:9090/employees/jdbc
```

## Architecture Patterns

### 1. Layered Architecture
- **Controller Layer**: REST endpoints
- **Service Layer**: Business logic
- **Repository Layer**: Data access
- **Entity Layer**: Data models

### 2. Repository Pattern
- JPA repositories for standard operations
- Custom JDBC repository for complex queries
- Interface-based design

### 3. Dependency Injection
- Spring's IoC container
- Constructor injection
- Service layer abstraction

## Error Handling
- Database connection failures
- Constraint violations
- Invalid data handling
- Transaction rollback

## Performance Considerations
- Connection pooling (HikariCP)
- Lazy loading for relationships
- Query optimization
- Proper indexing

## Security Notes
- Database credentials in properties file (should use environment variables in production)
- No authentication/authorization implemented (add Spring Security for production)

## Future Enhancements
1. Add Spring Security for authentication
2. Implement pagination for large datasets
3. Add validation annotations
4. Create unit and integration tests
5. Add logging framework
6. Implement caching
7. Add API documentation with Swagger

## Troubleshooting

### Common Issues
1. **Database Connection Failed**: Ensure MySQL is running and credentials are correct
2. **Port Already in Use**: Change server.port in application.properties
3. **Table Not Found**: Check if ddl-auto=update is working properly

### Docker Issues
- Ensure Docker is running
- Check if ports 3306 and 9090 are available
- Verify MySQL container health status

## Contact
For any questions or issues, please refer to the application logs or check the database connectivity.