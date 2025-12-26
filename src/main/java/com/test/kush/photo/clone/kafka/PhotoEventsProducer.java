package com.test.kush.photo.clone.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoEventsProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.topic.photo-events}")
    private String topic;

    public void photoUploaded(Long id, String fileName, String contentType, int bytes) {
        send(id, Map.of(
                "event", "PHOTO_UPLOADED",
                "id", id,
                "fileName", fileName,
                "contentType", contentType,
                "bytes", bytes
        ));
    }

    public void photoDeleted(Long id) {
        send(id, Map.of(
                "event", "PHOTO_DELETED",
                "id", id
        ));
    }

    private void send(Long key, Map<String, Object> payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);

            // когда-то делал для откладки ошибки
            var res = kafkaTemplate.send(topic, String.valueOf(key), json)
                    .get(3, TimeUnit.SECONDS);

            log.info("KAFKA SENT topic={}, partition={}, offset={}, key={}, payload={}",
                    topic,
                    res.getRecordMetadata().partition(),
                    res.getRecordMetadata().offset(),
                    key,
                    json
            );

        } catch (Exception e) {
            log.error("KAFKA SEND FAILED topic={}, key={}, payload={}", topic, key, payload, e);
            throw new RuntimeException("Kafka send failed", e);
        }
    }
}
