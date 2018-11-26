/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.batch;

import com.cls.platform.batch.domain.ClsContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

//TODO was LoanFieldSetMapper
public class ContractFieldSetMapper implements FieldSetMapper<ClsContract> {
    private final Logger log = LoggerFactory.getLogger(ContractFieldSetMapper.class);

    public ClsContract mapFieldSet(FieldSet fieldSet) {
        ClsContract loan = new ClsContract();

        loan.setId(fieldSet.readString("Id"));
        loan.setName(fieldSet.readString("Name"));

        return loan;
    }
}