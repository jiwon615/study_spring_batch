package com.study.springbatch.config.part1.v5;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ExecutionContext 이해를 돕기 위해
 *  Step간 서로 공유되는지
 *  Job 간 서로 공유되는지 테스트
 *  - 처음엔 step3에서 test 날거고, 그 상태에서 다시 재실행하면 성공하게됨(공유된 값으로 인해 null 값 아니게 되기 때문) -> 디비 데이터 확인해보자!
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ExecutionContextConfigV5 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ExecutionTasklet1 executionTasklet1;
    private final ExecutionTasklet2 executionTasklet2;
    private final ExecutionTasklet3 executionTasklet3;
    private final ExecutionTasklet4 executionTasklet4;

    @Bean
    public Job job() {
        log.info(">> job_V5");
        return jobBuilderFactory.get("job_V5")
                .start(step1())
                .next(step2())
                .next(step3())
                .next(step4())
                .build();
    }

    @Bean
    public Step step1() {
        log.info(">> step1_V5");
        return stepBuilderFactory.get("step1_V5")
                .tasklet(executionTasklet1)
                .build();
    }

    @Bean
    public Step step2() {
        log.info(">> step2_V5");
        return stepBuilderFactory.get("step2_V5")
                .tasklet(executionTasklet2)
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3_V5")
                .tasklet(executionTasklet3)
                .build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4_V5")
                .tasklet(executionTasklet4)
                .build();
    }
}
