/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
//TODO: was SForceLoan

@JsonInclude(JsonInclude.Include.NON_NULL)  //Include only non null values when serializing
@JsonIgnoreProperties(ignoreUnknown = true) //Ignore unknown properties when serializing

public class ClsContract implements Serializable {
    private static final long serialVersionUID = 1L;    //version number for the serializable

    @JsonProperty("Id")     //JsonProperty indicates the property name used for parsing the JSON  for ClsContract
    private String id;

    @JsonProperty("Name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
