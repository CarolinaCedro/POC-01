# API INSIDER POC 01 2022


---


---

- [Getting started](#getting-started)
    - [Introdução](#introdução)
    - [Build configuration](#build-configuration)
    - [Como baixar ? ](#como-baixar-e-rodar-este-projeto)

## Introdução

Gostaria de apresentar a API Poc 01, que permite gerenciar os clientes e endereços de uma empresa. Com ela, é possível realizar as seguintes operações:

* Criar, ler, atualizar e excluir clientes;
* Criar, ler, atualizar e excluir endereços;
* Associar um endereço a um cliente ou desassociá-los.

Foi utilizado uma arquitetura REST e os métodos HTTP padrão 
(GET, POST, PUT,PATH e DELETE) para disponibilizar essas funcionalidades. Além disso, implementamos autenticação através de tokens de acesso para garantir a segurança dos dados.

O modelo de dados dessa API inclui dois tipos de recursos: clientes e endereços. Cada cliente possui um ID único, email, cpf/cnpj, tipo (PJ ou PF), endereço, telefone, e pode ter 1 ou até 5  endereços associados a ele. Já os endereços possuem um ID único, um nome de rua, numero, bairro, cidade, cep, estado.

API Poc possui um relacionamento de entidade entre os recursos de clientes (customers) e endereços (addresses). Segundo as regras de negócio, cada cliente pode ter no máximo 5 endereços cadastrados e deve sempre possuir um endereço principal. É possível realizar operações de CRUD (criar, ler, atualizar e excluir) tanto para clientes quanto para endereços, além de tornar um endereço um endereço principal.

Para garantir a qualidade dos dados, foi incluído validações e máscaras em alguns campos, como o CEP e o número de telefone. Além disso, utilizamos o padrão REST e o framework Spring Boot para desenvolver a API de forma eficiente e seguindo os princípios SOLID.

Para facilitar o uso da API, também foi incluído paginação e filtros nas consultas através do método GET. Também versionamos o código da aplicação no GitHub e realizamos testes de integração e unitários para garantir a qualidade do código. Por fim, utilizamos um banco de dados em memória para armazenar os dados.

## Build configuration

Dependências

| Dependência               | Grupo                        | Artefato                                  | Versão | Escopo      | Opcional |
|---------------------------|-------------------------------|--------------------------------------------|--------|------------|----------|
| spring-boot-starter-data-jpa | org.springframework.boot    | spring-boot-starter-data-jpa               |        |            |          |
| spring-boot-starter-web     | org.springframework.boot    | spring-boot-starter-web                   |        |            |          |
| spring-boot-devtools        | org.springframework.boot    | spring-boot-devtools                      |        | runtime    | true     |
| h2                         | com.h2database              | h2                                        |        |            |          |
| modelmapper                 | org.modelmapper             | modelmapper                               | 3.1.0  |            |          |
| spring-boot-starter-validation | org.springframework.boot | spring-boot-starter-validation            |        |            |          |
| spring-boot-starter-test   | org.springframework.boot    | spring-boot-starter-test                  |        | test       |          |
| springfox-boot-starter     | io.springfox                | springfox-boot-starter                    | 3.0.0  |            |          |
| javax.servlet-api          | javax.servlet               | javax.servlet-api                         | 4.0.1  | provided   |          |
| validation-api             | javax.validation           | validation-api                            | 2.0.1.Final |          |          |
| gson                       | com.google.code.gson        | gson                                      | 2.10    |            |          |
| junit                      | junit                       | junit                                     | 4.5     | test       |          |
| mockito-junit-jupiter      | org.mockito                 | mockito-junit-jupiter                     |          | test       |          |
| spring-context-support     | org.springframework         | spring-context-support                    |          |            |          |
| spring-boot-starter-cache  | org.springframework.boot    | spring-boot-starter-cache                 |          |            |          |

## Como baixar e rodar este projeto

Para baixar e rodar o projeto Java, siga os passos abaixo:

1. Faça o clone do projeto no GitHub:

``git clone https://github.com/CarolinaCedro/POC01.git``

2. Entre na pasta do projeto:

``cd POC01``

3. Execute o comando abaixo para baixar as dependências do Maven:

``mvn clean install``

4. Execute o projeto com o comando abaixo:

``mvn spring-boot:run``

5. Acesse a aplicação em seu navegador pelo endereço: `http://localhost:8080`

