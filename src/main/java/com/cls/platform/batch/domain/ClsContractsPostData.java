/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

//TODO was SForceLoanPost

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClsContractsPostData implements Serializable {
    private static final long serialVersionUID = 1L;    //version number for the serializable

    @JsonProperty("loanIds")
    private List<String> loanIds;

    public List<String> getLoanIds() {
        return loanIds;
    }

    public void setLoanIds(List<String> loanIds) {
        this.loanIds = loanIds;
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
