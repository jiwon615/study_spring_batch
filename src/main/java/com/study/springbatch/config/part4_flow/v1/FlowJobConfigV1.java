package com.study.springbatch.config.part4_flow.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class FlowJobConfigV1 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v1");
        // 만약 step 1 성공 -> step3로 이동
        // 만약 step 1 실패 -> step2로 이동 후
        // end()로 종료로 FlowJobBuilder 반환
        return jobBuilderFactory.get("batchJob_v1")
                .start(step1())
                .on("COMPLETED").to(step3())
                .from(step1())
                .on("FAILED").to(step2())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step1_v1");
                    throw new RuntimeException("step1 is FAILED!!");
//                    return RepeatStatus.FINISHED;
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

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3_v1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step3_v1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
