package dev.vality.transactionprocessor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JsonDeserializationException extends RuntimeException {
    public JsonDeserializationException(String message) {
        super(message);
    }
}
