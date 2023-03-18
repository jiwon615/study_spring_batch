package com.study.springbatch.config.part6_chunk_itemReader.v5;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class DB_JdbcCursorItemReaderConfigV5 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private int chunkSize = 2;
    private final DataSource dataSource;

    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v5");
        return jobBuilderFactory.get("batchJob_v5")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v5")
                .<Customer, Customer>chunk(chunkSize)
                .reader(customerItemReader())
                .writer(customerItemWriter())
                .build();
    }

    @Bean
    public ItemReader<Customer> customerItemReader() {
        return new JdbcCursorItemReaderBuilder<Customer>()
                .name("jdbcCursorItemReader")
                .fetchSize(chunkSize)
                .sql("select id, firstName, lastName, birthdate from customer where firstName like ? order by id")
                .beanRowMapper(Customer.class)
                .queryArguments("%0")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public ItemWriter<Customer> customerItemWriter() {
        return customers -> {
            for (Customer customer : customers) {
                log.info("customer id/firstName={}/{}", customer.getId(), customer.getFirstName());
            }
        };
    }
}
