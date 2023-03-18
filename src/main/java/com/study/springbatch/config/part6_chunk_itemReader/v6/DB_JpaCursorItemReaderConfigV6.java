package com.study.springbatch.config.part6_chunk_itemReader.v6;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class DB_JpaCursorItemReaderConfigV6 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

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
                .<Customer, Customer>chunk(5)
                .reader(customerItemReader())
                .writer(customerItemWriter())
                .build();
    }

    @Bean
    public ItemReader<? extends Customer> customerItemReader() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstname", "%0");

        return new JpaCursorItemReaderBuilder<Customer>()
                .name("jpaCursorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Customer c where firstname like :firstname")
                .parameterValues(parameters)
                .build();
    }

    @Bean
    public ItemWriter<Customer> customerItemWriter() {
        return customers -> {
            for (Customer customer : customers) {
                log.info("customer id/firstname={}/{}", customer.getId(), customer.getFirstname());
            }
        };
    }
}
