# Courier
Project Requirement 

  Java 17
  Avaible port on 8080
  Docker
  Postgresql


Postman Collection is available in the project.

Notes: timestamp parameter should send in this format "2023-04-17T14:23:56.179" and can not be null.
For Postgres, the docker-compose.yml file must be run.

Project Design Patterns
  Strategy Pattern : by using comparator.
  Singleton Pattern : by using @Bean.
  Null Object Pattern: by using CollectionUtils
  Data Access Object : by using JpaRepository.
  Iterator Pattern: by using foreach and iterator.
  In general, this piece of code contains design patterns that are commonly used to solve problems with ordering objects, database operations, and loops.
  

Project Context Path : /migrosApi


I couldn't customize the project too much due to time constraints.
