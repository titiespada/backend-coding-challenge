README
====

# Specifications

*   Maven
*   Java 8
*   Spring Boot 1.5.9
*	MySQL
*	Liquibase
*	Hibernate
*	Jackson
*	ExchangeRate API (to get the rate for converting EUR to GBP)

# Init

The application runs over MySQL database. These are configurations defined to connect to the database:

> **address:** localhost:3306   
> **schema:** backendcodingchallenge   
> **username:** root   
> **password:** password

If you have different configurations you can change the following properties file: liquibase.properties, application.properies.

Make sure you have MySQL running with the schema **backendcodingchallenge** previously added. Then run the following command over the solution directory:
> mvn liquibase:update

# Run

To get the application up and running, you will need to:

1.  Compile the client (exec the command over the project root directory): `gulp dev`
2.  Run the server (exec the command over the solution directory): `mvn clean spring-boot:run`
3.	Run the tests (exec the command over the solution directory): `mvn clean test`
