package com.study.springbatch.config.v2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobParametersConfigV2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        log.info(">> job_V2");
        return jobBuilderFactory.get("job_V2")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_V2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step1_V2");

                    // contribution VS chunkContext
                    JobParameters parameters = contribution.getStepExecution().getJobExecution().getJobParameters();
                    log.info("getString name: {}", parameters.getString("name"));
                    log.info("getLong seq: {}", parameters.getLong("seq"));
                    log.info("getString date: {}", parameters.getDate("date"));
                    log.info("getDouble age: {}", parameters.getDouble("age"));

                    Map<String, Object> parameters2 = chunkContext.getStepContext().getJobParameters();

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_V2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_V2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
