package dev.vality.transactionprocessor.service.impl;

import dev.vality.transactionprocessor.model.TransactionModel;
import dev.vality.transactionprocessor.repository.TransactionsRepository;
import dev.vality.transactionprocessor.service.NotificationService;
import dev.vality.transactionprocessor.service.TransactionComparisonService;
import dev.vality.transactionprocessor.service.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component("kafkaComparator")
public class KafkaTransactionComparisonService implements TransactionComparisonService {

    private final TransactionsRepository transactionsRepository;
    private final NotificationService notificationService;

    @Autowired
    public KafkaTransactionComparisonService(TransactionsRepository transactionsRepository,
                                             @Qualifier("kafkaNotify") NotificationService notificationService) {
        this.transactionsRepository = transactionsRepository;
        this.notificationService = notificationService;
    }

    @Override
    @KafkaListener(id = "consumer-group-1",topics = "${notification.kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public TransactionDto compareAndNotify(TransactionDto transactionDto) {
        Long transactionId = transactionDto.getId();
        try {
            TransactionModel dbTransaction = transactionsRepository.findById(transactionId)
                    .orElse(null);

            if (dbTransaction != null && dbTransaction.getAmount().equals(transactionDto.getAmount())) {

                log.info("Transactions matched. Sending notification.");
                notificationService.notify(transactionDto);
            } else {
                log.info("Transactions did not match. No notification sent.");
            }
        } catch (Exception e) {
            log.error("Error accessing the database: {}", e.getMessage());
        }
        return transactionDto;
    }
}
