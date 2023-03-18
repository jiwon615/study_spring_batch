package com.study.springbatch.config.part6_chunk_itemReader.v8;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class DB_JpaPagingItemReaderConfigV8 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v6");
        return jobBuilderFactory.get("batchJob_v6")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v6")
                .<Customer_p6v8, Customer_p6v8>chunk(chunkSize)
                .reader(customerItemReader())
                .writer(customerItemWriter())
                .build();
    }

    @Bean
    public ItemReader<? extends Customer_p6v8> customerItemReader() {
        return new JpaPagingItemReaderBuilder<Customer_p6v8>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("select c from Customer_p6v8 c join fetch c.address")  // 쿼리 n+1 문제로 수많은 쿼리 나오는 것 방지위해 'join fetch c.address' 추가!!
                .build();
    }

    @Bean
    public ItemWriter<Customer_p6v8> customerItemWriter() {
        return customers -> {
            for (Customer_p6v8 customer : customers) {
                log.info("customer={}", customer.getAddress().getLocation()); // customer_p6v8, address 테이블에 데이터 있어야 출력 됨
            }
        };
    }
}
