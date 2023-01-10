package com.anton.fomenko.exceptions;

public class InternalViolationException extends RuntimeException {

    private final InternalViolationType type;
    private final String customMessage;

    public InternalViolationException(InternalViolationType type) {
        super(type.getMessage(), null, false, false);
        this.type = type;
        this.customMessage = type.getMessage();
    }

    public InternalViolationException(InternalViolationType type, String customMessage) {
        super(customMessage, null, false, false);
        this.type = type;
        this.customMessage = customMessage;
    }

    public InternalViolationType getType() {
        return type;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    @Override
    public String toString() {
        return "InternalViolationException{" +
                "type=" + type +
                ", message='" + customMessage + '\'' +
                '}';
    }
}
