# raspberry-award-service

Microserviço para gerenciamento de funcionalidades relacionadas ao **Golden Raspberry Awards**.

## Arquitetura
Esse projeto foi estruturado utilizando os princípios da Clean Architecture, com duas camadas principais representadas através de módulos Gradle:
- **application**: Camada baixo nível, contendo configurações e implementação da infraestrutura;
- **core**: Camada alto nível, contendo a lógica de negócio e as regras de domínio.

## Tecnologias e Requisitos
- **Linguagem:** Java 21
- **Framework base:** Quarkus
- **Build:** Gradle
- **Container:** Docker
- **Orquestração (opcional):** Kubernetes

## Setup
Clone o repositório e execute:
```bash
./gradlew build
```

## Testes
```bash
./gradlew test
```

## Execução local
```bash
./gradlew --no-daemon :application:quarkusDev
```

## Docker
Para gerar a imagem e executá-la:
```bash
docker build -t raspberry-award-service:latest . && \
docker run --rm -p 8080:8080 raspberry-award-service:latest
```

## Deploy Kubernetes
Manifestos estão na pasta `k8s/`.

## Documentação da API
http://localhost:8080/q/swagger-ui/

## Funcionalidades

### Schema & ETL
Criação automática do esquema de banco e carga de dados a partir de "movielist.csv" em "/resources".

### Endpoints
- `GET /api/v1/awards/worst-film/producers-intervals`    Retorna produtores com maior e menor intervalo entre premiações;
- `GET /api/v1/awards/worst-director` *(to-do)*;
- `GET /api/v1/awards/worst-actor` *(to-do)*;