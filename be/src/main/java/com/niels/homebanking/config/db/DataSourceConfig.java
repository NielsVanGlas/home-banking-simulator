package com.niels.homebanking.config.db;

import jakarta.persistence.EntityManagerFactory;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EntityScan
public class DataSourceConfig {

    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource readWriteConfiguration() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource readOnlyConfiguration() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource routingDataSource() {
        return new TransactionRoutingDataSource(loggingProxy("read_write", readWriteConfiguration()),
                loggingProxy("read_only", readOnlyConfiguration()));
    }

    private DataSource loggingProxy(String name, DataSource dataSource) {
        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
        loggingListener.setLogLevel(SLF4JLogLevel.DEBUG);
        loggingListener.setLogger(name);
        loggingListener.setWriteConnectionId(false);
        return ProxyDataSourceBuilder.create(dataSource).name(name).listener(loggingListener).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(routingDataSource()).packages("com.niels.homebanking").build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("jpaTransactionManager") PlatformTransactionManager wrapped) {
        return new ReplicaAwareTransactionManager(wrapped);
    }

    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
