/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.entity;

import com.cls.platform.entity.exception.EntityException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "tenant_environment")
public class TenantEnvironment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @JsonBackReference      //Tells the Jackson JSON parser that this is a back reference and should not
                            //be serialized and will be reconstructed during
                            //the deserialization of the forward i.e. BatchJob part. This is to prevent the
                            //infinite recursion problem during Jackson/JSON serialization
    private Tenant tenant;

    private String name;

    private String loginUrl;

    private String serviceUrl;

    private String clientId;

    private String clientSecret;

    private String userName;

    private String password;

    public TenantEnvironment() {
    }

    public TenantEnvironment(TenantEnvironment newTenantEnvironment) {
        if (newTenantEnvironment != null) {
            this.clientId = newTenantEnvironment.getClientId();
            this.clientSecret = newTenantEnvironment.getClientSecret();
            this.name = newTenantEnvironment.getName();
            this.loginUrl = newTenantEnvironment.getLoginUrl();
            this.password = newTenantEnvironment.getPassword();
            this.serviceUrl = newTenantEnvironment.getServiceUrl();
            this.userName = newTenantEnvironment.getUserName();
        } else {
            throw new EntityException("Cannot create new batch job, input batch job object is null");
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

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
