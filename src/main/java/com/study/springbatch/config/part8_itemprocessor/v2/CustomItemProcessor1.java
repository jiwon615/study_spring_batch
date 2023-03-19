package com.study.springbatch.config.part8_itemprocessor.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CustomItemProcessor1 implements ItemProcessor<ProcessorInfoDTO, ProcessorInfoDTO> {
    @Override
    public ProcessorInfoDTO process(ProcessorInfoDTO item) throws Exception {
        log.info(">>CustomItemProcessor1()");
        return item;
    }
}
