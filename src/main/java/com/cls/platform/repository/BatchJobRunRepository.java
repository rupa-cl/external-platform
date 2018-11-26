/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.repository;

import com.cls.platform.entity.BatchJobRun;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called batchJobRunRepository
// CRUD refers Create, Read, Update, Delete

public interface BatchJobRunRepository extends CrudRepository<BatchJobRun, Long> {

}
