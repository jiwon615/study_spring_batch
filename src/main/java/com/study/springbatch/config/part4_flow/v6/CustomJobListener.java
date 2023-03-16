package com.study.springbatch.config.part4_flow.v6;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class CustomJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("==CustomJobListener beforeJob()==");
        jobExecution.getExecutionContext().putString("name", "user");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
