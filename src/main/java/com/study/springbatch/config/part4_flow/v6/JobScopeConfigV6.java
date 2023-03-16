package com.study.springbatch.config.part4_flow.v6;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@RequiredArgsConstructor
@Configuration
@Slf4j
public class JobScopeConfigV6 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v5");
        return jobBuilderFactory.get("batchJob_v5")
                .start(step1(null))
                .next(step2())
                .listener(new CustomJobListener())
                .build();
    }

    @Bean
    @JobScope
    // Step이 생성되는 시점에 jobParameters 값을 표현식을 통해 동적인 시점에 값 세팅 가능
    public Step step1(@Value("#{jobParameters['message']}") String message) {
        log.info("message 값 = {}", message);
        return stepBuilderFactory.get("step1_v5")
                .tasklet(tasklet1(null))
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_v5")
                .tasklet(tasklet2(null))
                .listener(new CustomStepListener())
                .build();
    }

    @Bean
    @StepScope
    // CustomJobListener에서 name의 값 미리 세팅해두어서 실행시점에 동적으로 참조하여 name 값 세팅 가능
    public Tasklet tasklet1(@Value("#{jobExecutionContext['name']}") String name) {
        log.info("name 값 = {}", name);
        return (contribution, chunkContext) -> {
            log.info(">> tasklet1");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @StepScope
    // CustomStepListener에서 name2의 값 미리 세팅해두어서 실행시점에 동적으로 참조하여 name2 값 세팅 가능
    public Tasklet tasklet2(@Value("#{stepExecutionContext['name2']}") String name2) {
        log.info("name2 = {}", name2);
        return (contribution, chunkContext) -> {
            log.info(">> tasklet2");
            return RepeatStatus.FINISHED;
        };
    }
}
