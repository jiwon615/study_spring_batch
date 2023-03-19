package com.study.springbatch.config.part9_repeat_skip_retry.v3;

public class CustomNoSkippableException extends Exception {
    public CustomNoSkippableException(String s) {
        super(s);
    }
}
