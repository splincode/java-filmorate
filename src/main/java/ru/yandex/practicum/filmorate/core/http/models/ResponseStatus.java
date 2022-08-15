package ru.yandex.practicum.filmorate.core.http.models;

import org.springframework.http.HttpStatus;

public class ResponseStatus {
    private final HttpStatus status;
    private final String message;

    public static ResponseStatus success() {
        return new ResponseStatus(HttpStatus.OK, null);
    }

    public ResponseStatus(HttpStatus code, String message) {
        this.status = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
