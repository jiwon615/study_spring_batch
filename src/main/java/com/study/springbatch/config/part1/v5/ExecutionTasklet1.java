package com.study.springbatch.config.part1.v5;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExecutionTasklet1 implements Tasklet {


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("=== ExecutionTasklet1.execute() 실행===");
        ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

        String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

        // 최초 실행시 jobNamer과 stepName 모두 null
        if (jobExecutionContext.get("jobName") == null) {
            jobExecutionContext.put("jobName", jobName); // 값을 넣으면, Job의 Step간 서로 공유됨
        }

        if (stepExecutionContext.get("stepName") == null) {
            stepExecutionContext.put("stepName", stepName); // 값을 넣어도, Step 간 서로 공유 안됨
        }

        log.info("jobName : ", jobName);  // job_V5
        log.info("stepName : ", stepName); // step1_V5
        return RepeatStatus.FINISHED;
    }
}
