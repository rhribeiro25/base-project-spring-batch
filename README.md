# Base Project Spring Batch

## Project overview

This project's a Restful API that manages logs

## Requirements

#### Roteiro do desafio técnico

Seu objetivo é criar uma aplicação em Java para fazer o upload de um arquivo de logs populando o banco de dados.
Para isso, será necessário uma interface para o upload do arquivo de logs e uma para inserir/editar/listar/consultar/pesquisar (CRUD).
Implementar o back-end com (Spring ou JavaEE/MicroProfile usando java 8+) e front-end Angular 6+.

#### Detalhes do back-end

- Definir o modelo de dados no PostgreSQL;
- Definir serviços para a inserção em batch (usando o arquivo de logs fornecido,
usando JPA);
- Definir serviços para a inserção de logs manuais (CRUD), (não utilizar spring-data-jpa);
- Implementar filtros ou pesquisas de logs;
- Testes Unitários;
- (BÔNUS) Testes de Integração;

#### Detalhes do front-end

- Tela para inserção de logs manuais (CRUD);
- Tela para inserção de logs usando o arquivo modelo;
- (BÔNUS) Uma tela (dashboard) para exibir os logs feitos por um determinado IP, por hora, user-agent (agregação).

#### Detalhes do arquivo de log

Data, IP, Request, Status, User Agent (delimitado por aspas duplas);
O delimitador do arquivo de log é o caracter pipe |;
Formato de data: yyyy-MM-dd HH:mm:ss.SSS;

#### O que avaliamos?

- Princípios de programação
- Arquitetura de Software
- Manutenabilidade
- Performance
- Testes

Obs: Ficaríamos impressionados se seu projeto levasse em conta uma arquitetura de sistema distribuído e de alta disponibilidade

## Tech Stack

- Spring Boot / MVC / Validation / Security / Batch
- PostgreSQL 11 / PgAdmin 4
- Docker / Docker Compose
- JUnit / Mockito
- Java 11
- Postman
- Gradle
- Lombok

## Programming principles

- Behavior Driven Development
- Clean Code by Lombok
- Spring Validator with Custon constraints
- Object Orientation Programming
- Exception handling
- Logs with Log4j2
- SOLID

## Software architecture

- Representational State Transfer (REST)
- Clean Architecture
- Data Transfer Objects pattern
- Data mapper pattern
- Builder pattern

## Starting the project by Docker

- Should have Docker and Docker-compose installed
- Create the network manually using: `$ docker network create base-project-spring-batch-net`
- You can boot the whole stack by using `$ docker-compose up -d` and to access logs after with `$ docker-compose logs -f api`

## Access PGADMIN by Docker

- To access PGADMIN locally open your browser and type: `http://localhost:16543`
- login with default credentials:
  ```
  email: rhribeiro_25@hotmail.com
  password: postgres
  ```

## Database configuration in PGADMIN

- then config the connection to your local database:
  ```
  Host name/ address : pgsql
  port: 5432
  Maintenance database: bank-project-db
  Username: postgres
  Password: postgres
  ```

## Starting the project by IntelliJ

- Should have PostgreSQL installed and configured
- Should have IntelliJ Community, JDK 11
- Should have Lombook and Docker plugins installed in IntelliJ
- You need to update gradle dependencies
- To execute clean and build the project
- Run BaseProjectSpringBatchApplication.java file

## Documentation and Credentials

- You can use the Postman collection to test the API:
```
  Path: \src\main\resources\postmanCollection\base-project-spring-batch.postman_collection.json
```
- Authentications to API:
```
  Reading
  login: user
  password: smartLog2020

  Writing
  login: admin
  password: smartLog2020
```
