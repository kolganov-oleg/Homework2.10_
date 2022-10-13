package com.example.homework_stream.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException() {
    }

    public IncorrectInputException(String message) {
        super(message);
    }

    public IncorrectInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputException(Throwable cause) {
        super(cause);
    }

    public IncorrectInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
