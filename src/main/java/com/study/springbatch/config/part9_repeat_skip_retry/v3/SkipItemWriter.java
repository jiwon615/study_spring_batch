package com.study.springbatch.config.part9_repeat_skip_retry.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class SkipItemWriter implements ItemWriter<String> {
    private int cnt = 0;

    @Override
    public void write(List<? extends String> items) throws Exception {
        for (String item : items) {
            if (item.equals("-12")) {
                throw new CustomSkippableException("write() fail 카운트 : " + cnt);
            } else {
                log.info("ItemWriter 아이템 : {}", item);
            }
        }
        log.info("");
    }
}
