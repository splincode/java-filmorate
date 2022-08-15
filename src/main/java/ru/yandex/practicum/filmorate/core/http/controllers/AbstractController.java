package ru.yandex.practicum.filmorate.core.http.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.core.functions.MakeResponse;
import ru.yandex.practicum.filmorate.core.http.models.ResponseDataWrapper;
import ru.yandex.practicum.filmorate.core.http.models.ResponseStatus;

public abstract class AbstractController {
    private final static Logger log = LoggerFactory.getLogger(AbstractController.class);

    public <T> ResponseEntity<ResponseDataWrapper<T>> interception(MakeResponse<T> handler) {
        return interception(handler, HttpStatus.BAD_REQUEST);
    }

    public <T> ResponseEntity<ResponseDataWrapper<T>> interception(MakeResponse<T> handler, HttpStatus fallback) {
        try {
            T result = handler.handle();

            log.debug(result.toString());

            return ResponseEntity.ok(new ResponseDataWrapper<>(result));
        } catch (RuntimeException err) {
            log.debug(err.getMessage());

            ResponseStatus status = new ResponseStatus(fallback, err.getMessage());
            return new ResponseEntity<>(new ResponseDataWrapper<>(null, status), fallback);
        }
    }
}
