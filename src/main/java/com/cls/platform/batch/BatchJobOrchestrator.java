/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch;


import com.cls.platform.batch.domain.ClsContract;
import com.cls.platform.core.config.PlatformConstants;
import com.cls.platform.repository.TenantEnvironmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//TODO was SForceBatchJob
@Configuration
public class BatchJobOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(BatchJobOrchestrator.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope  //Annotation to get the Job parameters from the job invocation
    public BatchDataReader salesforceBatchDataReader(
            @Value("#{jobParameters[tenant]}") String tenant,
            @Value("#{jobParameters[env]}") String env,
            @Value("#{jobParameters[batchJobName]}") String batchJobName,
            @Value("#{jobParameters[batchJobId]}") String batchJobId) {
        log.debug("*** Job parameters in salesforceBatchDataReader are "
                + tenant + " " + env + " " + batchJobName);
        BatchDataReader reader = new BatchDataReader();
        reader.setTenant(tenant);
        reader.setEnv(env);
        reader.setBatchJobName(batchJobName);
        reader.configure();
        return reader;
    }

    @Bean
    public TaskExecutor salesforceTaskExecutor(){
        log.debug("*** salesforceTaskExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(PlatformConstants.BATCH_THREAD_POOL_SIZE);
        executor.setCorePoolSize(PlatformConstants.BATCH_THREAD_POOL_SIZE);

        return executor;
    }

    @Bean
    @StepScope
    public BatchDataWriter salesforceBatchDataWriter(
            @Value("#{jobParameters[tenant]}") String tenant,
            @Value("#{jobParameters[env]}") String env,
            @Value("#{jobParameters[batchJobName]}") String batchJobName,
            @Value("#{jobParameters[batchJobId]}") String batchJobId) {
        log.debug("*** Job parameters in salesforceBatchDataWriter are "
                + tenant + " " + env + " " + batchJobName);
        BatchDataWriter writer = new BatchDataWriter();
        writer.setTenant(tenant);
        writer.setEnv(env);
        writer.setBatchJobName(batchJobName);
        return writer;
    }

    @Bean
    public BatchStepListener salesforceBatchStepListener() {
        log.debug("*** salesforceBatchStepListener");
        BatchStepListener stepListener = new BatchStepListener();
        return stepListener;
    }

    @Bean
    public BatchJobListener processSForceJobListener() {
        log.debug("*** processSForceJobListener");
        return new BatchJobListener();
    }

    @Bean
    public Job salesforceBatchJobRun() {
        log.debug("*** Register Batch Job, salesforceBatchJobRun");
        BatchJobRegistry.registerBatchJob("sforce-rest-test-job", "testSForceBatchRest");
        return jobBuilderFactory.get("testSForceBatchRest")
                .incrementer(new RunIdIncrementer())
                //.listener(processSForceJobListener())
                .preventRestart()
                .flow(testSForceBatchRestStep())
                .end()
                .build();
    }

    @Bean
    public Step testSForceBatchRestStep() {
        log.debug("*** testSForceBatchRestStep");
        return stepBuilderFactory.get("testSForceBatchRestStep")
                .<ClsContract, ClsContract> chunk(PlatformConstants.BATCH_CHUNK_SIZE)
                .reader(salesforceBatchDataReader(null, null, null, null))
                .writer(salesforceBatchDataWriter(null, null, null, null))
                .listener(salesforceBatchStepListener())
                .taskExecutor(salesforceTaskExecutor())
                .throttleLimit(PlatformConstants.BATCH_THREAD_POOL_SIZE)
                .build();
    }
}
