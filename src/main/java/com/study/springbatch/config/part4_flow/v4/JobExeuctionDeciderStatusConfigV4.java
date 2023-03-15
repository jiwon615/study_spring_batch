package com.study.springbatch.config.part4_flow.v4;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Transition 사용해서 FlowJob을 구성할 수도 있지만,
 * 여기서는 CustomDecider를 통해 decide() 안에서 하고자 하는 조건적인 흐름들을 제어할 수 있음
 * - 즉 여기서는 JobExecutionDecider를 활용한 JobFlow 구성
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class JobExeuctionDeciderStatusConfigV4 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    

    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v4");
        return jobBuilderFactory.get("batchJob_v4")
                .start(step())
                .next(decider()) // step() 이 성공적으로 완료하면 decider() 를 실행하라
                .from(decider()).on("ODD").to(oddStep()) // decider() 의 실행 후 상태가 ODD 이면 oddStep() 을 실행하라
                .from(decider()).on("EVEN").to(evenStep()) // decider() 의 실행 후 상태가 EVEN 이면 evenStep() 을 실행하라
                .end()
                .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new CustomDecider();
    }


    @Bean
    public Step step() {
        return stepBuilderFactory.get("step_v4")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step_v4");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep_v4")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> evenStep_v4");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep_v4")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> oddStep_v4");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
