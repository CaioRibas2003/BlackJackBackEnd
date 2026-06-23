# Banco de dados PostgreSQL

Este projeto usa PostgreSQL. O H2 foi removido.

## Subir o banco com Docker

```bash
docker compose up -d
```

Dados de conexao padrao:

```text
Host: localhost
Porta: 5433
Banco: jogoseducativos
Usuario: postgres
Senha: postgres
```

## Rodar a aplicacao

```bash
./mvnw spring-boot:run
```

No Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

## Criar o banco manualmente

Se preferir usar um PostgreSQL instalado na maquina, crie o usuario e o banco:

```sql
CREATE DATABASE jogoseducativos OWNER postgres;
GRANT ALL PRIVILEGES ON DATABASE jogoseducativos TO postgres;
```

Tambem deixei o arquivo `database.sql` na raiz do projeto com esses comandos e a criacao da tabela `players`.

## Configurar outro banco, usuario ou senha

A aplicacao tambem aceita variaveis de ambiente:

```bash
DB_URL=jdbc:postgresql://localhost:5433/jogoseducativos
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

O Hibernate esta configurado com `spring.jpa.hibernate.ddl-auto=update`, entao as tabelas sao criadas/atualizadas automaticamente a partir das entidades JPA.
