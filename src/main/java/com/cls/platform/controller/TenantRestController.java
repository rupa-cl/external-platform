/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.controller;

import com.cls.platform.entity.Tenant;
import com.cls.platform.entity.TenantEnvironment;
import com.cls.platform.repository.TenantEnvironmentRepository;
import com.cls.platform.repository.TenantRepository;
import com.cls.platform.repository.operations.TenantDBOperations;
import com.cls.platform.repository.operations.TenantEnvironmentDBOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenant")
public class TenantRestController {
    private final Logger log = LoggerFactory.getLogger(TenantRestController.class);


    @Autowired  // This gets the bean called tenantRepository
                // Which is auto-generated by Spring, we will use it to handle the data
    private TenantRepository tenantRepository;

    @Autowired
    private TenantEnvironmentRepository tenantEnvironmentRepository;


    @RequestMapping(method= RequestMethod.GET, path="/find")
    //example request: http://localhost:8080/tenant/find?name=ABN
    public Tenant getTenant(@RequestParam(value="name") String name) {
        return TenantDBOperations.findTenant(tenantRepository, name);
    }

    @RequestMapping(method={RequestMethod.PUT}, path="/new")
    public Tenant createTenant(@RequestBody Tenant tenant) {
        //example request: http://localhost:8080/tenant/new, header: Content-Type=application/json,
        //body: {
        //    "name": "ABN",
        //}
        return TenantDBOperations.createTenant(tenantRepository, tenant);

    }

    @RequestMapping(method={RequestMethod.PUT}, path="/environment/new")
    public TenantEnvironment createTenantEnvironment(@RequestBody TenantEnvironment tenantEnvironment) {
        //example request: http://localhost:8080/tenant/environment/new, header: Content-Type=application/json,
        //body: {
        //    "name": "Sandbox",
        //    "loginUrl": "https://cs10.salesforce.com/services/oauth2/token",
        //    "serviceUrl": "https://cs10.salesforce.com/services",
        //    "clientId": "3MVG9_7ddP9KqTzcf82mgk9kVfn.W_kT9SNZNPTvQF3AuTX0QGi3YGyMf5valqoGDgy37DHJsdiq3Ym3C0rIe",
        //    "clientSecret": "5484539614263923975",
        //    "userName": "sanjay.ldv@cloudlendinginc.com",
        //    "password": "Park2018#",
        //    "tenant": {
        //        "name": "ABN"
        //    }

        return TenantEnvironmentDBOperations.createTenantEnvironment(tenantRepository, tenantEnvironmentRepository,
                tenantEnvironment);

    }
}
