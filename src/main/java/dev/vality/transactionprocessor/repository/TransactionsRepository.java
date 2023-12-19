package dev.vality.transactionprocessor.repository;

import dev.vality.transactionprocessor.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<TransactionModel, Long> {
}
