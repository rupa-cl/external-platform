/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.repository;

import com.cls.platform.entity.TenantEnvironment;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called tenantRepository
// CRUD refers Create, Read, Update, Delete

public interface TenantEnvironmentRepository extends CrudRepository<TenantEnvironment, Long> {

}
