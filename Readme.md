**Execution instructions**

Go to the root of the application where build.gradle is available

Run execute the below command "gradle bootRun"


**Architectural and Technologicals decisions**

This project is structured in 3 layers

Controller Layer
Contains the class that exposes the endpoints

Service Layer
Contains the class that contains the business logic

Repository Layer
Contains the classes that interact with the database. In this case, a H2 in memory database was used.


A RestControllerAdvice was used for centralized exception handling.


