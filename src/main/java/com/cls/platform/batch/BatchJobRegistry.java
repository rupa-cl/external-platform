/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch;


import java.util.HashMap;
import java.util.Map;

//TODO was JobStreamInfo
public class BatchJobRegistry {
    private static Map<String, String> jobStreamMap = new HashMap<String, String>();

    public static void registerBatchJob(String type, String batchJobName) {
        jobStreamMap.put(type, batchJobName);
    }

    public static String getBatchJobName(String type) {
        return jobStreamMap.get(type);
    }

}
