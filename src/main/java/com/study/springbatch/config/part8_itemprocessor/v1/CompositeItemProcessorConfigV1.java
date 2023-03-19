package com.study.springbatch.config.part8_itemprocessor.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class CompositeItemProcessorConfigV1 {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() throws Exception {
        log.info(">> batchJob_v1");
        return jobBuilderFactory.get("batchJob_v1")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1_v1")
                .<String, String>chunk(10)
                .reader(new ItemReader<>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        return i > 10 ? null : "item";  // 총 10개의 item을 읽음
                    }
                })
                .processor(customItemProcessor())
                .writer(items -> log.info(items.toString()))
                .build();
    }

    @Bean
    public ItemProcessor<? super String, String> customItemProcessor() {
        List itemProcessors = new ArrayList<>();
        itemProcessors.add(new CustomItemProcessor1()); // 읽은 item을 uppercase + idx 붙이는 프로세서
        itemProcessors.add(new CustomItemProcessor2()); // 또다서 idx를 추가하는 프로세서
        return new CompositeItemProcessorBuilder<>()
                .delegates(itemProcessors)
                .build();
    }
}
