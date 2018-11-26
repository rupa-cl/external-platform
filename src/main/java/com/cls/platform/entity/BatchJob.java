/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.entity;

import com.cls.platform.entity.exception.EntityException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "batch_job")
public class BatchJob {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private String batchHandlerRESTApi;
    private String query;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @JsonBackReference      //Tells the Jackson JSON parser that this is a back reference and should not
                            //be serialized and will be reconstructed during
                            //the deserialization of the forward i.e. BatchJob part. This is to prevent the
                            //infinite recursion problem during Jackson/JSON serialization
    private Tenant tenant;

    @OneToMany(mappedBy = "batchJob", cascade = CascadeType.ALL)
    @JsonManagedReference   //Tells the Jackson JSON parser to convert this into a JSON like storage format
                            //The back reference i.e. BatchJob will not be serialized and will be reconstructed during
                            //the deserialization of the forward i.e. BatchJobRun part. This is to prevent the
                            //infinite recursion problem during Jackson/JSON serialization
    private Set<BatchJobRun> batchJobRuns = new HashSet<BatchJobRun>();

    public BatchJob() {
    }

    public BatchJob(BatchJob newBatchJob) {
        if (newBatchJob != null) {
            this.name = newBatchJob.getName();
            this.batchHandlerRESTApi = newBatchJob.getBatchHandlerRESTApi();
            this.query = newBatchJob.getQuery();
        } else {
            throw new EntityException("Cannot create new batch job, input batch job object is null");
        }
    }

    public BatchJob(String name, String batchHandlerRESTApi, String query) {
        this.batchHandlerRESTApi = batchHandlerRESTApi;
        this.name = name;
        this.query = query;
    }

    public Long getId() {
        return id;
    }

    public String getBatchHandlerRESTApi() {
        return batchHandlerRESTApi;
    }

    public String getName() {
        return name;
    }

    public String getQuery() {
        return query;
    }


    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String newString = mapper.writeValueAsString(this);
            return newString;
        } catch(JsonProcessingException exp) {
            exp.printStackTrace();
        }
        return null;
    }
}
