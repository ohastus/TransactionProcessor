package dev.vality.transactionprocessor.controllers;

import dev.vality.transactionprocessor.exceptions.JsonDeserializationException;
import dev.vality.transactionprocessor.service.BrokerService;
import dev.vality.transactionprocessor.service.TransactionComparisonService;
import dev.vality.transactionprocessor.service.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/test-json/kafka")
public class ProducerController {

    BrokerService transactionService;
    TransactionComparisonService transactionComparisonService;

    @Autowired
    public ProducerController(@Qualifier("kafkaService") BrokerService transactionService,
                              @Qualifier("kafkaComparator") TransactionComparisonService transactionComparisonService) {
        this.transactionService = transactionService;
        this.transactionComparisonService = transactionComparisonService;
    }

    @PostMapping("/producer")
    public ResponseEntity<String> sendOneMessageToKafkaTopic(@RequestBody TransactionDto transaction) {
        try {
            transactionService.sendMessage(transaction);
            transactionComparisonService.compareAndNotify(transaction);
            return ResponseEntity.ok("Transaction successfully sent to Kafka topic");

        } catch (JsonDeserializationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to send transaction: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send transaction: " + e.getMessage());
        }
    }

    @PostMapping("/producers")
    public ResponseEntity<String> sendMessagesToKafkaTopic(@RequestBody List<TransactionDto> transactions) {
        try {
            for (TransactionDto transaction : transactions) {
                transactionService.sendMessage(transaction);
                transactionComparisonService.compareAndNotify(transaction);
            }
            return ResponseEntity.ok("Transactions successfully sent to Kafka topic");
        } catch (JsonDeserializationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to send transactions: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send transactions: " + e.getMessage());
        }
    }
}

