# Kafkatest

An application for testing Kafka data processing capabilities with Zipkin for tracing and latency troubleshooting.

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
This project tests Kafka message publishing/consumption and collects timing data with Zipkin to help troubleshoot latency issues.

## Technologies Used
- Java
- Docker
- Apache Kafka
- OpenAPI / Swagger
- Zipkin

## Features
- Publish messages to Kafka topics
- Collect and view tracing/latency data via Zipkin

## Screenshots
![img.png](img.png)

## Setup

Prerequisites:
- Java (to run the application JAR)
- Kafka (local) or Docker (recommended)
- Docker Desktop (if using Docker)

A. Start Kafka locally (Windows):
1. Open two terminals.
2. Navigate to Kafka bin directory, e.g.:
   cd kafka_location\bin\windows
3. In terminal 1:
   zookeeper-server-start.bat ..\..\config\zookeeper.properties
4. In terminal 2:
   kafka-server-start.bat ..\..\config\server.properties

B. Start Kafka locally (Linux / macOS):
1. Open two terminals.
2. In terminal 1:
   ./bin/zookeeper-server-start.sh ./config/zookeeper.properties
3. In terminal 2:
   ./bin/kafka-server-start.sh ./config/server.properties

C. Using Docker (recommended):
1. Start Docker Desktop.
2. From the repository root (where docker-compose.yml is located) run:
   docker-compose up -d
This will start Kafka, Zookeeper, Zipkin (if configured), and other services defined in the compose file.

D. Run the application:
- Build the JAR (if not already built) and run:
  java -jar path/to/your-app.jar
- Ensure the application configuration (bootstrap servers, Zipkin URL, ports) matches your environment.

## Usage
Send messages via the API or use the OpenAPI UI.

- Publish message (example):
  POST http://localhost:8080/api/v1/messages
  Body (JSON):
  {
    "key": "some-key",
    "value": "your message payload"
  }

- OpenAPI / Swagger UI (if enabled):
  http://localhost:8080/swagger-ui/index.html

- Zipkin UI (default):
  http://localhost:9411/zipkin/
  Use the "RUN QUERY" button to view traces and latency information.

## Project Status
In progress.

## Room for Improvement
- Add consumer-side processing examples.
- Add automated tests and CI for integration tests.
- Provide docker-compose examples to run a full local stack with Kafka and Zipkin.

