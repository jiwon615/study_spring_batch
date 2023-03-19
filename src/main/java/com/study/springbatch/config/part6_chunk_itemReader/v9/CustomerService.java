package com.study.springbatch.config.part6_chunk_itemReader.v9;

public class CustomerService<T> {
    private int cnt = 0;

    public T customRead() {
        return (T)("item" + cnt++);
    }
}
