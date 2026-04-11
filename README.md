# Desafio Muralis

Este projeto é uma aplicação web de gerenciamento de clientes e contatos. Nela é possível cadastrar, visualizar, editar e excluir clientes, além de gerenciar os contatos vinculados a cada um deles. A aplicação também conta com busca por nome e CPF, paginação e uma interface limpa e responsiva.

---

## Sumário

- [Uso de IA durante o desenvolvimento](#uso-de-ia-durante-o-desenvolvimento)
- [Banco de Dados](#banco-de-dados)
- [Back-end](#back-end)
  - [Visão Geral](#visão-geral)
  - [Estrutura de Pacotes](#estrutura-de-pacotes)
  - [Camadas](#camadas)
  - [Testes](#testes)
  - [Dependências](#dependências)
  - [Tecnologias](#tecnologias)
  - [Fluxo de uma Requisição](#fluxo-de-uma-requisição)
- [Front-end](#front-end)
  - [Visão Geral](#visão-geral-1)
  - [Estrutura de Pastas](#estrutura-de-pastas)
  - [Camadas](#camadas-1)
  - [Dependências](#dependências-1)
  - [Tecnologias](#tecnologias-1)
  - [Fluxo de uma Requisição](#fluxo-de-uma-requisição-1)

---

## Uso de IA durante o desenvolvimento

A inteligência artificial foi utilizada como apoio na geração de códigos operacionais. No entanto, todas as decisões estratégicas — como escolha de tecnologias, definição de arquitetura, abstrações e organização do projeto — foram tomadas por mim, sendo a IA empregada apenas como ferramenta de execução.

---

## Banco de Dados

Subindo um container no Docker o banco será populado automaticamente. Mesmo assim, o script se encontra na pasta:

```
Back-end/
└── db/
    └── init.sql
```

## Requisitos

- Node.js 18+
- Java 17
- Docker e Docker Compose

---

## Como executar

### Back-end

O back-end sobe junto com o banco de dados MySQL via Docker Compose. O banco é populado automaticamente na primeira execução.

```bash
cd Back-end
docker compose up
```

A API estará disponível em `http://localhost:8080`.

### Front-end

```bash
cd front-next
npm install
npm run dev
```

O front-end estará disponível em `http://localhost:3000`.


---

## Back-end

### Visão Geral

O back-end segue os princípios da **Arquitetura Limpa (Clean Architecture)**, separando claramente as responsabilidades em camadas bem definidas. A comunicação entre camadas segue a direção:

```
Web → Application → Enterprise → Infra
```

### Estrutura de Pacotes

```
com.desafio.backend
├── application
│   ├── exceptions
│   └── useCases
├── enterprise
│   ├── cliente
│   ├── contato
│   └── pagination
├── infra
│   └── database
└── web
    ├── config
    ├── controllers
    ├── dto
    └── exceptions
```

### Camadas

**Enterprise**
Núcleo da aplicação. Contém as entidades de domínio e as interfaces de repositório. Não depende de nenhuma outra camada.

**Application**
Contém os casos de uso da aplicação. Orquestra as regras de negócio utilizando as interfaces definidas na camada Enterprise. Também concentra as exceções específicas utilizadas durante a execução.

**Infra**
Implementações concretas dos repositórios. Isola os detalhes de acesso ao banco de dados do restante da aplicação.

**Web**
Camada de entrada HTTP. Expõe os endpoints REST e trata erros globalmente. Organizada em controllers por entidade, DTOs de request e response, configurações do Spring e um handler global de exceções.

### Testes

```
test/
├── e2e/
│   └── controllers/       — Testes de ponta a ponta com RestTestClient
└── unit/
    ├── useCases/          — Testes de unidade dos casos de uso
    └── valueObjects/      — Testes dos value objects (ex: CPF)
```

### Dependências

**Produção**

| Dependência | Descrição |
|-------------|-----------|
| `spring-boot-starter-web` | Servidor HTTP embutido (Tomcat) e suporte a REST |
| `spring-boot-starter-data-jdbc` | Acesso a banco de dados via `JdbcTemplate` |
| `mysql-connector-j` | Driver JDBC para MySQL |

**Teste**

| Dependência | Descrição |
|-------------|-----------|
| `spring-boot-starter-test` | JUnit 5, AssertJ, Mockito e suporte a testes Spring |
| `h2` | Banco de dados em memória utilizado nos testes |

### Tecnologias

| Tecnologia | Versão |
|------------|--------|
| Java | 17 |
| Spring Boot | 4.0.5 |
| Maven | — |
| MySQL | — (produção) |
| H2 | — (testes) |

### Fluxo de uma Requisição

```
HTTP Request
    │
    ▼
Controller (web)
    │  valida entrada, chama use case
    ▼
Use Case (application)
    │  executa regra de negócio
    ▼
Repository Interface (enterprise)
    │  contrato de acesso a dados
    ▼
Repository JDBC (infra)
    │  executa SQL via JdbcTemplate
    ▼
Banco de Dados (MySQL / H2)
```

---

## Front-end

### Visão Geral

O front-end é construído com **Next.js 15** (App Router) e segue uma organização por responsabilidade, separando claramente páginas, componentes, lógica de dados, serviços HTTP e validação de schemas.

### Estrutura de Pastas

```
src/
├── app/               — Rotas e páginas (Next.js App Router)
├── components/        — Componentes reutilizáveis de UI
├── hooks/             — Hooks de data fetching e mutations (React Query)
├── interfaces/        — Tipos TypeScript e schemas Zod
├── lib/               — Utilitários gerais
├── providers/         — Providers globais (React Query, etc.)
├── services/          — Funções de chamada HTTP (Axios)
└── templates/         — Layouts de página reutilizáveis
```

### Camadas

**`app/`**
Diretório de roteamento do Next.js App Router. Cada pasta representa uma rota da aplicação. Contém o layout raiz que injeta a barra de navegação, o provider de dados e o sistema de notificações. As rotas são aninhadas refletindo a hierarquia de recursos: clientes, seus contatos e as páginas de busca.

**`components/`**
Componentes de UI organizados por contexto. Divididos entre componentes base reutilizáveis (shadcn/ui), componentes específicos de cada entidade de negócio (cliente e contato) e os componentes de tabela com suas definições de colunas.

**`hooks/`**
Encapsula toda a lógica de comunicação com o servidor. Cada hook utiliza o TanStack Query para gerenciar cache, estados de loading e erro, e invalidação automática após mutations. A separação por entidade mantém a organização alinhada com os recursos da API.

**`interfaces/`**
Define os contratos de dados da aplicação. Contém os tipos TypeScript das entidades e os schemas Zod responsáveis por validar e fazer o parsing das respostas da API e dos dados de formulário antes do envio.

**`services/`**
Funções puras de chamada HTTP utilizando Axios. Cada função representa um endpoint da API REST, sem lógica de estado ou cache — essa responsabilidade fica nos hooks.

**`providers/`**
Providers React injetados no layout raiz. Responsável por configurar e disponibilizar o `QueryClient` do TanStack Query para toda a árvore de componentes.

**`lib/`**
Utilitários gerais compartilhados entre as camadas da aplicação, como funções auxiliares de estilização.

**`templates/`**
Layouts de página reutilizáveis para estruturas visuais comuns entre diferentes rotas.

### Dependências

**Produção**

| Dependência | Descrição |
|-------------|-----------|
| `next` 15 | Framework React com App Router, SSR e otimizações |
| `react` / `react-dom` 19 | Biblioteca base de UI |
| `@tanstack/react-query` | Data fetching, cache e sincronização de estado servidor |
| `@tanstack/react-table` | Tabelas headless com suporte a colunas customizadas |
| `axios` | Cliente HTTP para chamadas à API REST |
| `react-hook-form` | Gerenciamento de formulários performático |
| `@hookform/resolvers` | Integração do react-hook-form com Zod |
| `zod` | Validação e parsing de schemas TypeScript-first |
| `sonner` | Notificações toast |
| `lucide-react` | Biblioteca de ícones SVG |
| `@radix-ui/*` | Componentes acessíveis sem estilo (base do shadcn/ui) |
| `class-variance-authority` | Variantes de componentes com Tailwind |
| `clsx` + `tailwind-merge` | Merge seguro de classes CSS |
| `next-themes` | Suporte a temas claro/escuro |
| `react-input-mask` | Máscaras de input (ex: CPF, telefone) |
| `react-number-format` | Formatação numérica em inputs |

**Desenvolvimento**

| Dependência | Descrição |
|-------------|-----------|
| `typescript` 5 | Tipagem estática |
| `tailwindcss` 4 | Framework CSS utilitário |
| `@tailwindcss/postcss` | Integração Tailwind com PostCSS |
| `tw-animate-css` | Animações prontas para Tailwind |
| `eslint` + `eslint-config-next` | Linting com regras específicas para Next.js |

### Tecnologias

| Tecnologia | Versão |
|------------|--------|
| Next.js | 15.3.3 |
| React | 19 |
| TypeScript | 5 |
| Tailwind CSS | 4 |
| TanStack Query | 5 |
| TanStack Table | 8 |
| Zod | 3 |
| Axios | 1 |

### Fluxo de uma Requisição

```
Interação do usuário (clique, submit)
    │
    ▼
Componente (components/)
    │  chama hook
    ▼
Hook React Query (hooks/)
    │  gerencia cache, loading e error states
    ▼
Service (services/)
    │  executa chamada HTTP via Axios
    ▼
API REST (Spring Boot)
    │
    ▼
Resposta validada com Zod (interfaces/)
    │
    ▼
Estado atualizado no componente
```
