# CRUD - SPRINGBOOT REST API + ANDROID UI

This repository contains a basic CRUD mobile application using ANDROID and SPRINGBOOT

## TECHNOLOGY STACK
COMPONENT                           | TECHNOLOGY              	| FOR MORE INFORMATION
---                                 | ---                     	|---
Languages & Frameworks              |`spring boot`		| https://spring.io/projects/spring-boot
Databases                           |`postgreSQL`		| https://www.postgresql.org/
Documentation as a Service & Tools  |`swagger`			| https://swagger.io/
Java Tools                          |`lombok` `maven`		| https://projectlombok.org/ & https://maven.apache.org/
API Tools		            |`retrofit2` `picasso` 	| https://square.github.io/retrofit/ & https://square.github.io/picasso/
Platform as a Service               |`heroku` `heroku postgres` | https://www.heroku.com/

## PREREQUISITES
### COMPONENTS
- [JAVA](https://nakebenihime.github.io/windows/java/guide-openjdk16.html)
- [MAVEN](https://nakebenihime.github.io/windows/java/guide-apache-maven3.html)
- [HEROKU](https://devcenter.heroku.com/articles/heroku-cli#download-and-install)
- [DOCKER](https://docs.docker.com/get-docker/)

## EXPLORE REST APIs
Explore the resources using swagger-ui or any other REST client:

METHOD | PATH                    | DESCRIPTION                    |
-------|-------------------------|--------------------------------|
GET    | /api/v1/mangas          | retrieve all the mangas        |
GET    | /api/v1/mangas/{id}     | retrieve one manga by its ID   |
POST   | /api/v1/mangas          | create or update a new manga   |
DELETE | /api/v1/mangas/{id}     | remove a manga by its ID       |



