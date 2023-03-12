package com.study.springbatch.config.part1_domain.v3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JobExecution의 실행결과가 COMPLETED -> JOB_INSTANCE 1개 생성, JOB_EXECUTION 1개 생성
 * JobExecution의 실행결과가 FAILED -> JOB_INSTANCE 수는 그대로, JOB_EXECUTION만 FAIL 했다는 상태정보를 가진 ROW 추가됨 (성공할 때까지 추가됨)
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobExecutionConfigV3 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        log.info(">> job_V3");
        return jobBuilderFactory.get("job_V3")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_V3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step1_V3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_V3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_V3");
//                    throw new RuntimeException(">> step2_V3 FAILED!!");   // 활성화 시켜서 일부러 실행결과 FAILED 내도록 함
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
