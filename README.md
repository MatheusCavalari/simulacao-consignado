# Documentação da API Bancária

Esta é a documentação da API bancária RESTful que permite realizar operações bancárias, como criar clientes, realizar transferências e obter informações de conta.

# API Bancária RESTful

Esta API bancária RESTful fornece recursos para criar clientes, realizar transferências entre contas, e obter informações sobre clientes e transferências. Ela é desenvolvida com base nas melhores práticas de design de APIs RESTful e utiliza tecnologias modernas para atender às necessidades de um sistema bancário.

## Boas Práticas Utilizadas

- **Versionamento de API**: A API segue uma estratégia de versionamento no caminho (`/api/v1`) para garantir a compatibilidade com versões futuras.
- **Uso de Verbos HTTP**: Os métodos HTTP (GET, POST) são utilizados de acordo com suas semânticas para operações de leitura e escrita, respectivamente.
- **URI Semântica**: As URIs são projetadas de forma semântica, facilitando a compreensão dos recursos e operações.
- **Respostas HTTP Adequadas**: Respostas HTTP apropriadas, como códigos de status 2xx para sucesso e 4xx para erros do cliente, são fornecidas.
- **Documentação OpenAPI**: A API é documentada usando SpringDoc OpenAPI para fornecer informações detalhadas sobre os recursos, endpoints e modelos. (http://localhost:port/swagger-ui/index.html)
- **Testes Unitários**: São realizados testes unitários para garantir a qualidade do código e a funcionalidade correta da API.

Esta API é uma solução eficiente e segura para operações bancárias, seguindo as melhores práticas e padrões de desenvolvimento de APIs RESTful. Ela oferece uma maneira simples e confiável de gerenciar contas e transferências.


## Endpoints

### Clientes

#### Criar um Cliente

- **Método**: `POST`
- **Rota**: `/api/v1/cliente`
- **Descrição**: Cria um novo cliente com as informações fornecidas.
- **Exemplo de Requisição**:
  ```json
  {
      "nome": "Nome do Cliente",
      "numeroConta": "123456",
      "saldoConta": 1000.0
  }
- **Exemplo de Resposta**:
  ```json
  {
    "id": 1,
    "nome": "Nome do Cliente",
    "numeroConta": "123456",
    "saldoConta": 1000.0
  }

#### Obter Todos os Clientes

- **Método**: `GET`
- **Rota**: `/api/v1/cliente/obterTodosClientes`
- **Descrição**: Obtém a lista de todos os clientes cadastrados.
- **Exemplo de Resposta**:
  ```json
  [
    {
        "id": 1,
        "nome": "Nome do Cliente 1",
        "numeroConta": "123456",
        "saldoConta": 1000.0
    },
    {
        "id": 2,
        "nome": "Nome do Cliente 2",
        "numeroConta": "789012",
        "saldoConta": 2000.0
    }
  ]

#### Obter Todos os Clientes

- **Método**: `GET`
- **Rota**: `/api/v1/cliente/conta/{numeroConta}`
- **Descrição**: Obtém informações de um cliente com base no número da conta.
- **Exemplo de Resposta**:
  ```json
  {
    "id": 1,
    "nome": "Nome do Cliente",
    "numeroConta": "123456",
    "saldoConta": 1000.0
  }

### Transferências

#### Realizar uma Transferência

- **Método**: `POST`
- **Rota**: `/api/v1/transferencia`
- **Descrição**: Realiza uma transferência entre duas contas.
- **Exemplo de Requisição**:
  ```json
  {
    "contaOrigem": "123456",
    "contaDestino": "789012",
    "valor": 500.0
  }
- **Exemplo de Resposta**:
  ```json
  {
    "id": 1,
    "contaOrigem": "123456",
    "contaDestino": "789012",
    "valor": 500.0,
    "data": "2023-10-19T15:30:00",
    "sucesso": true
  }

#### Obter Transferências

- **Método**: `GET`
- **Rota**: `/api/v1/transferencia/{numeroConta}`
- **Descrição**: Obtém a lista de todas as transferências relacionadas a uma conta.
- **Exemplo de Resposta**:
  ```json
  [
    {
        "id": 1,
        "contaOrigem": "123456",
        "contaDestino": "789012",
        "valor": 500.0,
        "data": "2023-10-19T15:30:00",
        "sucesso": true
    },
    {
        "id": 2,
        "contaOrigem": "123456",
        "contaDestino": "789012",
        "valor": 300.0,
        "data": "2023-10-19T15:45:00",
        "sucesso": true
    }
  ]

## Tecnologias Utilizadas

- **Spring Boot 3.0.12**: Framework para desenvolvimento de aplicativos Java.
- **Java 17**: Linguagem de programação.
- **H2 Database**: Banco de dados em memória para desenvolvimento.
- **Lombok**: Biblioteca para reduzir a verbosidade do código.
- **ModelMapper**: Biblioteca para mapeamento de objetos.
- **SpringDoc OpenAPI**: Biblioteca para geração de documentação OpenAPI.
- **JUnit 5**: Framework para testes.

## Pré-requisitos

Para executar esta aplicação em sua máquina, você precisará ter o seguinte instalado:

- **Java Development Kit (JDK) 17**: Certifique-se de ter o JDK 17 instalado em sua máquina. Você pode fazer o download em [AdoptOpenJDK](https://adoptopenjdk.net/) ou [OpenJDK](https://openjdk.java.net/).

- **Maven**: Esta aplicação utiliza o Maven como sistema de gerenciamento de construção. Se você não tiver o Maven instalado, você pode baixá-lo em [Maven Downloads](https://maven.apache.org/download.cgi).

Certifique-se de configurar corretamente as variáveis de ambiente do Java e do Maven em seu sistema.

## Autor

Matheus Cavalari Barbosa







  

