package com.study.springbatch.config.part9_repeat_skip_retry.v4;

public class RetryableException extends RuntimeException{

    public RetryableException() {
        super();
    }

    public RetryableException(String message) {
        super(message);
    }
}
