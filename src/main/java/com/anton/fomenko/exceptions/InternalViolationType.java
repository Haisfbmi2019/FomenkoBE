package com.anton.fomenko.exceptions;

public enum InternalViolationType {

    //Auth
    INVALID_PASSWORD_EXCEPTION(1001, "Invalid password"),

    //Registration
    EMAIL_IN_USE_EXCEPTION(2001, "Email address already in use"),

    //User
    USER_NOT_FOUND_EXCEPTION(3001, "User not found");

    private final int code;
    private final String message;

    InternalViolationType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ViolationType{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
