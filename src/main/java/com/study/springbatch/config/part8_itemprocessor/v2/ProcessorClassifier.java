package com.study.springbatch.config.part8_itemprocessor.v2;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.HashMap;
import java.util.Map;

public class ProcessorClassifier<C,T> implements Classifier<C,T> {

    private Map<Integer, ItemProcessor<ProcessorInfoDTO, ProcessorInfoDTO>> processorMap = new HashMap<>();

    @Override
    public T classify(C classifiable) {
        return (T) processorMap.get(((ProcessorInfoDTO) classifiable).getId());
    }

    public void setProcessorMap(Map<Integer, ItemProcessor<ProcessorInfoDTO, ProcessorInfoDTO>> processorMap) {
        this.processorMap = processorMap;
    }
}

