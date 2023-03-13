package com.study.springbatch.config.part4_flow.v3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 임의의 종료 코드로 설정하는 방법
 * 각 batchJob1과 batchJob2 실행해보자
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class BatchStatusExitStatusConfigV3 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

//    @Bean
//    public Job batchJob() {
//        log.info(">> batchJob_v3");
//        return jobBuilderFactory.get("batchJob_v3")
//                .start(step1())
//                .next(step2())
//                .build();
//    }

    @Bean
    public Job batchJob2() {
        log.info(">> batchJob2_v3");
        return jobBuilderFactory.get("batchJob_v3")
                .start(step3())
                .on("FAILED").to(step4())
                .end()
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step1_v3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_v3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_v3");
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3_v3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step3_v3");
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4_v3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step4_v3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5_v3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step5_v3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step6() {
        return stepBuilderFactory.get("step6_v3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step6_v3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
