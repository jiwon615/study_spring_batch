package com.study.springbatch.config.part6_chunk_itemReader.v9;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class ItemReaderAdapterConfigV9 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v9");
        return jobBuilderFactory.get("batchJob_v9")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v9")
                .<String, String>chunk(10)
                .reader(customerItemReader())
                .writer(customerItemWriter())
                .build();
    }

    @Bean
    public ItemReader<String> customerItemReader() {
        ItemReaderAdapter<String> reader = new ItemReaderAdapter<>();
        reader.setTargetObject(customService());  // 타겟 object
        reader.setTargetMethod("customRead"); // 타겟 메소드명
        return reader;
    }

    @Bean
    public CustomerService customService() {
        return new CustomerService();
    }

    @Bean
    public ItemWriter<String> customerItemWriter() {
        return items -> {
            log.info("items={}", items);
        };
    }
}
