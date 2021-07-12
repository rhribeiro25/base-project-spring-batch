# Base Project Spring Batch

Roteiro do desafio técnico
--------
Seu objetivo é criar uma aplicação em Java para fazer o upload de um arquivo de logs populando o banco de dados.
Para isso, será necessário uma interface para o upload do arquivo de logs e uma para inserir/editar/listar/consultar/pesquisar (CRUD).
Implementar o back-end com (Spring ou JavaEE/MicroProfile usando java 8+) e front-end Angular 6+.

Detalhes do back-end
-----------
- Definir o modelo de dados no PostgreSQL;
- Definir serviços para a inserção em batch (usando o arquivo de logs fornecido,
usando JPA);
- Definir serviços para a inserção de logs manuais (CRUD), (não utilizar spring-data-jpa);
- Implementar filtros ou pesquisas de logs;
- Testes Unitários;
- (BÔNUS) Testes de Integração;

Detalhes do front-end
-----------
- Tela para inserção de logs manuais (CRUD);
- Tela para inserção de logs usando o arquivo modelo;
- (BÔNUS) Uma tela (dashboard) para exibir os logs feitos por um determinado IP, por hora, user-agent (agregação).

Detalhes do arquivo de log
-----------
Data, IP, Request, Status, User Agent (delimitado por aspas duplas);
O delimitador do arquivo de log é o caracter pipe |;
Formato de data: yyyy-MM-dd HH:mm:ss.SSS;

O que avaliamos?
--------
- Princípios de programação
- Arquitetura de Software
- Manutenabilidade
- Performance
- Testes

Obs: Ficaríamos impressionados se seu projeto levasse em conta uma arquitetura de sistema distribuído e de alta disponibilidade

Detalhes do desenvolvimento
--------
Será disponibilizado um postmanCollection para testes da API em __"\src\main\resources\postmanCollection"__.
Utilizar as autenticações abaixo diretamente no postman:
##### Leitura
- __User:__ user
- __password:__ smartLog2020
##### Gravação
- __User:__ admin
- __password:__ smartLog2020

