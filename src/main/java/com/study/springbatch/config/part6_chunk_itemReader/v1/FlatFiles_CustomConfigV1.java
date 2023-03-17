package com.study.springbatch.config.part6_chunk_itemReader.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

/**
 * ItemReader 의 FlatFileItemReader 학습
 * - FlatFieldItemReader 는 파일을 읽는 두가지 방식 존재
 *  1. DelimitedLineTokenizer - 구분자방식
 *  2. FixedLengthTokenizer   -고정길이 방식
 *
 *  - 여기서는 직접 구현체 만들어서 활용해보자
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class FlatFiles_CustomConfigV1 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v1");
        return jobBuilderFactory.get("batchJob_v1")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v1")
                .<String, String>chunk(5)
                .reader(itemReader())
                .writer(new ItemWriter() {
                    @Override
                    public void write(List items) throws Exception {
                        log.info("items = {}" + items);
                    }
                })
                .build();
    }

    @Bean
    public ItemReader itemReader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("/customer.csv")); // setResource(): 읽어야할 리소스 설정

        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer()); // LineTokenizer 객체 설정
        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper()); // FieldSetMapper 객체 설정

        itemReader.setLineMapper(lineMapper); // LineMapper 객체 설정
        itemReader.setLinesToSkip(1); // customer.csv파일의 첫번째 라인은 skip
        return itemReader;
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_v1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_v1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
