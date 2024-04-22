## Simulação Consignado

O microserviço Simulação Consignado realiza simulações de empréstimos consignados, considerando diversas variáveis como prazo, valor e tipo de cliente.

### Como Utilizar

1. **Clonar o Repositório**:
   ```sh
   git clone https://github.com/MatheusCavalari/simulacao-consignado.git

2. **Executar a Aplicação:**:
- **Porta do Servidor: 8082**
- **Banco de Dados: H2 em memória**
   ```sh
    mvn spring-boot:run

### Endpoints
1. **Listar Simulações**: Endpoint para listar todas as simulações.
- **URL**: `/v1/simulacao`
- **Método**: `GET`
- **Resposta de Sucesso**:
    - Código: `200 OK`
    - Corpo: Lista de simulações

2. **Buscar Simulação por ID**: Endpoint para buscar uma simulação pelo ID.
- **URL**: `/v1/simulacao/{id}`
- **Método**: `GET`
- **Parâmetros de URL**: `{id}` - ID da simulação a ser buscada
- **Resposta de Sucesso**:
    - Código: `200 OK`
    - Corpo: Simulação encontrada

3. **Simular Empréstimo Consignado**: Endpoint para simular um empréstimo consignado.
- **URL**: `/v1/simulacao`
- **Método**: `POST`
- **Corpo da Requisição**:
  ```json
  {
  "cpfCliente": "111.111.111-11",
  "valorSolicitado": 1000.00,
  "prazo": 12
  }
  ```
- **Resposta de Sucesso**:
    - Código: `200 OK`
    - Corpo: Simulação realizada

### Dependências

- Spring Boot Web
- Spring Boot DevTools
- Spring Boot Starter Test
- Spring Boot Starter Data JPA
- H2 Database
- Lombok
- Springdoc OpenAPI
- JUnit

### Arquitetura Hexagonal

O microserviço Simulação Consignado segue a arquitetura hexagonal (ou ports and adapters), que separa a lógica de negócios da implementação técnica. Nessa arquitetura, as camadas são organizadas da seguinte forma:

- **Domínio**: Contém as entidades de negócio, os serviços e as interfaces que definem as operações do domínio.
- **Aplicação**: Implementa a lógica de negócios usando os serviços do domínio.
- **Adaptadores**: São responsáveis por conectar o domínio à infraestrutura externa, como bancos de dados e serviços externos.

### Versão do Java

O microserviço foi desenvolvido utilizando Java 17, aproveitando as últimas funcionalidades e melhorias da linguagem.

Framework para desenvolvimento: Spring Boot 3.0.12.

### Gerenciamento de Dependências

O Maven foi utilizado como gerenciador de dependências e para realizar o build da aplicação. Ele simplifica o processo de compilação e gerenciamento de dependências, facilitando o desenvolvimento e a manutenção do projeto.

## Autor

Matheus Cavalari Barbosa
