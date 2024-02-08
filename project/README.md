## 360 Project | csd4300 

### Introduction

This is a project for the course "Databases" of the Department of Computer Science at the University of Crete.
The project is a web application that simulates a car rental system. The application is built using Java, Maven, Tomcat, and Postgres. The application provides a RESTful API without any frontend.

### Presqsuisites

1. Docker and Docker Compose  
2. Java 8 or higher  
3. Maven  
4. Tomcat 

### How to run

1. Start the database server by running `docker-compose up` in the root directory of the project.
2. Before deploying the war file to Tomcat, you need to build and run the main.java file in the `project_maven/src/main/java/cmd/Main.java` to init the tables and populate the database with some data. 
3. You can either get the war file (project/WAR-FILE-360.war) or build it yourself by running `mvn clean install` inside the `project_maven` directory. 
4. Deploy the war file to Tomcat.  
5. Open the browser and go to `http://localhost:8080/` to access the application.  


### How to use

The functionalities of the application are included in the `project_maven/README.md` file.   
Or you can test the API by using Postman or any other API testing tool.  
The API endpoints are included in the `project_maven/README.md` file.  