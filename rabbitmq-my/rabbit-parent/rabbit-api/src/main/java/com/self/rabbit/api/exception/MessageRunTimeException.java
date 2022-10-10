package com.self.rabbit.api.exception;

import java.io.Serial;

/**
 * @description
 */

public class MessageRunTimeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5418337875716007511L;

    public MessageRunTimeException() {
        super();
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }
}
