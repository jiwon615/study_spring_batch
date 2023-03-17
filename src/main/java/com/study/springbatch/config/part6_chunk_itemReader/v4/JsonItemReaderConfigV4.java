package com.study.springbatch.config.part6_chunk_itemReader.v4;

import com.study.springbatch.config.part6_chunk_itemReader.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class JsonItemReaderConfigV4 {

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
                .<Customer, Customer>chunk(3)
                .reader(customerItemReader())
                .writer(customerItemWriter())
                .build();
    }

    @Bean
    public ItemReader<? extends Customer> customerItemReader() {
        return new JsonItemReaderBuilder<Customer>()
                .name("jsonReader")
                .resource(new ClassPathResource("customerPart6_v4.json"))
                .jsonObjectReader(new JacksonJsonObjectReader<>(Customer.class))
                .build();
    }

    @Bean
    public ItemWriter<Customer> customerItemWriter() {
        return items -> {
            for (Customer item : items) {
                log.info("item={}", item.toString());
            }
        };
    }
}
