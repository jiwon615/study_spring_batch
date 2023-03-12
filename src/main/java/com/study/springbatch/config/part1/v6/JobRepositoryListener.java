package com.study.springbatch.config.part1.v6;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobRepositoryListener implements JobExecutionListener {

    @Autowired
    private JobRepository jobRepository;  // @EnableBatchProcessing 시 자동으로 생성되는 빈

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    // Job이 수행된 이후에 실행
    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("===JobRepositoryListener.afterJob()===");
        String jobName = jobExecution.getJobInstance().getJobName();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", "20230309").toJobParameters();

        // getLastJobExecution, getLastStepExecution, getStepExecutionCount 등 사용 가능
        // BATCH_JOB_EXECUTION_PARAMS 디비에 저장된 값을 가져와서 아래처럼 조회해볼 수 있음 (디비에 있는 값의 jobParameters를 넣어주면 됨)
        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
        if (lastJobExecution != null) {
            for (StepExecution execution : lastJobExecution.getStepExecutions()) {
                BatchStatus status = execution.getStatus();
                ExitStatus exitStatus = execution.getExitStatus();
                String stepName = execution.getStepName();
                log.info("status: {}", status.toString());
                log.info("exitStatus: {}", exitStatus.getExitCode());
                log.info("stepName: {}", stepName);
            }
        }
    }
}
