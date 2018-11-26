/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.repository.operations;

import com.cls.platform.entity.Tenant;
import com.cls.platform.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class TenantDBOperations {
    private static final Logger log = LoggerFactory.getLogger(TenantDBOperations.class);

    public static Tenant createTenant(TenantRepository tenantRepository, Tenant tenant) {

        Tenant newTenant = new Tenant(tenant);
        tenantRepository.save(newTenant);

        log.debug("Newly inserted tenant is " + newTenant);

        return newTenant;
    }


    public static Tenant findTenant (TenantRepository tenantRepository, String name) {
        log.debug("Number of tenants " + tenantRepository.count());

        Iterable<Tenant> tenants = tenantRepository.findAll();

        Iterator<Tenant> tenantsItertor = tenants.iterator();

        while (tenantsItertor.hasNext()) {
            Tenant tenant = tenantsItertor.next();
            log.debug("Tenant is " + tenant + ", specified name is " + name);
            if(tenant.getName().equalsIgnoreCase(name)) {
                return tenant;
            }
        }
        return null;
    }

}
