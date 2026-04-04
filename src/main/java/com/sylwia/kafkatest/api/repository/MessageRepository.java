package com.sylwia.kafkatest.api.repository;

import com.sylwia.kafkatest.api.dto.Message;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MessageRepository extends ElasticsearchRepository<Message, String> {
}
