package com.sylwia.kafkatest.messaging.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.CalendarInterval;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.sylwia.kafkatest.api.dto.Message;
import com.sylwia.kafkatest.api.dto.MessageStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final ElasticsearchClient client;

    public MessageStats getStats() throws IOException {

        SearchResponse<Message> response = client.search(s -> s
                .index("messages")
                .size(0)
                .aggregations("authors", a -> a
                    .terms(t -> t.field("author")))
                .aggregations("hours", a -> a
                    .dateHistogram(h -> h
                        .field("createdAt")
                        .calendarInterval(CalendarInterval.Hour)))
                .aggregations("keywords", a -> a
                    .terms(t -> t
                        .field("message.keyword")
                        .size(10))),
            Message.class);

        assert response.hits().total() != null;
        long total = response.hits().total().value();

        Map<String, Long> authors = new HashMap<>();
        Map<String, Long> hours = new HashMap<>();
        List<String> keywords = new ArrayList<>();

        var authorsAgg = response.aggregations().get("authors").sterms();

        for (StringTermsBucket bucket : authorsAgg.buckets().array()) {
            authors.put(bucket.key().stringValue(), bucket.docCount());
        }

        var hoursAgg = response.aggregations().get("hours").dateHistogram();

        hoursAgg.buckets().array().forEach(bucket ->
            hours.put(bucket.keyAsString(), bucket.docCount())
        );

        var keywordsAgg = response.aggregations().get("keywords").sterms();

        for (StringTermsBucket bucket : keywordsAgg.buckets().array()) {
            keywords.add(bucket.key().stringValue());
        }

        return new MessageStats(total, authors, hours, keywords);
    }
}
