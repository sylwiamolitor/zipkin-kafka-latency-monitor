package com.sylwia.kafkatest.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {

    @KafkaListener(topics = "sylwia", groupId = "groupId")
    void listener(String data) {
        System.out.println("Listener received: " + data + " ");
    }
}
