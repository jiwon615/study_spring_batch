package com.study.springbatch.config.part9_repeat_skip_retry.v3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 진행되는 순서
 * 1)ItemReader에서 3일 때 Exception!!! (첫번쨰 exception)
 * 2) - ItemReader에서 발생했으니 이어서 다음 아이템으로 넘어감
 * 3)ItemProcessor에서 6일 때 Exception!!! (두번째 exception)
 * 4) - ItemProcessor에서 발생했으니 chunk 처음으로 돌아가서 ItemReader부터 시작
 *     (다만, 이미 ItemReader로 진행했었던 경우 cache가 남아있어서 읽어놨던 데이터를 ItemProcessor로 보내줌)
 * 5) ItemProcessor에서 예외터졌던 아이템 skip 하고 데이터 처리진행 ...
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class SkipConfigV3 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v3");
        return jobBuilderFactory.get("batchJob_v3")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v3")
                .<String, String>chunk(5)
                .reader(new ItemReader<>() {
                    int i = 0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        if (i == 3) {
                            throw new CustomSkippableException("ItemReader에서 발생한 예외는 바로 skip 하고 다음 아이템 읽음");
                        }
                        log.info("ItemReader 아이템 : {}", i);
                        return i > 20 ? null : String.valueOf(i);
                    }
                })
                .processor(itemProcess())
                .writer(itemWriter())
                .faultTolerant()
                .noSkip(CustomNoSkippableException.class) // 해당 예외발생시 skip 하지 않고 바로 step 종료
                .skip(CustomSkippableException.class)
                .skipLimit(2)
                .build();
    }

    @Bean
    public ItemProcessor<? super String, String> itemProcess() {
        return new SkipItemProcessor();
    }

    @Bean
    public ItemWriter<? super String> itemWriter() {
        return new SkipItemWriter();
    }
}
