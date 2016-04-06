package com.amrendra.codefiesta.model;

/**
 * Created by Amrendra Kumar on 07/12/15.
 */
public class Error {

    public static final int CONNECTION_ERROR = -100;
    public static final int UNKNOWN_ERROR = -200;
    public static final int SUCCESS = 200;

    private int code;
    private String description;

    public Error() {
        code = SUCCESS;
        description = "Success";
    }

    public Error(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Error[" + code + ": " + description + "]";
    }
}