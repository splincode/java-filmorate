package ru.yandex.practicum.filmorate.core.http.models;

public class ResponseDataWrapper<T> {
    private T data;
    private final ResponseStatus status;

    public ResponseDataWrapper(ResponseStatus status) {
        this.status = status;
    }

    public ResponseDataWrapper(T data) {
        this.data = data;
        status = ResponseStatus.success();
    }

    public ResponseDataWrapper(T data, ResponseStatus status) {
        this.data = data;
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public ResponseStatus getStatus() {
        return status;
    }
}
