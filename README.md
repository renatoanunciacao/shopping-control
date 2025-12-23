# Shopping Control API

API backend desenvolvida em **Spring Boot** para controle de usuários e autenticação, incluindo **login tradicional** e **login via Google OAuth 2.0**, com geração de **JWT** para autenticação stateless.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
  - Spring Web
  - Spring Security
  - Spring Data JPA
- **OAuth 2.0 (Google Login)**
- **JWT (JSON Web Token)**
- **Hibernate**
- **PostgreSQL**
- **Maven**
- **JUnit 5**

---

## 🧩 Arquitetura

O projeto segue uma arquitetura em camadas:

```
src/main/java
 └── com.shopping_control
     ├── config        # Configurações de segurança, filtros JWT
     ├── controller    # Controllers REST
     ├── entity        # Entidades JPA
     ├── repository   # Repositórios
     ├── service      # Regras de negócio
     └── dto          # Objetos de transferência de dados
```

---

## 🔐 Autenticação

### Tipos de login suportados

- **LOCAL**
  - Email + senha
- **GOOGLE**
  - OAuth 2.0
  - Usuário criado sem senha
  - Provider = `GOOGLE`

### Estratégia de autenticação

- Autenticação **stateless**
- Token JWT enviado no header:

```
Authorization: Bearer <token>
```

---

## 🔑 Fluxo de Login com Google

1. Usuário acessa:
   
   ```
   GET /auth/google/login
   ```

2. É redirecionado para o Google
3. Após consentimento, o Google retorna:
   
   ```
   /auth/google/callback?code=...
   ```

4. O backend:
   - Troca o `code` por `access_token`
   - Busca os dados do usuário no Google
   - Cria ou atualiza o usuário no banco
   - Gera um JWT da aplicação

---

## 📌 Endpoints Principais

### Autenticação

| Método | Endpoint | Descrição |
|------|--------|----------|
| GET | `/auth/google/login` | Inicia login com Google |
| GET | `/auth/google/callback` | Callback do Google |
| POST | `/auth/login` | Login local |

---

## 🗄️ Entidade User (resumo)

```java
User {
  id
  name
  email
  password (nullable)
  provider (LOCAL | GOOGLE)
  active
}
```

- Usuários Google **não possuem senha**
- Usuários locais possuem senha hashada

---

## 🧪 Testes

O projeto utiliza **JUnit 5**.

### Executar testes

```bash
mvn test
```

### Executar um teste específico

```bash
mvn -Dtest=UserTest test
```

---

## ⚙️ Configuração do Ambiente

### Variáveis necessárias

Configure no `application.yml` ou variáveis de ambiente:

```yaml
google:
  client-id: SEU_CLIENT_ID
  client-secret: SEU_CLIENT_SECRET

jwt:
  secret: SUA_SECRET_KEY
```

---

## 🛡️ Segurança

- CSRF desabilitado (API stateless)
- Sessões desativadas
- Filtro JWT customizado
- Rotas públicas:
  - `/auth/**`
  - `/users`

---

## 📦 Build do Projeto

```bash
mvn clean package
```

O JAR será gerado em:

```
target/shopping-control.jar
```

---

## 📄 Versionamento

Arquivos ignorados no Git:
- `target/`
- `.idea/`
- `.vscode/`
- arquivos de build e cache

---

## 📌 Status do Projeto

🚧 Em desenvolvimento  
Funcionalidades principais de autenticação concluídas.

---

## 👤 Autor

**Renato Wagner Anunciação**  
Desenvolvedor Frontend em transição para Backend Java / Spring Boot

