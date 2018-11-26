/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//TODO was SForceService
@Service
public class SalesforceLoginService {

    private static final Logger log = LoggerFactory.getLogger(SalesforceLoginService.class);

    public SalesforceAuthResponse login (SalesforceConnectionAttributes connectionAttributes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        //Login parameters
        params.add(SalesforceConnectionAttributes.USERNAME, connectionAttributes.getUsername());
        params.add(SalesforceConnectionAttributes.PASSWORD, connectionAttributes.getPassword());
        params.add(SalesforceConnectionAttributes.CLIENT_SECRET, connectionAttributes.getClientSecret());
        params.add(SalesforceConnectionAttributes.CLIENT_ID, connectionAttributes.getClientId());
        params.add(SalesforceConnectionAttributes.GRANT_TYPE, SalesforceConnectionAttributes.PASSWORD);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SalesforceAuthResponse> response = restTemplate.postForEntity
                (connectionAttributes.getLoginUrl(), request, SalesforceAuthResponse.class);
        return response.getBody();
    }

}
