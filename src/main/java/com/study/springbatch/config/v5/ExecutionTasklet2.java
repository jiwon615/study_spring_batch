package com.study.springbatch.config.v5;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExecutionTasklet2 implements Tasklet {


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("=== ExecutionTasklet2.execute() 실행===");
        ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

        // tasklet1에서부터 set 해줬기 때문에 job간에, 그리고 step 간에 공유 되는지 확인
        log.info("jobName : ", jobExecutionContext.get("jobName")); // job_V5 (공유 받음)
        log.info("stepName : ", stepExecutionContext.get("stepName")); // null (공유 안받음)

        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

        if (stepExecutionContext.get("stepName") == null) {
            stepExecutionContext.put("stepName", stepName); // 다시 저장
        }

        return RepeatStatus.FINISHED;
    }
}
