# Kafkatest

A lightweight application for testing Apache Kafka message publishing and consumption, with Zipkin integration for distributed tracing and latency troubleshooting.

## Table of Contents
- [General Information](#general-information)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Screenshots](#screenshots)
- [Setup](#setup)
- [Usage](#usage)
- [Project Status](#project-status)
- [Room for Improvement](#room-for-improvement)

## General Information
This project is designed to test Kafka-based data processing pipelines.  
It publishes and consumes Kafka messages and collects tracing data via Zipkin to help identify performance bottlenecks and latency issues.
Kafka is configured in **KRaft mode (ZooKeeper-less)** and runs via **Docker Compose**, making local setup simple and consistent.

## Technologies Used
- Java / Spring Boot
- Docker & Docker Compose
- Apache Kafka (KRaft mode)
- OpenAPI / Swagger
- Zipkin

## Features
- Publish messages to Kafka topics via REST API
- Consume Kafka messages for processing
- Distributed tracing and latency analysis using Zipkin
- Fully containerized local development environment

## Screenshots
![img.png](img.png)

## Setup

### Prerequisites
- Docker Desktop
- Docker Compose
- Java (only required if running the JAR outside Docker)

### Running with Docker (Recommended)

Kafka runs in **KRaft mode**, so **ZooKeeper is NOT required**.

1. Start Docker Desktop
2. From the repository root (where `docker-compose.yml` is located), run:

```bash
docker-compose up -d
```

This will start:
- Kafka (KRaft mode)
- The application (`kafkatest`)
- Zipkin

3. Verify services are running:

```bash
docker-compose ps
```

### Running the Application Without Docker (Optional)
If you want to run the application locally:
1. Ensure Kafka is running and accessible
2. Build and run the JAR:

```bash
java -jar path/to/kafkatest.jar
```

3. Make sure application properties match your environment:
- Kafka bootstrap servers
- Zipkin URL
- Application port

---

## Usage

### Publish a Message

**Endpoint**
```http
POST http://localhost:8090/api/v1/messages
```

**Body (JSON)**
```json
{
  "key": "some-key",
  "value": "your message payload"
}
```

### OpenAPI / Swagger UI

```text
http://localhost:8090/swagger-ui/index.html
```

### Zipkin UI

```text
http://localhost:9411/zipkin/
```

Use the **RUN QUERY** button to inspect traces and latency information.

## Project Status
In progress.

## Room for Improvement
- Add more consumer-side processing examples
- Add integration tests and CI pipelines
- Provide a multi-broker Kafka KRaft setup
- Improve observability dashboards and metrics
