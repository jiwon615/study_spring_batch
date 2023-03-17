package com.study.springbatch.config.part6_chunk_itemReader.v2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

/**
 * ItemReader 의 FlatFileItemReader 학습
 * -v1에서는 직접 FieldSetMapper와 LineMapper을 구현했지만,
 * 여기서는 스프링 배치에서 제공하는 구현체를 사용함 (훨씬 더 간편하니 이 방식을 사용하자)
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class FlatFilesConfigV2 {

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
    @JobScope
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
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFile")
                .resource(new ClassPathResource("/customer.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>()) // 직접구현하기보다, 제공되는걸 쓰는게 훨씬 간편!
                .targetType(Customer.class) // 대신 타겟클래스 지정해야함
                .linesToSkip(1) // customer.csv파일의 첫번째 라인은 skip
                .delimited().delimiter(",")
                .names("name", "age", "year")
                .build();
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
