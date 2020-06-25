# spring-boot-crud
Projeto de CRUD com Spring Boot

# Features
CRUD de Pessoa

[Postman collections](https://github.com/robertocandido/spring-boot-crud/blob/master/spring-boot-crud.postman_collection.json)

# Execução
### Iniciar o banco de dados
```bash
sudo docker-compose up
```
### Compilar
```bash
mvn clean install
```
### Executar a aplicação
```bash
mvn spring-boot:run
```

## Stack
* Java 11
* Spring Boot
* Spring Data JPA
* Maven;
* Postgres;
* JUnit;

# Requisitos
* [Java 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html);
* [docker-compose](https://docs.docker.com/compose/install/#install-compose);
* [Lombok](https://projectlombok.org/)


## Changelog
- 0.0.1: Configuração da aplicação;
- 0.0.2: Implementação do fluso salvar pessoa #13;
- 0.0.3: Formatação do código com padrão google #17;
- 0.0.4: Criação de endpoint para salvar pessoa #14;
- 0.0.5: Configuração do hystrix #15;
- 0.0.6: Estrutura de validação #20;
- 0.0.7: Configuração Docker #6;
- 0.0.8: Funcionalidade atualizar pessoa #31;
- 0.0.9: Endpoint para busca de pessoa por id #30;
- 0.0.10: Endpoint para exclusão lógica da entidade Person #33;
- 0.0.11: Fix busca por filtro, mão funcionada corretamente o filtro por nome e endereço;
