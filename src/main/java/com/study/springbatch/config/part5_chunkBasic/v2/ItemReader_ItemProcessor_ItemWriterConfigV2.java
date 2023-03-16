package com.study.springbatch.config.part5_chunkBasic.v2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 일반적으로는 ItemReader나 ItemWriter 의 경우, 보통 스프링배치가 기본적으로 제공하는 인터페이스를 구현하여 많이들 사용함
 * 그러나 학습의 목적을 위해 여기서는 직접  custom 하여 사용하는 방법을 학습해보자
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class ItemReader_ItemProcessor_ItemWriterConfigV2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v2");
        return jobBuilderFactory.get("batchJob_v2")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        log.info(">> step1_v2");
        return stepBuilderFactory.get("step1_v2")
                .<Customer, Customer>chunk(3)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader<Customer> itemReader() {
        return new CustomItemReader(
                Arrays.asList(
                new Customer("user1"),
                new Customer("user2"),
                new Customer("user3")
                )
        );
    }

    @Bean
    public ItemProcessor<? super Customer, ? extends Customer> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<? super Customer> itemWriter() {
        return new CustomItemWriter();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_v2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_v2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
