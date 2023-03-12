package com.study.springbatch.config.v7;

import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 동기적 실행 VS 비동기적 실행 테스트 위해 HTTP 요청하는 컨트롤러 생성함
 */
@RestController
public class JobLauncherController {

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private BasicBatchConfigurer basicBatchConfigurer; // 비동기적 실행 테스트 위해 해당 클래스 추가

    // 동기적 싫행 (기본값 SyncTaskExecutor) -> step1에서 3초 이상 딜레이 모두 기다린 뒤 결과 나옴
    @PostMapping("/batch")
    public String syncLaunch(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
        return "sync batch completed";
    }

    // 비동기적 싫행 (SimpleAsyncTaskExecutor) -> 실행 즉시 바로 결과 나옴
    @PostMapping("/batch2")
    public String asyncLaunch(@RequestBody Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();

        SimpleJobLauncher jobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor()); // 비동기적 실행으로 설정!!! (기본은 내부적으로 new SyncTaskExecutor()) 함
        jobLauncher.run(job, jobParameters);
        return "async batch completed";
    }

    @Getter
    @Setter
    static class Member {
        private String id;
    }
}
