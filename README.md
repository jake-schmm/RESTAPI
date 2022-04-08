# RESTAPI
A REST API built using Spring Boot and MySQL.

To run:
Download files, and in MySQL, run the following queries in order to create database and table:
<br />
CREATE DATABASE IF NOT EXISTS test1;

USE test1;

CREATE TABLE recipe (
    id int,
    recipe_name varchar(255),
    ingredients varchar(6500),
    instructions varchar(6500)
);

Then in Eclipse, right click project -> Run as ... Maven. 
For goal, enter "spring-boot:run", and in Environment put spring.datasource.username set to "root" and spring.datasource.password set to your MySQL root password.
Save this Run configuration.

Right click project -> Build Path -> Configure build path. Go to Java Build Path -> source, and add the folders:
- recipe/src/main/java/com/example/recipe/model
- recipe/src/main/java/com/example/recipe/repository

Now run the project using the saved Run as ... configuration.
