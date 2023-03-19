package com.study.springbatch.config.part8_itemprocessor.v2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class ClassifierCompositeItemProcessorConfigV2 {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() throws Exception {
        log.info(">> batchJob_v2");
        return jobBuilderFactory.get("batchJob_v2")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1_v2")
                .<ProcessorInfoDTO, ProcessorInfoDTO>chunk(10)
                .reader(new ItemReader<>() {
                    int i = 0;
                    @Override
                    public ProcessorInfoDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        ProcessorInfoDTO processorInfoDTO = ProcessorInfoDTO.builder()
                                .id(i)
                                .build();
                        return i > 3 ? null : processorInfoDTO;  // 총 10개의 item을 읽음
                    }
                })
                .processor(customItemProcessor())
                .writer(items -> System.out.println(items))
                .build();
    }

    @Bean
    public ItemProcessor<? super ProcessorInfoDTO, ? extends ProcessorInfoDTO> customItemProcessor() {
        ClassifierCompositeItemProcessor<ProcessorInfoDTO, ProcessorInfoDTO> processor = new ClassifierCompositeItemProcessor<>();


        Map<Integer, ItemProcessor<ProcessorInfoDTO, ProcessorInfoDTO>> processorMap = new HashMap<>();
        processorMap.put(1, new CustomItemProcessor1());
        processorMap.put(2, new CustomItemProcessor2());
        processorMap.put(3, new CustomItemProcessor3());

        ProcessorClassifier<ProcessorInfoDTO, ItemProcessor<?, ? extends ProcessorInfoDTO>> classifier = new ProcessorClassifier<>();
        classifier.setProcessorMap(processorMap);

        processor.setClassifier(classifier);
        return null;
    }
}
