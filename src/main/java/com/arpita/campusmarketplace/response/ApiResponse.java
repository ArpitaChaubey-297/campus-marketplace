package com.arpita.campusmarketplace.response;

import java.time.LocalDateTime;

import com.arpita.campusmarketplace.model.Product;


public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private String message;
    private int status;
    private T data;

    public ApiResponse() {}

    public ApiResponse(String message, int status, T data) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
