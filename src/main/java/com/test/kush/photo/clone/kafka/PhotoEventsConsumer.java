package com.test.kush.photo.clone.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PhotoEventsConsumer {

    @KafkaListener(topics = "${app.kafka.topic.photo-events}", concurrency = "2")
    public void consume(String message) {
        log.info("KAFKA EVENT RECEIVED: {}", message);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}

        log.info("KAFKA EVENT PROCESSED: {}", message);
    }
}
