package com.study.springbatch.config.part2_job.v3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * - SimpeJob은 배치job을 구성하는 가장 기본이 되는 표준 구현체
 * - SimpleJob은 Step으로만 구성 가능
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class SimpleJobConfigV3 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        log.info(">> job_v3");
        return jobBuilderFactory.get("job_v3")
                .start(step1())
                .next(step2())
                .next(step3())
                /**
                 * JobParametersValidator 인터페이스 구현하는 custom 클래스 직접 만들어서 검증 로직 추가 가능 (ex .validator(new CustomParamsValidator()) )
                 * (여기선 일단 익명클래스로 구현함)
                 */
//                .validator(new JobParametersValidator() {
//                    @Override
//                    public void validate(JobParameters parameters) throws JobParametersInvalidException {
//                        log.info("===validate()===");
//                        if (parameters.getString("requiredTestKey1") == null) {
//                            throw new JobParametersInvalidException("requiredTestKey1 parameters is not found!!");
//                        }
//                    }
//                })
                /**
                 * 스프링 배치가 기본적으로 제공하는 validator 클래스인 DefaultJobParametersValidator 클래스 사용해서 검증
                 * .validator(new DefaultJobParametersValidator(String[] requiredKeys, String[] optionalKeys))
                 */
                .validator(new DefaultJobParametersValidator(new String[]{"requiredTestKey1", "requiredTestKey2"}, new String[]{"optionalTestKey"}))
//                .preventRestart() // 활성화 시키면, job fail 후 동일한 job 재실행 못하게 막는 것 (기본적으로는 fail해도 재실행 가능하게 되어있음)

                /**
                 * 직접 구현한 CustomJobParametersIncrementer 사용
                 */
//                .incrementer(new CustomJobParametersIncrementer())
                /**
                 * 스프링 배치가 기본적으로 제공하는RunIdIncrementer 사용 (강사는 보통 이거로 사용한다고 함)
                  */
                .incrementer(new RunIdIncrementer())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {

                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {

                    }
                })
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
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3_v3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step3_v3");
//                    chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);
//                    contribution.setExitStatus(ExitStatus.STOPPED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
