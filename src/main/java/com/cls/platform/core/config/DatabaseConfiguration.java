/*
 * Copyright (c) 2018,  Cloud Lending, a Q2 Company.
 * All Rights Reserved.
 */

package com.cls.platform.core.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;

//TODO was SqlDbConfiguration
@Configuration
public class DatabaseConfiguration {
    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    //TODO what's this
    @Inject
    private Environment env;

    //TODO what's this
    @Inject
    JpaProperties jpaProperties;

    @Bean(name = "batchDataSource")
    public DataSource batchDataSource(DataSourceProperties dataSourceProperties) {
        log.debug("Configuring Batch Datasource");
        if (dataSourceProperties.getUrl() == null) {
            log.error(
                    "Your database connection pool configuration is incorrect! The application"
                            + " cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        //Hikari is a JDBC DataSource implementation that provides a connection pooling mechanism. It's faster
        //and more lightweight than the Tomcat-JDBC datasource. It's the default datasource in spring boot 2
        //TODO do we need this if the default is Hikari?
        HikariDataSource hikariDataSource = DataSourceBuilder
                .create(dataSourceProperties.getClassLoader()).type(HikariDataSource.class)
                .driverClassName(dataSourceProperties.getDriverClassName()).url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername()).password(dataSourceProperties.getPassword()).build();

        hikariDataSource.setMaximumPoolSize(PlatformConstants.HIKARI_POOL_SIZE);

        return hikariDataSource;
    }
}
