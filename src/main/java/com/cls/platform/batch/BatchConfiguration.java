/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch;

import com.cls.platform.core.config.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@AutoConfigureAfter(value = { DatabaseConfiguration.class })
@EnableBatchProcessing  //@EnableBatchProcessing provides a base configuration for building batch jobs.
                        // Within this base configuration, an instance of StepScope is created in addition
                        // to a number of beans made available to be autowired:
                        // JobRepository - bean name "jobRepository"
                        // JobLauncher - bean name "jobLauncher"
                        // JobRegistry - bean name "jobRegistry"
                        // PlatformTransactionManager - bean name "transactionManager"
                        // JobBuilderFactory - bean name "jobBuilders"
                        // StepBuilderFactory - bean name "stepBuilders"
public class BatchConfiguration extends DefaultBatchConfigurer {
    //DefaultBatchConfigurer  doesn’t require a dataSource, it’s Autowired with required to false, so
    // it will use a Map based JobRepository if its dataSource is null. Here though, we set the data source
    // to Hikari-JDBC

    private final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Override
    @Autowired
    //Use the data source configured in DatabaseConfiguration
    public void setDataSource(@Qualifier("batchDataSource") DataSource batchDataSource) {
        super.setDataSource(batchDataSource);
        dataSource = batchDataSource;
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {
        log.debug("BatchConfiguration" + "createJobRepository");
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
