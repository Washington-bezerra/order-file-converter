# Order File Converter

Este projeto é uma aplicação desenvolvida em Kotlin, utilizando Spring Boot e Gradle. Ele é responsável por converter arquivos de pedidos e devolver um JSON.

## Como rodar o projeto

Existem duas maneiras de rodar este projeto:

1. **Usando Docker Compose**: No terminal, navegue até a raiz do projeto e execute o seguinte comando:

```bash
docker-compose up -d
```

2. **Usando uma IDE**: Você pode rodar o projeto localmente através de sua IDE favorita. Certifique-se de ter um banco de dados Postgres local configurado e em execução (Altere o `application.yml`, se necessário).

## Como testar a API usando Postman

Para testar a API usando o Postman, siga as etapas abaixo:

1. Abra o Postman e clique em "New" > "Request".

2. No campo "Request URL", insira a URL da API que você deseja testar. No seu caso, seria `localhost:8080/v1/convert/order-file`.

3. No dropdown à esquerda da "Request URL", selecione o método HTTP apropriado. No seu caso, seria `POST`.

4. Clique na aba "Body" abaixo da "Request URL". Selecione "form-data".

5. No novo painel que aparece, você verá uma opção para adicionar "Key" e "Value". Adicione a chave `userName` e o valor `user123`.

6. Para adicionar o arquivo, clique em "Key" e mude o tipo de "Text" para "File". No campo "Key", insira `file`. No campo "Value", clique no botão "Select Files" e navegue até o arquivo no seu sistema de arquivos (Você pode usar o arquivo de exemplo que está no final deste readme).

7. Clique no botão "Send" para enviar a solicitação.

Aqui está um exemplo de CURL de como deve ficar sua requisição:

```
curl --location 'localhost:8080/v1/convert/order-file' \
--header 'userName: user123' \
--form 'file=@"data_1.txt"'
```

Lembre-se de substituir o caminho do arquivo `data_1.txt` pelo caminho correto no seu sistema de arquivos.

## Como executar os testes

Para rodar os testes, você pode usar o seguinte comando no terminal:

```bash
./gradlew test
```

## Como executar validação de cobertura de código

Para verificar a cobertura do código, você pode usar o seguinte comando no terminal:

```bash
./gradlew jacocoTestCoverageVerification
```

O relatório de cobertura será gerado no diretório `build/reports/jacoco/test/html/`.

## Como rodar o relatório de testes

Para gerar o relatório de testes, você pode usar o seguinte comando no terminal:

```bash
./gradlew jacocoTestReport
```

O relatório de testes será gerado no diretório `build/reports/tests/test/`.

## Pipeline CI/CD

Este projeto utiliza uma pipeline CI/CD configurada através do GitHub Actions. A pipeline é definida no arquivo `.github/workflows/ci.yml` e é composta por três jobs principais: `build`, `test` e `docker`.

- O job `build` compila o projeto usando Gradle.
- O job `test` executa os testes unitários e verifica a cobertura de código.
- O job `docker` constrói a imagem Docker do projeto.

A pipeline é acionada sempre que um novo commit é feito na branch `main` ou quando um pull request é aberto para a branch `main`.

Para visualizar o status da pipeline e os detalhes de cada execução, você pode visitar a página de Actions do repositório no GitHub em [https://github.com/Washington-bezerra/order-file-converter/actions](https://github.com/Washington-bezerra/order-file-converter/actions).

## Repositório do Projeto

O código-fonte do projeto está disponível no GitHub. Você pode acessar o repositório em [https://github.com/Washington-bezerra/order-file-converter](https://github.com/Washington-bezerra/order-file-converter).

## Exemplo de file

```
0000000002                                     Medeiros00000123450000000111      256.2420201201
0000000001                                      Zarelli00000001230000000111      512.2420211201
0000000001                                      Zarelli00000001230000000122      512.2420211201
0000000002                                     Medeiros00000123450000000122      256.2420201201
```
