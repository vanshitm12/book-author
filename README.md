# Book Author Manager

Spring Boot web application for managing Books and Authors using JSP, JPA, H2, and Bootstrap.

## Tech Stack
- Java 17
- Spring Boot 3.3.5
- Spring MVC + JSP + JSTL
- Spring Data JPA (Hibernate)
- H2 in-memory database
- JUnit 5 + Mockito

## Features
- `Author` and `Book` entities with one-to-many / many-to-one relationship.
- CRUD-style create/list/update flows for both books and authors.
- Book list uses custom join query (`SELECT b FROM Book b JOIN b.author a`).
- Startup data seeding with 10 authors and 10 books.
- Friendly error handling for validation and data integrity exceptions.
- Bootstrap-styled JSP views.

## Run
```bash
mvn spring-boot:run
```

Open:
- `http://localhost:8080/books/list`
- `http://localhost:8080/authors/list`
- H2 Console: `http://localhost:8080/h2-console`

H2 settings:
- JDBC URL: `jdbc:h2:mem:bookauthordb`
- User: `sa`
- Password: *(empty)*

## Test
```bash
mvn test
```

## Troubleshooting
- H2 console login fails with database not found:
	- Use JDBC URL: `jdbc:h2:mem:bookauthordb`
	- Do not use the default `jdbc:h2:~/test`
	- Ensure the app is running before opening H2 console
- JSP pages fail with JSTL class errors:
	- Ensure JSTL API and implementation dependencies are present in `pom.xml`
	- This project already includes the required JSTL dependencies

## Main Endpoints
### Books
- `GET /books/add`
- `POST /books/add`
- `GET /books/list`
- `GET /books/edit/{id}`
- `POST /books/update/{id}`

### Authors
- `GET /authors/add`
- `POST /authors/add`
- `GET /authors/list`
- `GET /authors/edit/{id}`
- `POST /authors/update/{id}`
# book-author
