package com.study.springbatch.config.part9_repeat_skip_retry.v4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class RetryItemProcessor implements ItemProcessor<String, String> {
    private int cnt = 0;
    @Override
    public String process(String item) throws Exception {
        log.info("ItemProcessor 아이템: {}", item);
        if (item.equals("2") || item.equals("3")) {
            cnt++;
            throw new RetryableException("failed cnt: " + cnt);
        }
        return item;
    }
}
