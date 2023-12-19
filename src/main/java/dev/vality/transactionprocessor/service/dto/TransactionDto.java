package dev.vality.transactionprocessor.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    @NonNull
    @JsonProperty("PID")
    private Long id;

    @NonNull
    @JsonProperty("PAMOUNT")
    private BigDecimal amount;

    @NonNull
    @JsonProperty("PDATA")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private String data;

}
