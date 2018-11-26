/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.core.config;

import okhttp3.MediaType;

public class PlatformConstants {
    public static final int HIKARI_POOL_SIZE = 40;

    public static final int BATCH_THREAD_POOL_SIZE = 50;
    public static final int BATCH_CHUNK_SIZE = 20;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String DEFAULT_TENANT_NAME = "DEFAULT_TENANT";
    public static final String DEFAULT_ENV_NAME = "DEFAULT_SANDBOX";
    public static final String DEFAULT_JOB_NAME = "DEFAULT_JOB";
}
