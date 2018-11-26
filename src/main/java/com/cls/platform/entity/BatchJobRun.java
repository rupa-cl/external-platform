/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "batch_job_run")
public class BatchJobRun {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Date startDate;

    private Date endDate = null;

    @ManyToOne
    @JoinColumn(name = "batch_job_id")
    @JsonBackReference      //Tells the Jackson JSON parser that this is a back reference and should not
                            //be serialized and will be reconstructed during
                            //the deserialization of the forward i.e. BatchJobRun part. This is to prevent the
                            //infinite recursion problem during Jackson/JSON serialization
    private BatchJob batchJob;

    public BatchJobRun() {
        startDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BatchJob getBatchJob() {
        return batchJob;
    }

    public void setBatchJob(BatchJob batchJob) {
        this.batchJob = batchJob;
    }

}
