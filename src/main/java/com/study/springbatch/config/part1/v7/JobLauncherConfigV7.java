package com.study.springbatch.config.part1.v7;

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


@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobLauncherConfigV7 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        log.info(">> job_v7");
        return jobBuilderFactory.get("job_v7")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v7")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info(">> step1_v7");
                        // 동기적 방식은 3초 딜레이 포함 모든 작업 완료된 후에 반환
                        // 비동기적 방식은 일단 먼저 JobExecution 반환 하고, 3초까지 클라이언트 입장에서는 안걸림
                        Thread.sleep(3000);
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_v7")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_v7");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
