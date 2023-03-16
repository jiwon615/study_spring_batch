package com.study.springbatch.config.part5_chunkBasic.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 청크 개념이해를 돕기 위해 기본적인 사용 ->chunk()
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class ChunkConfigV1 {

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
                .<String, String>chunk(5) // <inputType, outputTpe>(청크사이즈)
                .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5")))
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        Thread.sleep(300);
                        log.info("item = {}", item);
                        return "my" + item;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> items) throws Exception {
                        Thread.sleep(300);
                        log.info("items = {}", items);
                    }
                })

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