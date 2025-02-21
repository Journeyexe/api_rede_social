# API de Rede Social

## 📋 Descrição
API RESTful desenvolvida com Spring Boot para uma rede social, permitindo criação de posts, comentários, likes e gerenciamento de usuários.

## 🛠️ Tecnologias Utilizadas
- Java 17
- Spring Boot 3.4.2
- Spring Security
- JWT (JSON Web Token)
- MongoDB
- Maven
- Lombok

## 🔧 Configuração

### Pré-requisitos
- Java 17
- MongoDB
- Maven

### Variáveis de Ambiente
Configure as seguintes variáveis no arquivo `application.properties`:

```properties
jwt.secret=sua_chave_secreta
spring.data.mongodb.uri=sua_uri_mongodb
spring.data.mongodb.database=nome_do_banco
```

### Instalação
```bash
# Clone o repositório
git clone https://github.com/Journeyexe/api

# Entre no diretório
cd api

# Instale as dependências
mvn install

# Execute o projeto
mvn spring-boot:run
```

## 📚 Endpoints

### Autenticação
- `POST /api/v1/auth/register` - Registro de novo usuário
- `POST /api/v1/auth/authenticate` - Login de usuário

### Posts
- `POST /api/v1/posts` - Criar novo post
- `GET /api/v1/posts` - Listar todos os posts
- `GET /api/v1/posts/user/{userId}` - Listar posts de um usuário
- `POST /api/v1/posts/{postId}/like` - Curtir/descurtir post
- `POST /api/v1/posts/{postId}/comments` - Adicionar comentário
- `DELETE /api/v1/posts/{postId}/comments/{commentId}` - Remover comentário
- `GET /api/v1/posts/{postId}/author` - Obter autor do post

## 🔒 Segurança
A API utiliza JWT para autenticação. Para acessar endpoints protegidos, inclua o token no header:
```
Authorization: Bearer seu_token_jwt
```

## 📦 Modelos de Requisição

### Registro de Usuário
```json
{
  "username": "usuario",
  "email": "usuario@email.com",
  "password": "senha123"
}
```

### Login
```json
{
  "username": "usuario",
  "password": "senha123"
}
```

### Criar Post
```json
{
  "title": "Título do Post",
  "description": "Descrição do post",
  "imageUrl": "https://url-da-imagem.com"
}
```

### Adicionar Comentário
```json
{
  "content": "Conteúdo do comentário"
}
```

## 👥 Contribuição
Contribuições são bem-vindas! Por favor, leia as diretrizes de contribuição antes de submeter um Pull Request.

## 🤝 Suporte
Em caso de dúvidas ou problemas, abra uma issue no repositório ou entre em contato com a equipe de desenvolvimento.