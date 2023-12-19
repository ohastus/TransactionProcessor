package dev.vality.transactionprocessor.service;

import dev.vality.transactionprocessor.service.dto.TransactionDto;

public interface BrokerService {
    public void sendMessage(TransactionDto transactionDto);
}
