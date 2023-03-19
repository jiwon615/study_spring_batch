package com.study.springbatch.config.part9_repeat_skip_retry.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class RepeatConfigV1 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v1");
        return jobBuilderFactory.get("batchJob_v1")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v1")
                .<String, String>chunk(5)
                .reader(new ItemReader<String>() {
                    int i = 0;

                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        log.info(">>read(); i={}", i);
                        return i > 3 ? null : "item" + i;
                    }
                })
                .processor(new ItemProcessor<String, String>() {
                    RepeatTemplate repeatTemplate = new RepeatTemplate();

                    @Override
                    public String process(String item) throws Exception {

                        /**
                         * CompletionPolicy 반복제어 사용 예제
                         */
                        // 아래처럼 종료정책 지정 안하면 계속 무한 반복하게 됨 (여러개 설정 시 맨 마지막에 지정한 종료정책이 활성화됨)
//                        repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(3)); // 3번까지 반복하고 종료
//                        repeatTemplate.setCompletionPolicy(new TimeoutTerminationPolicy(3000)); // 3초동안 반복하고 종료

                        // CompositeCompletionPolicy : 만약 종료정책을 두개 이상 사용하고 싶고, 설정한 여러 종류정책중 먼저 해당되는게 하나라도 있으면 바로 반복문 종료하고자 할 때 사용
                        CompositeCompletionPolicy compositeCompletionPolicy = new CompositeCompletionPolicy();
                        CompletionPolicy[] completionPolicies = new CompletionPolicy[]{
                                new SimpleCompletionPolicy(3),
                                new TimeoutTerminationPolicy(3000)
                        };
                        compositeCompletionPolicy.setPolicies(completionPolicies);
                        repeatTemplate.setCompletionPolicy(compositeCompletionPolicy);

                        /**
                         * ExceptionHandler 반복제어 사용 예제
                         */
//                        repeatTemplate.setExceptionHandler(simpleLimitExceptionHandler());

                        // (위에서처럼) 반복제어 설정 안하면 무한 반복하게 됨
                        repeatTemplate.iterate(new RepeatCallback() {
                            @Override
                            public RepeatStatus doInIteration(RepeatContext context) throws Exception {
                                // SimpleCompletionPolicy(3) -> item1을 3번 반복, item2를 3번 반복, item3을 3번 반복 (총 9번)
                                // TimeoutTerminationPolicy(3000) -> item1을 3초간 반복, item2를 3초간 반복, item3을 3초간 반복 (총 9초)
                                log.info("repeatTemplate test 중...");

                                return RepeatStatus.CONTINUABLE;
                            }
                        });
                        return item;  // SimpleCompletionPolicy(3)로 설정시, item 3개를 다 도는게 1번쨰, 3개를 또다시 돌면 2번쨰, 또다시 돌면 3번째
                    }
                })
                .writer(items -> log.info(items.toString()))
                .build();
    }

    @Bean
    public ExceptionHandler simpleLimitExceptionHandler() {
        return new SimpleLimitExceptionHandler(3); // 예외발생해도 3번까지 반복 종료 안됨 (4번쨰 예외발생시 반복문종료)
    }
}
