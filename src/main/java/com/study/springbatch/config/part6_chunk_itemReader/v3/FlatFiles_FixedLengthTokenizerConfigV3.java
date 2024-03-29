package com.study.springbatch.config.part6_chunk_itemReader.v3;

import com.study.springbatch.config.part6_chunk_itemReader.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

/**
 * ItemReader 의 FlatFileItemReader 학습
 * - FlatFieldItemReader 는 파일을 읽는 두가지 방식 존재
 * 1. DelimitedLineTokenizer - 구분자방식
 * 2. FixedLengthTokenizer   -고정길이 방식  !!!
 * <p>
 * - 여기서는 스프링 배치가 제공하는 2번 활용
 */
@RequiredArgsConstructor
@Configuration
@Slf4j
public class FlatFiles_FixedLengthTokenizerConfigV3 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchJob() {
        log.info(">> batchJob_v3");
        return jobBuilderFactory.get("batchJob_v3")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1_v3")
                .<String, String>chunk(5)
                .reader(itemReader())
                .writer(new ItemWriter() {
                    @Override
                    public void write(List items) throws Exception {
                        log.info("items = {}" + items);
                    }
                })
                .build();
    }

    /**
     * FixedLengthTokenizer 활용 - ex) fixedLength(), addColumns()
     */
    @Bean
    public ItemReader itemReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFile")
                .resource(new FileSystemResource("/Users/jiwon/dev-study/inflearn-spring-batch/springbatch/src/main/resources/customerPart6_v3.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>()) // 직접구현하기보다, 제공되는걸 쓰는게 훨씬 간편!
                .targetType(Customer.class) // 대신 타겟클래스 지정해야함
                .linesToSkip(1) // customer.csv파일의 첫번째 라인은 skip
                .fixedLength()
                .strict(false)
                .addColumns(new Range(1, 5)) // user1, user2 등의 user
                .addColumns(new Range(6, 7)) // 31, 32 등의 age
                .addColumns(new Range(8, 11))  // 2001, 2002 등의 year
//                .addColumns(new Range(1))  // ex) 이처럼 min 값만 지정하면 1번째부터 끝까지 읽는다는 뜻

                .names("name", "age", "year")
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2_v3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">> step2_v3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
