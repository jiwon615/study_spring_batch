package com.study.springbatch.config.part1.v4;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Step 여러개 실행하여 성공했을 때의 케이스, Step2 실패했을 때의 케이스
 * - db 어떻게 데이터 row 생기는지 확인하는 예제 (JobInstance, JobExecution, StepExecution)
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class StepExecutionConfigV4 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        log.info(">> job_V4");
        return jobBuilderFactory.get("job_V4")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    /**
     *  직접 tasklet을 만들어서 사용하는 예제
     */
    @Bean
    public Step step1() {
        log.info(">> step1_V4");
        return stepBuilderFactory.get("step1_V4")
                .tasklet(new CustomTasklet())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_V4")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_V4");
//                    throw new RuntimeException(">> step2_V4 FAILED!!");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3_V4")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        // contribution을 통해 객체를 타고타고 올라가서 해당 Job에 대한 정보까지도 알아볼 수 있음
                        String jobName = contribution.getStepExecution().getJobExecution().getJobInstance().getJobName();

                        log.info(">> step3_V4");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
