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
public class ExecutionTasklet3 implements Tasklet {


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("=== ExecutionTasklet3.execute() 실행===");
        ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        Object name = jobExecutionContext.get("name"); // 최초 실행시 존재하지 않는 임의의 name이라는 key를 조회 -> 두번째부터는 디비에 저장되어있기에 name에 user1이라는 값이 있을 것

        // 처음 실행하면 null 이라 RunTimeException! -> 하지만 name:"user1"를 저장했기 때문에, 두 번째 시도에서는 null이 아니라 성공할 것임
        if (name == null) {
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("name", "user1");
            throw new RuntimeException("===step 3 was failed!!===");
        }
        return RepeatStatus.FINISHED;
    }
}
