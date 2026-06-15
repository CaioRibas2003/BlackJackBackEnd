# Blackjack Backend

Backend de um jogo de Blackjack desenvolvido com **Java** e **Spring Boot**.
A API é responsável por gerenciar jogadores, controlar sessões de jogo, processar apostas e aplicar as regras principais do Blackjack.

## Tecnologias utilizadas

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Lombok
* Banco de dados relacional
* API REST

## Funcionalidades

* Criação de jogador
* Controle de saldo do jogador
* Início de partida com aposta
* Distribuição de cartas
* Ação de Hit
* Ação de Stand
* Ação Dealer Next para executar o turno do dealer passo a passo
* Cálculo de pontuação do jogador e dealer
* Verificação de vitória, derrota, empate e bust
* Atualização do saldo após o resultado da rodada
* Retorno do estado completo do jogo para o frontend

## Principais endpoints

### Criar jogador

```http
POST /players
```

Exemplo de request:

```json
{
  "name": "Caio",
  "amount": 1000
}
```

Exemplo de response:

```json
{
  "id": 1,
  "name": "Caio",
  "amount": 1000
}
```

---

### Iniciar partida

```http
POST /blackjack/start
```

Exemplo de request:

```json
{
  "playerId": 1,
  "bet": 100
}
```

---

### Pedir carta

```http
POST /blackjack/hit
```

Exemplo de request:

```json
{
  "playerId": 1
}
```

---

### Parar jogada do jogador

```http
POST /blackjack/stand
```

Exemplo de request:

```json
{
  "playerId": 1
}
```

---

### Próxima ação do dealer

```http
POST /blackjack/dealer/next
```

Exemplo de request:

```json
{
  "playerId": 1
}
```

## Exemplo de resposta do jogo

```json
{
  "bet": 100,
  "playerCards": [
    {
      "suit": "CLUBS",
      "value": "SEVEN"
    },
    {
      "suit": "HEARTS",
      "value": "FOUR"
    }
  ],
  "dealerCards": [
    {
      "suit": "HEARTS",
      "value": "TWO"
    }
  ],
  "playerScore": 11,
  "dealerScore": 2,
  "status": "IN_PROGRESS",
  "playerAmount": 900,
  "message": "Your turn: Hit or Stand?"
}
```

## Regras principais

* O jogador inicia a rodada escolhendo uma aposta.
* O backend distribui cartas para jogador e dealer.
* O jogador pode pedir carta com Hit.
* O jogador pode parar com Stand.
* Após o Stand, o dealer joga através do endpoint Dealer Next.
* O dealer compra cartas enquanto sua pontuação for menor que 17.
* A rodada termina quando há vitória, derrota, empate ou bust.
* O saldo do jogador é atualizado conforme o resultado.

## Como rodar o projeto

### 1. Clonar o repositório

```bash
git clone <url-do-repositorio>
```

### 2. Entrar na pasta do projeto

```bash
cd <nome-do-projeto>
```

### 3. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

Ou, no Windows:

```bash
mvnw.cmd spring-boot:run
```

A API ficará disponível em:

```txt
http://localhost:8080
```

## Observações

Este backend foi desenvolvido para ser consumido por um frontend em React.
O objetivo principal do projeto é praticar arquitetura REST, regras de negócio, integração frontend/backend e desenvolvimento full stack.
