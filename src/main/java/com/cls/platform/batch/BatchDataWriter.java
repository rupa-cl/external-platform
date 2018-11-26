/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch;

import com.cls.platform.batch.domain.ClsContract;
import com.cls.platform.batch.domain.ClsContractsPostData;
import com.cls.platform.core.config.PlatformConstants;
import com.cls.platform.entity.BatchJob;
import com.cls.platform.repository.BatchJobRepository;
import com.cls.platform.repository.operations.BatchJobDBOperations;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


//TODO was SForceDataWriter
public class BatchDataWriter implements ItemWriter<ClsContract> {
    private static final Logger log = LoggerFactory.getLogger(BatchDataWriter.class);

    @Autowired
    private BatchJobRepository batchJobRepository;
    private BatchJob batchJob;

    private SalesforceAuthResponse authResponse;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static ObjectMapper mapper = new ObjectMapper();

    OkHttpClient client = new OkHttpClient();

    private String tenant = PlatformConstants.DEFAULT_TENANT_NAME;

    private String env = PlatformConstants.DEFAULT_ENV_NAME;

    private String batchJobName = PlatformConstants.DEFAULT_JOB_NAME;


    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getBatchJobName() {
        return batchJobName;
    }

    public void setBatchJobName(String batchJobName) {
        this.batchJobName = batchJobName;
    }

    private static int count = 0;

    @BeforeStep
    protected void beforeStep(final StepExecution stepExectuion) throws Exception {
        ExecutionContext context = stepExectuion.getExecutionContext();
        authResponse = (SalesforceAuthResponse) context.get("sforce_auth");
        batchJob = BatchJobDBOperations.getBatchJobByName(batchJobRepository, tenant, batchJobName);
    }

    @Override
    public void write(List<? extends ClsContract> list) throws Exception {
        if (batchJob == null) {
            return;
        }

        List<String> loanNames = list.stream().map(loan -> {
            return "'" + loan.getName() + "'";
        }).collect(Collectors.toList());

        List<String> loanIds = list.stream().map(loan -> {
            return "'" + loan.getId() + "'";
        }).collect(Collectors.toList());

        ClsContractsPostData loanPost = new ClsContractsPostData();
        loanPost.setLoanIds(loanIds);

        try {
            String postString = mapper.writeValueAsString(loanPost);

            String startTime = LocalTime.now().format(dtf);
            long start = System.currentTimeMillis();

            String loanAPI = batchJob.getBatchHandlerRESTApi();

            RequestBody body = RequestBody.create(PlatformConstants.JSON, postString);

            Request request = new Request.Builder()
                    .url(loanAPI)
                    .addHeader("Authorization", "Bearer " + authResponse.getAccessToken())
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String endTime = LocalTime.now().format(dtf);
                long end = System.currentTimeMillis();
                String content = response.body().string();

                System.out.println("********************************");
                System.out.println("LoanWriter,Ids," + loanNames);
                System.out.println("LoanWriter-Summary," + Thread.currentThread().getId() + "," + content + "," + (end - start) + " ms, " + startTime + ", " + endTime + ", Count-" + ++count);
                System.out.println("********************************");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("----------------------------------");
                System.out.println("LoanWriter --> " + Thread.currentThread().getId() + " Failed Batch " + e.getMessage());
                System.out.println("---------------------------------");
            }
        } catch (Exception e) {

        }
    }
}
