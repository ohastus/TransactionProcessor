package dev.vality.transactionprocessor.service.impl;

import dev.vality.transactionprocessor.service.NotificationService;
import dev.vality.transactionprocessor.service.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service("kafkaNotify")
public class KafkaNotificationService implements NotificationService {

    private final String notificationTopic;
    private final KafkaTemplate<String, TransactionDto> kafkaTemplate;

    public KafkaNotificationService(KafkaTemplate<String, TransactionDto> kafkaTemplate,
                                    @Value("${notification.kafka.topic}") String notificationTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.notificationTopic = notificationTopic;
    }

    @Override
    public void notify(TransactionDto transactionDto) {
        log.info("Sending notification to Kafka topic: {}", notificationTopic);
        kafkaTemplate.send(notificationTopic, transactionDto);
        log.info("Notification sent successfully");
    }
}
