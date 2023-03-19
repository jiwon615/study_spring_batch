package com.study.springbatch.config.part9_repeat_skip_retry.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class SkipItemProcessor implements ItemProcessor<String, String> {
    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        log.info("ItemProcessor 아이템: {}", item);
        if (item.equals("6") || item.equals("7")) {
            throw new CustomSkippableException("ItemProcessor에서 발생한 예외는 chunk의 처음으로 돌아가서 다시 시작 (예외발생한 아이템은 skip)" + cnt);
        } else {
            return String.valueOf(Integer.valueOf(item) * -1);
        }
    }
}
