package com.amrendra.codefiesta.model;

/**
 * Created by Amrendra Kumar on 07/04/16.
 */
public class ApiResponse<T> {

    Error error;
    T content;

    public ApiResponse() {
        error = new Error();
    }

    public Error getError() {
        return error;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ApiResponse:" + error.toString();
    }
}
