package dev.vality.transactionprocessor.service.impl;

import dev.vality.transactionprocessor.service.BrokerService;
import dev.vality.transactionprocessor.service.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service("kafkaService")
public class KafkaProducerService implements BrokerService {

    private final KafkaTemplate<String, TransactionDto> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, TransactionDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(TransactionDto transactionDto) {
        log.info("Transaction created -> {}", transactionDto);

        CompletableFuture.runAsync(() -> {
            try {
                kafkaTemplate.send("my-topic", transactionDto).get();
                log.info("Transaction successfully sent to Kafka.");
            } catch (Exception e) {
                log.error("Error sending transaction to Kafka: {}", e.getMessage());
            }
        });
    }
}