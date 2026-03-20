# Project Manager Application

O **Project Manager** é uma aplicação full-stack composta por um **backend em Spring Boot** e **banco de dados PostgreSQL** orquestrados com **Docker Compose**.

---

# TO DO
- Frontend com Angular 20+
- Testes unitários
- Implemetação de exclusão lógica nas entidades
- Melhor configuração de Security/JWT
    - Implementação de roles para acesso a URL's específicos (este projeto tem somente implementação de JWT simples)
    - Externalização de variáveis envolvendo JWT
    - Uso de encode na senha do usuário cadastrada no banco de dados


##  Requisitos
- Docker instalado  
- Java 17+ (caso queira rodar o backend localmente)  
- Maven 3.8+ (para build manual do backend)

---

##  Configuração do Ambiente

1. Copie o arquivo de exemplo `.env`:

```bash
cp .env.example .env

```

2. Configure o arquivo .env e atualize suas credenciais:
```
# Configuração do banco de dados
POSTGRES_DB=eventdb
POSTGRES_USER=seu_usuario_aqui
POSTGRES_PASSWORD=sua_senha_aqui

# Configuração do datasource do Spring
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/eventdb
SPRING_DATASOURCE_USERNAME=${POSTGRES_USER}
SPRING_DATASOURCE_PASSWORD=${POSTGRES_PASSWORD}
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
```

ATENÇÃO: Substitua **seu_usuario_aqui** e **sua_senha_aqui** pelos valores desejados.


## Docker Compose

O projeto inclui um arquivo docker-compose.yml com dois serviços principais:

- **db** → Banco de dados PostgreSQL

- **backend** → API desenvolvida em Spring Boot


## Executando a Aplicação

1) Suba todos os serviços com o Docker Compose:

```bash
docker-compose up -d --build
```

2) Verifique se os containers estão rodando:
```bash
docker ps
```



## Acessos

- Backend (Spring Boot API) → http://localhost:8080

- Swagger UI → http://localhost:8080/swagger-ui/index.html
(também tem um arquivo de Swagger salvo em docs/swagger/ para facilitar a visualização)

O backend aplica automaticamente as migrações do Flyway no banco de dados ao iniciar.


## Parar a Aplicação
docker-compose down


### ATENÇÃO: Se alterar o .env, é necessário reconstruir os containers:

```bash
docker-compose up -d --build
```
