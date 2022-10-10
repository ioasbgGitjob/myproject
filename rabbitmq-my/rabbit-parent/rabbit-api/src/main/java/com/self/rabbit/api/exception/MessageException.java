package com.self.rabbit.api.exception;

/**
 * @author szy
 * @version 1.0
 * @description
 */

public class MessageException extends Exception{

    private static final long serialVersionUID = -4914085318860780272L;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }
}
