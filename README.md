# Spring Security Application

Esta aplicação é um exemplo de como configurar e utilizar o Spring Security para autenticação e autorização de usuários em uma aplicação Spring Boot. A aplicação utiliza tokens JWT (JSON Web Tokens) para autenticação e define diferentes níveis de acesso para usuários com diferentes roles (papéis).

## Funcionalidades

- **Autenticação de Usuários**: Os usuários podem se autenticar utilizando suas credenciais (email e senha) para obter um token JWT.
- **Autorização de Usuários**: A aplicação define diferentes níveis de acesso para usuários com diferentes roles (papéis), como ADMIN e USER.
- **Proteção de Endpoints**: Os endpoints da aplicação são protegidos com base nas roles dos usuários, garantindo que apenas usuários autorizados possam acessar determinados recursos.
- **Configuração Stateless**: A aplicação é configurada para ser stateless, ou seja, não mantém estado de sessão entre as requisições. Cada requisição é autenticada de forma independente utilizando tokens JWT.

## Endpoints

### `/auth/register` (POST)

- **Descrição**: Endpoint para registrar um novo usuário.
- **Acesso**: Apenas usuários com a role ADMIN podem acessar este endpoint.
- **Exemplo de Requisição**:
  ```json
  {
    "email": "user@example.com",
    "password": "password123",
    "role": "USER"
  }
  ```

### `/auth/login` (POST)

- **Descrição**: Endpoint para autenticar um usuário e obter um token JWT.
- **Acesso**: Acesso público, qualquer usuário pode acessar este endpoint para se autenticar.
- **Exemplo de Requisição**:
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
  ```

### `/user` (GET)

- **Descrição**: Endpoint para obter informações do usuário autenticado.
- **Acesso**: Apenas usuários com a role ADMIN podem acessar este endpoint.
- **Exemplo de Requisição**:
  ```http
  GET /user
  Authorization: Bearer <token_jwt>
  ```

## Configuração de Segurança

A configuração de segurança da aplicação é definida na classe `SecurityConfig`. Aqui estão os principais pontos da configuração:

### Desabilitar Proteção CSRF

```java
.csrf(csrf -> csrf.disable())
```
- **Descrição**: Desabilita a proteção CSRF (Cross-Site Request Forgery) para a aplicação. Isso é geralmente seguro para APIs RESTful que utilizam tokens JWT para autenticação.

### Configuração Stateless

```java
.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
```
- **Descrição**: Configura a política de sessão como stateless, ou seja, a aplicação não mantém estado de sessão entre as requisições. Cada requisição é autenticada de forma independente utilizando tokens JWT.

### Autorização de Requisições

```java
.authorizeHttpRequests(authorize -> authorize
    .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN")
    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
    .requestMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
    .anyRequest().authenticated())
```
- **Descrição**: Define as regras de autorização para os endpoints da aplicação. Apenas usuários com a role ADMIN podem acessar o endpoint `/auth/register` e `/user`, enquanto o endpoint `/auth/login` é acessível por qualquer usuário.

### Filtro de Segurança

```java
.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
```
- **Descrição**: Adiciona o filtro de segurança `SecurityFilter` antes do filtro `UsernamePasswordAuthenticationFilter` para verificar se o token do usuário é válido para a operação solicitada.

## Roles de Usuários

A aplicação define diferentes níveis de acesso para usuários com diferentes roles (papéis):

- **ADMIN**: Usuários com a role ADMIN têm acesso a todos os endpoints da aplicação, incluindo o endpoint de registro de novos usuários.
- **USER**: Usuários com a role USER têm acesso limitado aos endpoints da aplicação.

## Configuração de Beans

### `PasswordEncoder`

```java
@Bean
public PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();
}
```
- **Descrição**: Define um bean `PasswordEncoder` utilizando o `BCryptPasswordEncoder` para codificação de senhas.

### `AuthenticationManager`

```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    throws Exception {
  return authenticationConfiguration.getAuthenticationManager();
}
```
- **Descrição**: Define um bean `AuthenticationManager` utilizando a configuração de autenticação fornecida pelo Spring Security.

## Conclusão

Esta aplicação demonstra como configurar e utilizar o Spring Security para autenticação e autorização de usuários em uma aplicação Spring Boot. Utilizando tokens JWT para autenticação e definindo diferentes níveis de acesso para usuários com diferentes roles, a aplicação garante que apenas usuários autorizados possam acessar determinados recursos.
