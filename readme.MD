# Ford Challenge API — Inteligência Competitiva Automotiva

API REST desenvolvida em Java + Spring Boot para comparação inteligente de veículos utilizando IA generativa (Google Gemini) integrada à base oficial Ford.

---

### Integrantes
Mohamed Afif Smaili RM 554445

Mateus dos Santos da Silva RM 558436

Nicolas Moreno Cardoso RM 557132

Andre Giovane RM 556384

# Objetivo do Projeto

O sistema foi desenvolvido para permitir que vendedores e analistas comparem veículos concorrentes com modelos Ford utilizando inteligência artificial.

A aplicação:

- importa dados técnicos oficiais da Ford via Excel;
- realiza pré-processamento dos dados;
- consulta IA generativa para obter dados de concorrentes;
- gera comparativos estruturados em JSON;
- aplica autenticação, RBAC e auditoria de acesso.

---

# Arquitetura da Solução

```text
Cliente → Spring Boot API → Segurança RBAC
→ Base Ford (Excel/H2)
→ Integração Gemini AI
→ Comparativo JSON
→ Auditoria
```

---

# Tecnologias Utilizadas

| Tecnologia | Finalidade |
|---|---|
| Java 17 | Linguagem principal |
| Spring Boot | Framework backend |
| Spring Security | Segurança e RBAC |
| Spring Data JPA | Persistência |
| H2 Database | Banco em memória |
| Apache POI | Leitura do Excel |
| Gemini API | Inteligência Artificial |
| Swagger/OpenAPI | Documentação da API |
| Maven | Gerenciamento de dependências |

---

# Funcionalidades

- Importação automática da base Ford via Excel
- Comparação inteligente entre veículos
- Integração com IA generativa
- Controle de acesso por perfil (RBAC)
- Auditoria de consultas IA
- Validação e sanitização de entrada
- Tratamento global de exceções
- Swagger/OpenAPI integrado

---

# Segurança Implementada

## RBAC (Role-Based Access Control)

| Endpoint | Perfil Necessário |
|---|---|
| `/api/v1/veiculos/comparar` | VENDEDOR ou ADMIN |
| `/api/v1/veiculos/ford-specs` | ADMIN |

---

## Usuários Mockados

| Usuário | Senha | Perfil |
|---|---|---|
| joao.vendas | senha123 | VENDEDOR |
| admin.ti | admin123 | ADMIN |

---

## Proteções Aplicadas

- Sanitização contra XSS/SQL Injection
- Validação de payload
- Tratamento global de exceções
- Ocultação de stack trace
- Auditoria de uso da IA
- Controle de autenticação Basic Auth
- Configuração CORS

---

# Estrutura da API

## Base URL Local

```text
http://localhost:8080
```

---

# API Publicada (Deploy Externo)

A API também foi publicada em ambiente externo para testes e consumo remoto.

## Base URL Produção

```text
http://163.176.204.45:8080
```

---

# Endpoint — Consultar Base Ford

## GET `/api/v1/veiculos/ford-specs`

Retorna todas as especificações técnicas oficiais da Ford importadas do Excel.

## URL Produção

```text
http://163.176.204.45:8080/api/v1/veiculos/ford-specs
```

---

## Exemplo de Requisição

```bash
curl -u admin.ti:admin123 \
-X GET \
http://163.176.204.45:8080/api/v1/veiculos/ford-specs
```

---

## Exemplo de Resposta

```json
[
  {
    "id": 1,
    "categoria": "Engine & Transmission",
    "equipamento": "Motor",
    "versaoXlt": "2.0",
    "versaoLimited": "2.5",
    "versaoLimitedPlus": "3.0"
  }
]
```

---

# Endpoint — Comparar Veículos com IA

## POST `/api/v1/veiculos/comparar`

Recebe um modelo Ford e um veículo concorrente para gerar um comparativo técnico utilizando IA.

## URL Produção

```text
http://163.176.204.45:8080/api/v1/veiculos/comparar
```

---

## Exemplo de Payload

```json
{
  "versaoFord": "Ranger Limited",
  "concorrente": "Toyota Hilux"
}
```

---

## Exemplo de Requisição

```bash
curl -u joao.vendas:senha123 \
-X POST \
-H "Content-Type: application/json" \
-d '{
  "versaoFord":"Ranger Limited",
  "concorrente":"Toyota Hilux"
}' \
http://163.176.204.45:8080/api/v1/veiculos/comparar
```

---

## Exemplo de Resposta

```json
{
  "motor": {
    "ford": "3.0 V6",
    "concorrente": "2.8 Turbo Diesel"
  },
  "potencia": {
    "ford": "250cv",
    "concorrente": "204cv"
  }
}
```

---

# Regras de Validação

## Campos protegidos

| Campo | Regra |
|---|---|
| versaoFord | obrigatório, 2-50 caracteres |
| concorrente | obrigatório, 2-50 caracteres |
| Pattern | proteção contra payload malicioso |

---

# Tratamento de Erros

## Response 400

```json
{
  "timestamp": "2026-05-22T10:00:00",
  "status": 400,
  "erro": "Validação falhou. Os dados não cumprem as políticas de segurança."
}
```

---

## Response 500

```json
{
  "timestamp": "2026-05-22T10:00:00",
  "status": 500,
  "erro": "Ocorreu um problema interno. A equipe técnica foi notificada."
}
```

---

# Auditoria

Toda consulta à IA é registrada.

## Exemplo de Log

```text
LOG DE AUDITORIA | TIMESTAMP: 2026-05-22T14:00:00Z
| USER: joao.vendas
| ACTION: CONSULTA_IA
| TARGET_FORD: Ranger Limited
| TARGET_CONCORRENTE: Toyota Hilux
```

---

# Swagger/OpenAPI

A documentação interativa pode ser acessada via Swagger.

## Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Como Executar o Projeto

## Pré-requisitos

- Java 17
- Maven
- Conta Google Gemini API

---

## Configurar API Key

Adicionar no `application.properties`:

```properties
gemini.api.key=SUA_API_KEY
```

---

## Executar

```bash
mvn spring-boot:run
```

---

# Estrutura do Projeto

```text
src/main/java/com/ford/challenge
│
├── config
├── controller
├── dto
├── exception
├── model
├── repository
├── service
```

---

# Melhorias Futuras

- JWT Authentication
- PostgreSQL
- Docker
- Kubernetes
- Redis Cache
- Histórico persistente
- Dashboard analytics
- Comparação multimarca

---

# Objetivos Acadêmicos Atendidos

- API RESTful
- Integração com IA
- Segurança RBAC
- Auditoria
- Sanitização
- OpenAPI/Swagger
- Deploy externo
- Integração de dados
- Persistência

---

# Autor

Projeto desenvolvido para o Ford Challenge — FIAP.