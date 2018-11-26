/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.repository.operations;

import com.cls.platform.entity.BatchJob;
import com.cls.platform.entity.BatchJobRun;
import com.cls.platform.repository.BatchJobRepository;
import com.cls.platform.repository.BatchJobRunRepository;
import com.cls.platform.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class BatchJobRunDBOperations {

    private static final Logger log = LoggerFactory.getLogger(BatchJobRunDBOperations.class);

    public static BatchJobRun createBatchJobRun(TenantRepository tenantRepository, BatchJobRepository batchJobRepository,
                                                BatchJobRunRepository batchJobRunRepository, String tenantName,
                                                String batchJobName) {


        BatchJob batchJob = BatchJobDBOperations.getBatchJobByName(batchJobRepository, tenantName, batchJobName);

        log.debug("BatchJob to be run is " + batchJob.getName());

        String tenantNameFromJob = batchJob.getTenant().getName();

        log.debug("Tenant name is " + tenantNameFromJob);

        BatchJobRun batchJobRun = new BatchJobRun();

        if(batchJob != null) {
            batchJobRun.setBatchJob(batchJob);
        }

        batchJobRunRepository.save(batchJobRun);

        log.debug("Newly created batch job run is " + batchJobRun);
        log.debug("Batch job after batch job run creation is " + batchJob);

        return batchJobRun;
    }

    public static BatchJobRun endBatchJobRun(BatchJobRunRepository batchJobRunRepository, BatchJobRun batchJobRun) {
        batchJobRun.setEndDate(new Date());
        batchJobRunRepository.save(batchJobRun);
        return batchJobRun;
    }
}
