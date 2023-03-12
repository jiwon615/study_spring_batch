package com.study.springbatch.config.part1.v6;

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
public class JobRepositoryConfigV6 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobRepositoryListener jobRepositoryListener;

    @Bean
    public Job job() {
        log.info(">> job_v6");
        return jobBuilderFactory.get("job_v6")
                .start(step1())
                .next(step2())
                .listener(jobRepositoryListener) // 빈으로 만들어준 리스너 등록!
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v6")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info(">> step1_v6");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_v6")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_v6");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
