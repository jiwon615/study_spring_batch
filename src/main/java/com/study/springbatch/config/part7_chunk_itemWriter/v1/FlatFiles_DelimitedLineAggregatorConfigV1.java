package com.study.springbatch.config.part7_chunk_itemWriter.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Arrays;
import java.util.List;

/**
 * ItemReader 의 FlatFileItemWriter 학습
 * - FlatFieldItemWriter 는 파일을 합치는(aggregate) 두가지 방식 존재
 * 1. DelimitedLineAggregator - 전달된 배열을 '구분자'로 구분하여 1개의 문자열로 합침
 * 2. FormattedLineAggregator = 전달된 배열을 '고정길이'로 구분하여 1개의 문자열로 합침
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class FlatFiles_DelimitedLineAggregatorConfigV1 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v1");
        return jobBuilderFactory.get("batchJob_v1")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        log.info(">> step1_v1");
        return stepBuilderFactory.get("step1_v1")
                .<Customer, Customer>chunk(10)
                .reader(customItemReader())
//                .writer(customItemWriter_delimited())
                .writer(customItemWriter_formatted())
                .build();
    }

    @Bean
    public ItemReader<? extends Customer> customItemReader() {
        log.info(">> customItemReader()");
        List<Customer> customers = Arrays.asList(
                new Customer(1, "jung ji won1", 28),
                new Customer(2, "jung ji won2", 29),
                new Customer(3, "jung ji won3", 30)
                );

        ListItemReader<Customer> reader = new ListItemReader<>(customers); // 읽고자 하는 데이터리스트
        return reader;
    }

    /**
     * 1. DelimitedLineAggregator 활용 - ex) .delimited(), .delimiter(구분자)
     */
    @Bean
    public ItemWriter<? super Customer> customItemWriter_delimited() {
        log.info(">> customItemWriter_delimited()");
        return new FlatFileItemWriterBuilder<Customer>()
                .name("flatFileWriter")
                // 해당 위치의 파일에 쓰고자함 (파일명 없으면 알아서 해당 위치에서 생성)
                .resource(new FileSystemResource("/Users/jiwon/dev-study/inflearn-spring-batch/springbatch/src/main/resources/part7/customerV1.txt"))
                .append(true) // true 주게되면 기존에 데이터 존재하면 그 아래에 이어 붙이기 (기본은 false -> 즉 덮어씌움)
                .delimited().delimiter("|") // 해당 구분자로 이어붙이게 하라!
                .names(new String[]{"id", "name", "age"}) // 쓰고자 하는 Customer 객체의 필드를 넣어야함 (추가안할시 해당 필드의 데이터는 안읽음)
                .build();
    }

    /**
     * 2. FormattedLineAggregator 활용 - ex) .formatted().format(String format)
     */
    @Bean
    public ItemWriter<? super Customer> customItemWriter_formatted() {
        log.info(">> customItemWriter_formatted()");
        return new FlatFileItemWriterBuilder<Customer>()
                .name("flatFileWriter")
                // 해당 위치의 파일에 쓰고자함 (파일명 없으면 알아서 해당 위치에서 생성)
                .resource(new FileSystemResource("/Users/jiwon/dev-study/inflearn-spring-batch/springbatch/src/main/resources/part7/customer_formattedV1.txt"))
                .append(true)
                .formatted().format("%-2d%-13s%-2d")// id(~2자까지), name(12자), age(2자)
                .names(new String[]{"id", "name", "age"}) // 쓰고자 하는 Customer 객체의 필드를 넣어야함 (추가안할시 해당 필드의 데이터는 안읽음)
                .build();
    }
}
