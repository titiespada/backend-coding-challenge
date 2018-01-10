README
====
How to run the your solution...

# Specifications

*   Maven
*   Java 8
*   Spring Boot 1.5.9
*	MySQL
*	Liquibase
*	Hibernate
*	Jackson

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
2.  Run the server (exec the command over the solution directory): `mvn spring-boot:run`

IMPORTANT
====
To avoid unconcious bias, we aim to have your submission reviewed anonymously by one of our engineering team. Please try and avoid adding personal details to this document such as your name, or using pronouns that might indicate your gender.