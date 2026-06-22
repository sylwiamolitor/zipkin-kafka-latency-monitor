package com.sylwia.kafkatest.api.controller;

import com.sylwia.kafkatest.api.dto.Message;
import com.sylwia.kafkatest.api.repository.MessageRepository;
import com.sylwia.kafkatest.messaging.service.KafkaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/messages")
@Tag(name = "Operations on messages.")
@AllArgsConstructor
public class MessageController {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final MessageRepository messageRepository;
    private final KafkaService kafkaService;
    private static final Set<String> ALLOWED_SORT_FIELDS =
        Set.of("message.keyword", "createdAt", "topic");

    @PostMapping
    @Operation(summary = "Method for publishing messages.")
    public void publish(@RequestBody Message request) {
        if (request.getCreatedAt() == null) {
            request.setCreatedAt(Instant.now());
        }
        kafkaTemplate.send(request.getTopic(), request);
    }

    @GetMapping("/search")
    @Operation(summary = "Search messages by query with pagination and sorting.",
        description = "Allowed sort fields: message.keyword, createdAt")
    public Page<Message> search(
        @RequestParam String query,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "DESC") Sort.Direction direction) {

        if (query.isBlank()) {
            return Page.empty();
        }

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            sortBy = "createdAt";
        }

        Pageable pageable = PageRequest.of(
            page,
            Math.min(size, 100),
            Sort.by(direction, sortBy)
        );

        return messageRepository.findByMessageContaining(query, pageable);
    }

    @GetMapping("/topics")
    @Operation(summary = "Returns topic list")
    public List<String> getTopics() {
        return kafkaService.getTopics();
    }

    @GetMapping("/topics/{topic}/messages")
    @Operation(summary = "Returns messages in a topic")
    public List<Message> getMessagesByTopic(@PathVariable String topic) {
        return messageRepository.findByTopicIgnoreCase(topic);
    }
}
