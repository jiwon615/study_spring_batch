package com.study.springbatch.config.part8_itemprocessor.v1;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor1 implements ItemProcessor<String, String> {
    int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        cnt++;
        return (item + cnt).toUpperCase();  // item -> ITEM1, ITEM2, ITEM3, ITEM4....
    }
}
