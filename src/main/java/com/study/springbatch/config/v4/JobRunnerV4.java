package com.study.springbatch.config.v4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 성공시 -> 1 JobInstance, 1 JobExecution, 3 StepExecution (총 3개의 steps 등록했으므로)
 * 실패시(step2에서 실패) ->
 */
@Component
@Slf4j
public class JobRunnerV4 implements ApplicationRunner { // ApplicationRunner는 스프링 부트 초기화 및 완료 된 직후 실행

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("===JobRunner의 run()===");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
