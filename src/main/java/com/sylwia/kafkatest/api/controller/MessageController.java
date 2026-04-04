package com.sylwia.kafkatest.api.controller;

import com.sylwia.kafkatest.api.dto.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/messages")
@Tag(name = "Operations on messages.")
public class MessageController {
    private KafkaTemplate<String, String> kafkaTemplate;

    public MessageController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    @Operation(summary = "Method for publishing messages.")
    public void publish(@RequestBody Message request) {
        kafkaTemplate.send("sylwia", request.getMessage());
    }
}
