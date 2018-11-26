/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class BatchJobListener implements JobExecutionListener {

    private final Logger log = LoggerFactory.getLogger(BatchJobListener.class);
/*
    @Inject
    private BatchJobDefinitionService jobStreamService;

    @Inject
    private BatchJobService jobStreamExecutionService;
    */

    @Override
    public void beforeJob(JobExecution jobExecution) {
        /*
        JobParameters parameters = jobExecution.getJobParameters();
        String jobStreamId = parameters.getString("jobStreamId");
        String jobStreamExecutionId = parameters.getString("jobStreamExecutionId");

        try {

            BatchJobDefinition jobStream = jobStreamService.findOne(jobStreamId);
            if(jobStream!= null && !jobStream.isScheduled()) {
                jobStream.setStatus(BatchJobDefinitionStatus.PROCESSING);
                jobStream.setJobData(getJobData(jobStream.getJobData()));
                jobStreamService.save(jobStream);
            }
        } catch(Exception e) {
            log.error("Error Updating BatchJobDefinition", e);
        }
        */
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        /*
        JobParameters parameters = jobExecution.getJobParameters();
        String jobStreamId = parameters.getString("jobStreamId");
        String jobStreamExecutionId = parameters.getString("jobStreamExecutionId");

        try {

            BatchJobDefinition jobStream = jobStreamService.findOne(jobStreamId);
            BatchJob jobStreamExecution = jobStreamExecutionService.findOne(jobStreamExecutionId);

            if(jobStream!= null && jobStreamExecution != null) {
                jobStreamExecution.setStatus(BatchJobStatus.COMPLETED);
                jobStreamExecution.setBatchJobExecutionId(jobExecution.getId());
                jobStreamExecution.setBatchCreateTime(jobExecution.getCreateTime());
                jobStreamExecution.setBatchStartTime(jobExecution.getStartTime());
                jobStreamExecution.setBatchEndTime(jobExecution.getEndTime());
                jobStreamExecution.setBatchStatus(jobExecution.getStatus().name());
                jobStreamExecution.setBatchExitStatus(jobExecution.getExitStatus().getExitCode());
                jobStreamExecution.setTimeToComplete((jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime()) / 1000);

                jobStreamExecution = jobStreamExecutionService.save(jobStreamExecution);

                if(jobStream.isScheduled()) {
                    jobStream.setLastRunDate(ZonedDateTime.ofInstant(jobExecution.getEndTime().toInstant(), ZoneId.systemDefault()));
                    jobStreamService.save(jobStream);
                } else {
                    jobStream.setStatus(BatchJobDefinitionStatus.COMPLETED);
                    jobStreamService.save(jobStream);
                }
            }
        } catch(Exception e) {
            log.error("Error Updating BatchJobDefinition", e);
        }
        */
    }

    /**
     *
     * @return
     */
    public Object getJobData(Object data) {
        return data;
    }
}
