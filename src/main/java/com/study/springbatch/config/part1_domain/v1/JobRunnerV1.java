package com.study.springbatch.config.part1_domain.v1;

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
 * JobParameter와 Job key가 같은 경우와 같지 않은 경우 테스트 위한 config
 * user1, user2 등록해봄( 동일한 key,name 중복으로 BATCH_JOB_EXECUTION_PARAMSd에 넣지 못함)
 */
@Component
@Slf4j
public class JobRunnerV1 implements ApplicationRunner { // ApplicationRunner는 스프링 부트 초기화 및 완료 된 직후 실행

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("===JobRunner의 run()===");
        JobParameters jobParameters = new JobParametersBuilder()
                // 동일한 jobNmae(ex. job_V1) + jobKey(jobParameter의 해시값)로는 JobInstance 저장 불가
                .addString("name", "user2")
//                .addString("name", "user1")
                .toJobParameters();

        jobLauncher.run(job, jobParameters); // jobLauncher를 통해 job을 수행
    }
}
