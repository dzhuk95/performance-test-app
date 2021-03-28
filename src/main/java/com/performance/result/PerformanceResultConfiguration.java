package com.performance.result;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EntityScan(basePackages = "com.performance.result.dao")
@EnableJpaRepositories(basePackages = "com.performance.result.dao",
        entityManagerFactoryRef = "queryResultEntityManagerFactory",
        transactionManagerRef = "queryResultTransactionManager")
public class PerformanceResultConfiguration {

    @Primary
    @Bean(name = "queryResultEntityManager")
    public EntityManager entityManager(@Qualifier("springDataSource") DataSourceProperties dataSourceProperties) {
        return entityManagerFactoryBean(dataSourceProperties).getObject().createEntityManager();
    }

    @Bean(name = "springDataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties springDataSource() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "queryResultEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            @Qualifier("springDataSource") DataSourceProperties dataSourceProperties) {
        final HikariDataSource dataSource =
                dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");

        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaPropertyMap(properties);
        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManagerFactoryBean.setPersistenceUnitName("dataSource");
        entityManagerFactoryBean.setPackagesToScan("com.performance.result.dao");
        return entityManagerFactoryBean;
    }

    @Primary
    @Bean(name = "queryResultTransactionManager")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Primary
    @Bean(name = "queryResultEntityManager")
    public EntityManager queryResultEntityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean(name = "queryResultTransactionManager")
    public PlatformTransactionManager queryResultTransactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean(name = "queryResultExceptionTranslation")
    public PersistenceExceptionTranslationPostProcessor queryResultExceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
