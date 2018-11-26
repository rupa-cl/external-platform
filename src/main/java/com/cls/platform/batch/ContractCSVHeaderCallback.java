/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch;

import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class ContractCSVHeaderCallback implements LineCallbackHandler {

    private DelimitedLineTokenizer tokenizer;

    public ContractCSVHeaderCallback(DelimitedLineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public void handleLine(String line) {
        line = line.replace("\"", "");
        System.out.println(line);
        // TODO Auto-generated method stub
        tokenizer.setNames(line.split(","));
    }
}
