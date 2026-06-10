package com.sylwia.kafkatest.api.controller;

import com.sylwia.kafkatest.api.dto.Message;
import com.sylwia.kafkatest.api.repository.MessageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/messages")
@Tag(name = "Operations on messages.")
public class MessageController {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final MessageRepository messageRepository;

    public MessageController(KafkaTemplate<String, Message> kafkaTemplate, MessageRepository messageRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageRepository = messageRepository;
    }

    @PostMapping
    @Operation(summary = "Method for publishing messages.")
    public void publish(@RequestBody Message request) {
        kafkaTemplate.send("sylwia", request);
    }

    @GetMapping("/search")
    @Operation(summary = "Simple search in messages.")
    public List<Message> search(@RequestParam String query) {

        if (query == null || query.isBlank()) {
            return List.of();
        }

        List<Message> results = messageRepository.search(query);

        return results.stream()
            .filter(Objects::nonNull)
            .limit(10)
            .toList();
    }
}
