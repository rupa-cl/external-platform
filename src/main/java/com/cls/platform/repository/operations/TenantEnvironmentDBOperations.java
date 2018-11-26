/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.repository.operations;

import com.cls.platform.entity.Tenant;
import com.cls.platform.entity.TenantEnvironment;
import com.cls.platform.repository.TenantEnvironmentRepository;
import com.cls.platform.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class TenantEnvironmentDBOperations {
    private static final Logger log = LoggerFactory.getLogger(TenantEnvironmentDBOperations.class);

    public static TenantEnvironment createTenantEnvironment(TenantRepository tenantRepository,
                                                            TenantEnvironmentRepository tenantEnvironmentRepository,
                                                            TenantEnvironment tenantEnvironment) {

        log.debug("Tenant environment to be inserted is " + tenantEnvironment);
        TenantEnvironment newTenantEnvironment = new TenantEnvironment(tenantEnvironment);

        String tenantName = tenantEnvironment.getTenant().getName();

        log.debug("Tenant name is " + tenantName);

        Tenant existingTenant = TenantDBOperations.findTenant(tenantRepository, tenantName);

        log.debug("Retrieved tenant is " + existingTenant);
        if(existingTenant != null) {
            newTenantEnvironment.setTenant(existingTenant);
        }

        tenantEnvironmentRepository.save(newTenantEnvironment);

        log.debug("Newly inserted tenant environment is " + newTenantEnvironment);
        log.debug("Tenant after tenant environment insertion is " + existingTenant);

        return newTenantEnvironment;
    }


    public static TenantEnvironment getTenantEnvironmentByName(TenantEnvironmentRepository tenantEnvironmentRepository,
                                                               String tenantName,
                                                               String environmentName) {

        Iterable<TenantEnvironment> tenantEnvironments = tenantEnvironmentRepository.findAll();

        Iterator<TenantEnvironment> tenantEnvironmentIterator = tenantEnvironments.iterator();

        while (tenantEnvironmentIterator.hasNext()) {
            TenantEnvironment tenantEnvironment = tenantEnvironmentIterator.next();
            log.debug("Tenant environment is " + tenantEnvironment + ", specified tenant name is " + tenantName);
            if(tenantEnvironment.getTenant().getName().equalsIgnoreCase(tenantName) &&
                    tenantEnvironment.getName().equalsIgnoreCase(environmentName)) {
                return tenantEnvironment;
            }
        }
        return null;
    }

}
