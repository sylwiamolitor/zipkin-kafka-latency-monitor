package com.sylwia.kafkatest.api.repository;

import com.sylwia.kafkatest.api.dto.Message;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MessageRepository extends ElasticsearchRepository<Message, String> {

    @Query("{\"match\": {\"message\": \"?0\"}}")
    List<Message> search(String text);

}
