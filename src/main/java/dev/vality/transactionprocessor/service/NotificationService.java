package dev.vality.transactionprocessor.service;

import dev.vality.transactionprocessor.service.dto.TransactionDto;

public interface NotificationService {
    void notify(TransactionDto transactionDto);
}
