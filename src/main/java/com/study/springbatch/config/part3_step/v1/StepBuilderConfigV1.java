package com.study.springbatch.config.part3_step.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * StepBuilderFactory은 StepBuilder을 생성하고, StepBuilder는 Step을 구성하는 조건에 따라 5개의 하위빌더클래스 생성
 *
 * ex) step1 : tasklet() -> TaskletStepBuilder 생성 -> TaskletStep 생성
 * ex) step2 : chunk(chunkSize) -> SimpleStepBuilder 생성 -> SimpleStep 생성
 * ex) step3 : partitioner(step) -> PartitionStepBuilder 생성 -> PartitionStep 생성
 * ex) step4 : job(job) -> JobStepBuilder 생성 -> JobStep 생성
 * ex) step5 : flow(flow) -> FlowStepBuilder 생성 -> FlowStep 생성
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class StepBuilderConfigV1 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v1");
        return jobBuilderFactory.get("batchJob_v1")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step1_v1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        log.info(">> step2_v1");
        return stepBuilderFactory.get("step2_v1")
                .<String, String> chunk(3)
                .reader(new ItemReader<String>() {
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        return null;
                    }
                })
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return null;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> items) throws Exception {

                    }
                })
                .build();
    }
    
    @Bean
    public Step step3() {
        log.info(">> step3_v1");
        return stepBuilderFactory.get("step3_v1")
                .partitioner(step1())
                .gridSize(2)
                .build();
    }

    @Bean
    public Step step4() {
        log.info(">> step4_v1");
        return stepBuilderFactory.get("step4_v1")
                .job(job())
                .build();
    }

    @Bean
    public Step step5() {
        log.info(">> step5_v1");
        return stepBuilderFactory.get("step5_v1")
                .flow(flow())
                .build();
    }

    @Bean
    public Job job() {
        log.info(">> 내부 job_v1");
        return this.jobBuilderFactory.get("job_v1")
                .start(step1())
                .start(step2())
                .start(step3())
                .build();
    }

    @Bean
    public Flow flow() {
        log.info(">> flow_v1");
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow_v1");
        flowBuilder.start(step2()).end();
        return flowBuilder.build();
    }
}