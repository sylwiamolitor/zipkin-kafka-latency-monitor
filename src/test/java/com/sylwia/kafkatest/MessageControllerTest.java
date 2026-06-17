package com.sylwia.kafkatest;

import com.sylwia.kafkatest.api.controller.MessageController;
import com.sylwia.kafkatest.api.dto.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @Mock
    private KafkaTemplate<String, Message> kafkaTemplate;

    @InjectMocks
    private MessageController messageController;

    @Test
    void testPublish() {
        Message request = new Message(UUID.randomUUID().toString(), "Test Message", Instant.now());

        messageController.publish(request);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);

        verify(kafkaTemplate, times(1)).send(topicCaptor.capture(), messageCaptor.capture());

        assertEquals("sylwia", topicCaptor.getValue());
        assertEquals(request, messageCaptor.getValue());
    }
}
