package com.sylwia.kafkatest.api.controller;

import com.sylwia.kafkatest.api.dto.Message;
import com.sylwia.kafkatest.api.repository.MessageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
@Tag(name = "Operations on messages.")
public class MessageController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessageRepository messageRepository;

    public MessageController(KafkaTemplate<String, String> kafkaTemplate, MessageRepository messageRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageRepository = messageRepository;
    }

    @PostMapping
    @Operation(summary = "Method for publishing messages.")
    public void publish(@RequestBody Message request) {
        kafkaTemplate.send("sylwia", request.getMessage());
    }

    @GetMapping("/search")
    public List<Message> search(@RequestParam String query) {
        return messageRepository.search(query);
    }
}
