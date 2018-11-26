/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.repository;

import com.cls.platform.entity.BatchJob;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called batchJobRepository
// CRUD refers Create, Read, Update, Delete

public interface BatchJobRepository extends CrudRepository<BatchJob, Long> {

}
