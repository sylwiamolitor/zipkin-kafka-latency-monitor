package com.sylwia.kafkatest.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageStats {

    private long totalMessages;
    private Map<String, Long> messagesPerAuthor;
    private Map<String, Long> messagesPerHour;
    private List<String> topKeywords;
}
