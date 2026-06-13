package com.sylwia.kafkatest.api.controller;

import com.sylwia.kafkatest.api.dto.Message;
import com.sylwia.kafkatest.api.repository.MessageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Paginated search in messages.")
    public Page<Message> search(
        @RequestParam String query,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        return messageRepository.findByMessageContaining(
            query,
            PageRequest.of(page, Math.min(size, 100))
        );
    }
}
