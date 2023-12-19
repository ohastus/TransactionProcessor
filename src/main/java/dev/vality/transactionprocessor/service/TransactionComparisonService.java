package dev.vality.transactionprocessor.service;

import dev.vality.transactionprocessor.service.dto.TransactionDto;

public interface TransactionComparisonService {
    public TransactionDto compareAndNotify(TransactionDto transactionDto);
}
