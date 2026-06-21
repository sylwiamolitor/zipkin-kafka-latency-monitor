package com.sylwia.kafkatest.messaging.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaAdmin kafkaAdmin;

    public List<String> getTopics() {
        try (AdminClient adminClient =
                 AdminClient.create(kafkaAdmin.getConfigurationProperties())) {

            return adminClient.listTopics()
                .names()
                .get()
                .stream()
                .filter(topic -> !topic.startsWith("__"))
                .sorted()
                .toList();

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch Kafka topics", e);
        }
    }
}
