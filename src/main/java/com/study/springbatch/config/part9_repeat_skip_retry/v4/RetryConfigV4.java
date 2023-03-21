package com.study.springbatch.config.part9_repeat_skip_retry.v4;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class RetryConfigV4 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v4");
        
        return jobBuilderFactory.get("batchJob_v4")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v4")
                .<String, String>chunk(5)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(items -> items.forEach(item -> log.info("ItemWriter 아이템: {}", item.toString())))
                .faultTolerant()
                .skip(RetryableException.class)
                .skipLimit(2)
                .retry(RetryableException.class) // 해당 예외 발생시 재시도 최대 2회까지 하도록 설정
                .retryLimit(2) // 재시도 횟수 2회
                .build();
    }

    @Bean
    public ItemProcessor itemProcessor() {
        return new RetryItemProcessor();
    }
    @Bean
    public ListItemReader<String> itemReader() {
        List<String> items = new ArrayList<>();
        for (int i=0; i<30; i++) {
            items.add(String.valueOf(i));
            log.info("ItemReader 아이템: {}", i);
        }
        return new ListItemReader<>(items);
    }
}
