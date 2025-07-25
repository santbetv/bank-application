package com.devsu.hackerearth.backend.client.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiExceptionResponse {

    private String type = "/errors/uncategorized";
    private String title;
    private String code;
    private String detail;
    private String instance = "/errors/";

    public ApiExceptionResponse(String type, String title, String code, String detail) {
        this.type = type;
        this.title = title;
        this.code = code;
        this.detail = detail;
    }

    public ApiExceptionResponse(String title, String code, String detail) {
        this.title = title;
        this.code = code;
        this.detail = detail;
    }
}
