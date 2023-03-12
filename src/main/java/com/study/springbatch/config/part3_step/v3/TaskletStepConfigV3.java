package com.study.springbatch.config.part3_step.v3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * TaskletStep이 제공하는 다양한 API 활용
 * - tasklet(true)
 * - startLimit(size)
 * - allowStartIfComplete(true)
 * - listener(listener)
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class TaskletStepConfigV3 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v3");
        return jobBuilderFactory.get("batchJob_v3")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .build();
    }

    // 직접 tasklet 구현 클래스 만들어 사용
    @Bean
    public Step step1() {
        log.info(">> step1_v3");
        return stepBuilderFactory.get("step1_v3")
                .tasklet(new CustomTasklet())
                .allowStartIfComplete(true) // 해당 step 성공했을지라도 job 재실행시 step1은 계속 실행됨
                .build();
    }

    // tasklet 익명클래스로 구현
    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_v3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info(">> step2_v3");
                        throw new RuntimeException("step2 was 실패!!!");
//                        return RepeatStatus.FINISHED;
                    }
                })
                .startLimit(3) // 3회까지 실행되고, 4회 부터는 StartLimitExceededException 발생 및 STEP_EXECUTION 테이블에도 안쌓임
                .build();
    }
}