package com.study.springbatch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;  // Job을 생성하는 빌더 팩토리
    private final StepBuilderFactory stepBuilderFactory;     // Step을 생성하는 빌더 팩토리

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob") // helloJob 이름으로 Job 생성
                .start(helloStep1())
                .next(helloStep2())
                .build();
    }

    @Bean
    public Step helloStep1() {
        log.info("==step1==");
        return stepBuilderFactory.get("helloStep1") // heooStep1 이름으로 Step 생성
                .tasklet((contribution, chunkContext) -> { // tasklet: Step안에서 단일 테스크로 수행되는 로직 구현
                    log.info("=================");
                    log.info(">>> Hello Spring Batch");
                    log.info("=================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step helloStep2() {
        log.info("==step2==");
        return stepBuilderFactory.get("helloStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("=================");
                    log.info(">>> Hello Spring Batch");
                    log.info("=================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
