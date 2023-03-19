package com.study.springbatch.config.part9_repeat_skip_retry.v3;

public class CustomSkippableException extends Exception {
    public CustomSkippableException(String s) {
        super(s);
    }
}
