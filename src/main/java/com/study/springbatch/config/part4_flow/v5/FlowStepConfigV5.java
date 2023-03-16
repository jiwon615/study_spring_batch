package com.study.springbatch.config.part4_flow.v5;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FlowStep은 Step이지만 안에 Flow를 품고 있는 Step 이다.
 * Flow가 실패시 -> flowstep의 상태가 실패 -> 최종적으로 Job 이 실패
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class FlowStepConfigV5 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v5");
        return jobBuilderFactory.get("batchJob_v5")
                .start(flowStep())
                .next(step2())

                .build();
    }

    @Bean
    public Step flowStep() {  // step인데, flow를 품고 있는 step -> FlowStep!!
        return stepBuilderFactory.get("flowStep")
                .flow(flow())
                .build();
    }

    public Flow flow() {
        FlowBuilder<Object> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(step1())
                .end();
        return null;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v5")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step1_v5");
                    throw new RuntimeException("step1 was FAILED!!");
//                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_v5")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_v5");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
