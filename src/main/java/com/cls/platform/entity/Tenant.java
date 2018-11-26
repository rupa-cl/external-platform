/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.entity;

import com.cls.platform.entity.exception.EntityException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    @JsonManagedReference   //Tells the Jackson JSON parser to convert this into a JSON like storage format
                            //The back reference i.e. Tenant will not be serialized and will be reconstructed during
                            //the deserialization of the forward i.e. BatchJob part. This is to prevent the
                            //infinite recursion problem during Jackson/JSON serialization
    private Set<BatchJob> batchJobs = new HashSet<BatchJob>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    @JsonManagedReference   //Tells the Jackson JSON parser to convert this into a JSON like storage format
    //The back reference i.e. Tenant will not be serialized and will be reconstructed during
    //the deserialization of the forward i.e. BatchJob part. This is to prevent the
    //infinite recursion problem during Jackson/JSON serialization
    private Set<TenantEnvironment> tenantEnvironments = new HashSet<TenantEnvironment>();

    public Tenant() {
    }

    public Tenant(Tenant newTenant) {
        if (newTenant != null) {
            this.name = newTenant.getName();
        } else {
            throw new EntityException("Cannot create new tenant, input tenant object is null");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BatchJob> getBatchJobs() {
        return batchJobs;
    }

    public void setBatchJobs(Set<BatchJob> batchJobs) {
        this.batchJobs = batchJobs;
    }

    public Set<TenantEnvironment> getTenantEnvironments() {
        return tenantEnvironments;
    }

    public void setTenantEnvironments(Set<TenantEnvironment> environments) {
        this.tenantEnvironments = environments;
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
