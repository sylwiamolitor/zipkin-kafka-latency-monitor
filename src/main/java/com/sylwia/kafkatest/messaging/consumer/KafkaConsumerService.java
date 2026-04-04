package com.sylwia.kafkatest.messaging.consumer;

import com.sylwia.kafkatest.api.dto.Message;
import com.sylwia.kafkatest.api.repository.MessageRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaConsumerService {

    private final MessageRepository repository;

    public KafkaConsumerService(MessageRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "sylwia", groupId = "groupId")
    void listener(String data) {
        Message doc = new Message(
            UUID.randomUUID().toString(),
            data
        );

        repository.save(doc);
    }
}
