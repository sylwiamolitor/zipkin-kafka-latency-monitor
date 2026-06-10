package com.sylwia.kafkatest.filter;

import com.sylwia.kafkatest.api.dto.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;

@Component
public class MessageFilter implements RecordFilterStrategy<String, Message> {

    private static final Logger log = LoggerFactory.getLogger(MessageFilter.class);

    @Override
    public boolean filter(ConsumerRecord<String, Message> record) {

        Message message = record.value();

        boolean reject =
            message == null
                || message.getId() == null
                || message.getId().isBlank()
                || message.getMessage() == null
                || message.getMessage().isBlank();

        if (reject) {
            log.warn("Rejected Kafka message: {}", message);
        }

        return reject;
    }
}
