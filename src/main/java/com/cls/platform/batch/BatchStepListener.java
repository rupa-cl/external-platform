/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class BatchStepListener implements StepExecutionListener {
    private static final Logger log = LoggerFactory.getLogger(BatchStepListener.class);

    private long start = 0;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        start = System.currentTimeMillis();
        System.out.println("StepExecutionListener - beforeStep" + stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        long end = System.currentTimeMillis();
        System.out.println("StepExecutionListener - afterStep" + stepExecution.getStepName() + (end - start) + " ms");
        return stepExecution.getExitStatus();
    }
}
