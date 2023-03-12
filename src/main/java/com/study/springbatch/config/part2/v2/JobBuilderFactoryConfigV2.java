package com.study.springbatch.config.part2.v2;

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
 * JobBuilderFactory 학습을 하며,
 * SimpleJobBuilder (ex. job1이 생성)과 FlowJobBuiler(ex. job2이 생성)의 차이를 학습
 *
 * Flow 객체 생성하는 부분을 아래 코드로 학습해봄
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobBuilderFactoryConfigV2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

//    @Bean
//    public Job job1() {
//        log.info(">> job1_V2");
//        return jobBuilderFactory.get("job1_V2")
//                .start(step1())
//                .next(step2())
//                .build();
//    }

    @Bean
    public Job job2() {
        log.info(">> job2_V2");
        return jobBuilderFactory.get("job2_V2")
                .start(flow())
                .next(step5())
                .end()// flow 객체는 end() 를 붙여줘야 함
                .build();
    }

    @Bean
    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow_v2");
        flowBuilder.start(step3())
                .next(step4())
                .end(); // flow 객체는 end() 를 붙여줘야 함

        return flowBuilder.build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_V2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step1_V2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_V2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_V2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3_V2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step3_V2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4_V2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step4_V2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5_V2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step5_V2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}