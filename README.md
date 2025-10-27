# Java API Sample

Uma API RESTful simples em Java para gerenciamento de usuários, construída sem o uso de frameworks externos, utilizando apenas as bibliotecas nativas do Java. Este projeto demonstra a criação de um servidor HTTP, manipulação de rotas e operações CRUD (Create, Read, Update, Delete) para uma entidade de usuário.

## Funcionalidades

- **Servidor HTTP Nativo**: Implementado com a classe `com.sun.net.httpserver.HttpServer`.
- **Operações CRUD**: Suporte completo para criar, ler, atualizar e deletar usuários.
- **Armazenamento em Memória**: Os dados dos usuários são armazenados em uma `ArrayList` em memória, sendo reiniciados a cada execução do servidor.
- **Manipulação de JSON**: Parsing e geração de JSON de forma manual, sem bibliotecas externas como GSON ou Jackson.

## Tecnologias Utilizadas

- **Java**: O projeto foi desenvolvido inteiramente em Java.
- **Servidor HTTP Nativo do Java**: Para a criação e gerenciamento do servidor web.

## Como Começar

Para executar este projeto, você precisará ter o JDK (Java Development Kit) instalado em sua máquina.

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/jacksjm/java-api-sample.git
    ```

2.  **Navegue até o diretório do projeto:**

    ```bash
    cd java-api-sample/src
    ```

3.  **Compile os arquivos Java:**

    ```bash
    javac *.java
    ```

4.  **Execute a aplicação:**

    ```bash
    java App
    ```

Após a execução, o servidor estará rodando em `http://localhost:8089`.

## Endpoints da API

A API possui um único endpoint `/user` que suporta as seguintes operações:

| Método | Descrição | Corpo da Requisição | Resposta |
| --- | --- | --- | --- |
| `GET` | Retorna uma lista de todos os usuários cadastrados. | - | `200 OK` com um JSON contendo a lista de usuários. |
| `POST` | Adiciona um novo usuário. | JSON com os dados do usuário. | `201 Created` com uma mensagem de sucesso. |
| `PUT` | Atualiza um usuário existente. | JSON com o `id` e os dados a serem atualizados. | `200 OK` com uma mensagem de sucesso. |
| `DELETE` | Remove um usuário existente. | JSON com o `id` do usuário a ser removido. | `200 OK` com uma mensagem de sucesso. |

### Exemplo de Corpo de Requisição

**POST /user**

```json
{
    "nome": "Tadeu",
    "email": "tadeu@mail.com",
    "dataNascimento": "01/01/1990",
    "cep": "89205035",
    "genero": "masculino",
    "senha": "123456"
}
```

**PUT /user**

```json
{
    "id": 1,
    "nome": "Tadeu Silva"
}
```

**DELETE /user**

```json
{
    "id": 1
}
```

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

