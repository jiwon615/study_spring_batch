package com.study.springbatch.config.part3_step.v4;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.core.step.job.JobParametersExtractor;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JobStep 이해를 돕기 위해 Job에 속하는 step 중 외부의 Job(childJob)을 포함하는 Step을 구성함
 *
 * step1에서 RuntimeException 발생시키면 -> childJob내의 step1이 fail된거라 -> childJob, ParentJob 모두 FAILED
 * step2에서 RuntimeException 발생시키면 ->  parentJob에서 fail, childJob에서는 성공 했기에 별도의 결과로 저장됨
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class JobStepConfigV4 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parentJob() {
        log.info(">> parentJob");
        return jobBuilderFactory.get("parentJob")
                .incrementer(new RunIdIncrementer())
                .start(jobStep(null))
                .next(step2())
                .build();
    }

    @Bean
    public Step jobStep(JobLauncher jobLauncher) { // JobLauncher를 DI 받음
        log.info(">> jobStep");
        return stepBuilderFactory.get("jobStep")
                .job(childJob())
                .launcher(jobLauncher)
                .parametersExtractor(jobParametersExtractor())
                .listener(new StepExecutionListener() {
                    // step이 실행하기 전 수행할 수 있는 사전 작업 메소드
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        stepExecution.getExecutionContext().putString("name", "user1"); // EXECUTION_PARAMS 테이블에  "name":"user" 추가
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .build();
    }

    // DefaultJobParametersExtractor는 EXECUTION_CONTEXT 테이블에 저장된 key를 기준으로 값을 가져올 수 있음
    private JobParametersExtractor jobParametersExtractor() {
        DefaultJobParametersExtractor extractor = new DefaultJobParametersExtractor();
        extractor.setKeys(new String[]{"name"});
        return extractor;
    }

    @Bean
    public Job childJob() {
        log.info(">> childJob");
        return jobBuilderFactory.get("childJob")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        log.info(">> step1_v4");
        return stepBuilderFactory.get("step1_v4")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        throw new RuntimeException("step1 실행 FAILED!!!!");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step step2() {
        log.info(">> step2_v4");
        return stepBuilderFactory.get("step2_v4")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        throw new RuntimeException("step2 실행 FAILED!!!!");
//                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}