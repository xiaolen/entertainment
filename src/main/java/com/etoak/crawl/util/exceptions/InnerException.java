package com.etoak.crawl.util.exceptions;


import com.etoak.crawl.util.enums.StatusCode ;

public class InnerException extends RuntimeException {
    private StatusCode statusCode;

    public InnerException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }


}
