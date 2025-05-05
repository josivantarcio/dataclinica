# Dataclin - Sistema de Agendamento para Clínicas

Sistema web fullstack para gerenciamento de clínicas médicas, desenvolvido com Spring Boot e Angular.

## 🚀 Tecnologias

### Backend
- Java 21
- Spring Boot 3.2.3
- Spring Security + JWT
- PostgreSQL
- JPA/Hibernate
- Swagger/OpenAPI
- Maven

### Frontend (Em desenvolvimento)
- Angular 17+
- Angular Material
- TypeScript
- SCSS

## 📋 Pré-requisitos

- Java 21
- Maven
- PostgreSQL
- Node.js 18+
- npm ou yarn

## 🔧 Configuração do Ambiente

### Backend

1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/dataclin.git
cd dataclin
```

2. Configure o banco de dados PostgreSQL
- Crie um banco de dados chamado `dataclin`
- Configure as credenciais no arquivo `application.yml`

3. Execute o projeto
```bash
mvn spring-boot:run
```

O backend estará disponível em `http://localhost:8080/api`

### Frontend (Em desenvolvimento)

1. Entre no diretório do frontend
```bash
cd frontend
```

2. Instale as dependências
```bash
npm install
```

3. Execute o projeto
```bash
ng serve
```

O frontend estará disponível em `http://localhost:4200`

## 📚 Documentação da API

A documentação da API está disponível através do Swagger UI em:
`http://localhost:8080/api/swagger-ui.html`

## 🔐 Segurança

O sistema utiliza autenticação JWT com dois tipos de usuários:
- ADMIN: Acesso total ao sistema
- RECEPCAO: Acesso limitado às funcionalidades de agendamento

## 🛠️ Funcionalidades

- [x] Autenticação e autorização
- [x] CRUD de médicos
- [x] CRUD de pacientes
- [x] Agendamento de consultas
- [ ] Dashboard administrativo
- [ ] Relatórios
- [ ] Notificações

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes. 