/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch;

//TODO was SForceAppConfig

public class SalesforceConnectionAttributes {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String CLIENT_ID = "client_id";
    public static final String GRANT_TYPE = "grant_type";

    private String loginUrl;
    private String serviceUrl;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;


    public String getLoginUrl() {
        return loginUrl;
    }
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
    public String getServiceUrl() {
        return serviceUrl;
    }
    public void setServiceUrl(String servceUrl) {
        this.serviceUrl = servceUrl;
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
