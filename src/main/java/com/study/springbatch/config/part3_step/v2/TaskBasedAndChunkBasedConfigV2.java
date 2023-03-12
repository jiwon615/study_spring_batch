package com.study.springbatch.config.part3_step.v2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 스프링 배치에서 Step의 실행 단위는 크게 2가지로 나누어짐
 * 1. Task 기반 (주고 tasklet 구현체를 만들어 사용)
 * 2. Chunk 기반
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class TaskBasedAndChunkBasedConfigV2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v2");
        return jobBuilderFactory.get("batchJob_v2")
                .incrementer(new RunIdIncrementer())
//                .start(taskStep())
                .start(chunkStep())
                .build();
    }


    @Bean
    public Step taskStep() {
        return stepBuilderFactory.get("taskStep")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info(">> taskStep");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step chunkStep() {
        log.info(">> chunkStep");
        return stepBuilderFactory.get("chunkStep")
                .<String, String> chunk(10)
                .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5")))
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return null;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> items) throws Exception {
                        items.forEach(System.out::println);
                    }
                })
                .build();
    }
}