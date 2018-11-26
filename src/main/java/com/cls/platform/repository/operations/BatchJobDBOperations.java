/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.repository.operations;

import com.cls.platform.entity.BatchJob;
import com.cls.platform.entity.Tenant;
import com.cls.platform.repository.BatchJobRepository;
import com.cls.platform.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BatchJobDBOperations {

    private static final Logger log = LoggerFactory.getLogger(BatchJobDBOperations.class);

    public static Set<BatchJob>getBatchJobs(BatchJobRepository batchJobRepository, String tenantName) {

        Iterable<BatchJob> batchJobs = batchJobRepository.findAll();

        Iterator<BatchJob> batchJobsItertor = batchJobs.iterator();

        Set<BatchJob> batchJobsForTenant = new HashSet<>();

        while (batchJobsItertor.hasNext()) {
            BatchJob batchJob = batchJobsItertor.next();
            log.debug("Batch Job is " + batchJob + ", specified tenant name is " + tenantName);
            if(batchJob.getTenant().getName().equalsIgnoreCase(tenantName)) {
                batchJobsForTenant.add (batchJob);
            }
        }
        return batchJobsForTenant;
    }

    public static BatchJob getBatchJobByName(BatchJobRepository batchJobRepository, String tenantName,
                                             String batchJobName) {

        Iterable<BatchJob> batchJobs = batchJobRepository.findAll();

        Iterator<BatchJob> batchJobsItertor = batchJobs.iterator();

        while (batchJobsItertor.hasNext()) {
            BatchJob batchJob = batchJobsItertor.next();
            log.debug("Batch Job is " + batchJob + ", specified tenant name is " + tenantName);
            if(batchJob.getTenant().getName().equalsIgnoreCase(tenantName) &&
                batchJob.getName().equalsIgnoreCase(batchJobName)) {
                return batchJob;
            }
        }
        return null;
    }

    public static BatchJob createBatchJob(TenantRepository tenantRepository, BatchJobRepository batchJobRepository,
                                          BatchJob batchJob) {
        log.debug("BatchJob to be inserted is " + batchJob);
        BatchJob newBatchJob = new BatchJob(batchJob);

        String tenantName = batchJob.getTenant().getName();

        log.debug("Tenant name is " + tenantName);

        Tenant existingTenant = TenantDBOperations.findTenant(tenantRepository, tenantName);

        log.debug("Retrieved tenant is " + existingTenant);
        if(existingTenant != null) {
            newBatchJob.setTenant(existingTenant);
        }

        batchJobRepository.save(newBatchJob);

        log.debug("Newly inserted batch job is " + newBatchJob);
        log.debug("Tenant after batch job insertion is " + existingTenant);

        return newBatchJob;
    }
}
